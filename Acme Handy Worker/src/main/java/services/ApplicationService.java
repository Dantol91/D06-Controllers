
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Application;
import domain.CreditCard;
import domain.Customer;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class ApplicationService {

	// Managed repository

	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supported services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CreditCardService		creditCardService;

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private CustomerService			customerService;


	// Constructor
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods

	public Application create() {
		Application result;
		Date moment;
		HandyWorker h;

		result = new Application();
		moment = new Date(System.currentTimeMillis() - 1);
		h = (HandyWorker) this.actorService.findByPrincipal();

		result.setRegisterMoment(moment);
		result.setStatus("PENDING");
		result.setHandyWorker(h);

		return result;
	}

	public Application save(final Application application) {
		Assert.notNull(application);

		Application result;
		Collection<Application> applications;
		final HandyWorker h;

		h = (HandyWorker) this.actorService.findByPrincipal();

		if (application.getCreditcard() != null) {
			final CreditCard c = this.creditCardService.save(application.getCreditcard());
			application.setCreditcard(c);
			application.setStatus("ACCEPTED");

		}

		result = this.applicationRepository.save(application);

		if (application.getId() == 0) {
			applications = h.getApplications();
			applications.add(result);
			h.setApplications(applications);
			this.handyWorkerService.save(h);
		}

		return result;
	}

	public Collection<Application> findAll() {

		Collection<Application> result;

		result = this.applicationRepository.findAll();

		return result;
	}

	public Application findOne(final int applicationId) {

		Application result;

		result = this.applicationRepository.findOne(applicationId);
		Assert.notNull(result);

		return result;
	}

	// Other business methods

	// Admin Dashboard

	//C.12.5
	public Double getPendingRatio() {
		return this.applicationRepository.getPendingRatio();
	}

	//C.12.5
	public Double getRejectedRatio() {
		return this.applicationRepository.getRejectedRatio();
	}

	//C.12.5
	public Double getAcceptedRatio() {
		return this.applicationRepository.getAcceptedRatio();
	}

	//The ratio of pending applications that cannot change its status because their time periods elapsed
	public Double getPendingRatioCannotChange() {

		return this.applicationRepository.getPendingRatioCannotChange();
	}

	public Collection<Application> getHandyWorkerApplications(final int HandyWorkerId) {

		return this.applicationRepository.getHandyWorkerApplications(HandyWorkerId);
	}

	// C El HandyWorker puede cancelar una Aplication con status ACCEPTED
	// siempre que la fecha de comienzo no haya pasado

	public void cancelApplicationAccepted(final Application application) {
		Assert.notNull(application);
		Assert.isTrue(application.getId() != 0);
		// La fecha de comienzo no debe haber pasado
		final Date currentDate = new Date(System.currentTimeMillis());
		Assert.isTrue(currentDate.before(application.getFixUpTask().getStartDate()));
		// Solo si tiene status ACCEPTED
		Assert.isTrue(application.getStatus().equals("ACCEPTED"));
		application.setStatus("CANCELLED");
	}

	public Collection<Application> getHandyWorkerApplicationsByStatus() {
		final UserAccount userAccount = LoginService.getPrincipal();
		final int id = userAccount.getId();
		return this.applicationRepository.getHandyWorkerApplicationsByStatus(id);
	}

	public Collection<Application> getApplicationsByStatusAndHandyWorkerId(final int handyWorkerId, final String status) {

		return this.applicationRepository.getApplicationsByStatusAndHandyWorkerId(handyWorkerId, status);
	}

	public void changeStatus(final Application a) {
		Assert.notNull(a);
		Customer c = new Customer();

		c = (Customer) this.actorService.findByPrincipal();

		if (a.getStatus().equals("REJECTED"))
			Assert.isTrue(c.equals(this.customerService.getCustomerFromApplicationId(a.getId())));

		this.applicationRepository.save(a);

		//		final HandyWorker h = this.handyWorkerService.findByApplicationId(a.getId());

	}

	public void checkPrincipal(final Application a) {
		Assert.notNull(a);

		Customer c = new Customer();
		HandyWorker h = new HandyWorker();

		if (this.actorService.getType(LoginService.getPrincipal()).equals("HANDYWORKER"))
			h = (HandyWorker) this.actorService.findByPrincipal();
		else if (this.actorService.getType(LoginService.getPrincipal()).equals("CUSTOMER")) {

			c = (Customer) this.actorService.findByPrincipal();

			if (a.getStatus().equals("PENDING"))
				Assert.isTrue(c.equals(this.customerService.getCustomerFromApplicationId(a.getId())));
			else if (a.getStatus().equals("ACCEPTED")) {

				h = this.handyWorkerService.findByApplicationId(a.getId());

				Assert.isTrue(h.equals(h));

			}

		}

	}

	public void cancelApplication(final Application a) {
		Assert.notNull(a);
		Assert.isTrue(a.getStatus().equals("ACCEPTED"));
		this.checkPrincipal(a);
		FixUpTask f;
		final Date currentDate = new Date(System.currentTimeMillis());

		f = this.fixUpTaskService.getFixUpTaskByApplicationId(a.getId());
		Assert.isTrue(f.getStartDate().after(currentDate), "message.error.cancelApplicationDate");

		a.setStatus("CANCELLED");
		this.applicationRepository.save(a);

		//	final HandyWorker h = this.handyWorkerService.findByApplicationId(a.getId());
		//	final Customer c = this.customerService.getCustomerFromApplicationId(a.getId());

	}

	public void addCreditCard(final Application a, final CreditCard c) {
		Assert.notNull(a);
		Assert.notNull(c);
		this.checkPrincipal(a);

		this.creditCardService.save(c);
		a.setCreditcard(c);
		this.applicationRepository.save(a);
	}

	public Double[] getAvgMinMaxStdevApplicationPerFixUpTask() {

		return this.applicationRepository.computeAvgMinMaxStdevApplicationPerFixUpTask();

	}

	public Double[] computeAvgMinMaxStdvPerOfferedPrice() {

		return this.applicationRepository.computeAvgMinMaxStdvPerOfferedPrice();
	}

}
