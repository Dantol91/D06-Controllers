
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ApplicationRepository;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Application;
import domain.Customer;
import domain.FixupTask;
import domain.Folder;
import domain.HandyWorker;
import domain.Message;

@Service
@Transactional
public class ApplicationService {

	//Managed Repository

	@Autowired
	private ApplicationRepository	applicationRepository;

	// Supporting Service

	@Autowired
	private MessageService			messageService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private FixupTaskService		fixupTaskService;
	@Autowired
	private ServiceUtils			serviceUtils;
	@Autowired
	private LoginService			loginService;

	@Autowired
	private ActorService			actorService;


	// Constructors
	public ApplicationService() {
		super();
	}

	// Simple CRUD methods

	public Application create() {
		final Application a = new Application();
		HandyWorker hw;
		hw = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		a.setHandyWorker(hw);
		a.setStatus("PENDING");
		a.setMoment(new Date(System.currentTimeMillis() - 1000));
		return a;
	}

	public Collection<Application> findAll() {
		return this.applicationRepository.findAll();
	}

	public Application findOne(final int applicationId) {
		return this.applicationRepository.findOne(applicationId);
	}

	public Application save(final Application app) {
		final Application application = (Application) this.serviceUtils.checkObjectSave(app);
		final String applicationStatus = application.getStatus();
		if (!applicationStatus.equals("PENDING"))
			app.setStatus(applicationStatus);
		Application res;

		final FixupTask f = app.getFixupTask();
		if (this.getTieneYaACCEPTED(f))
			throw new IllegalArgumentException("Only one application among all the applications for a fixup task can be accepted");
		//
		if (app.getStatus().equals("ACCEPTED"))

			Assert.notNull(app.getCreditCard());
		if (!app.getStatus().equals(applicationStatus)) {

			Assert.notNull(app.getHandyWorker());
			Assert.notNull(app.getFixupTask().getCustomer());

		}
		res = this.applicationRepository.save(app);
		this.NotificationMessage(app);

		return res;
	}
	private boolean getTieneYaACCEPTED(final FixupTask f) {
		Boolean res = false;
		for (final Application a : this.findAll(f))
			if (a.getStatus().equals("ACCEPTED"))
				res = true;
		return res;
	}

	//Other Methods

	public Map<String, Double> applicationPriceStats() {
		final Double[] statistics = this.applicationRepository.applicationPriceStats();
		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;
	}

	public Map<String, Double> pendingRatio() {
		final Double ratio = this.applicationRepository.pendingRatio();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
	}

	public Map<String, Double> acceptedRatio() {
		final Double ratio = this.applicationRepository.acceptedRatio();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
	}

	public Map<String, Double> appsRejectedRatio() {
		final Double ratio = this.applicationRepository.acceptedRatio();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
	}

	public Double lateApplicationsRatio() {
		final Double ratio = this.applicationRepository.lateApplicationsRatio();

		return ratio;
	}

	public Application changeStatus(final Application a, final String status) {
		Assert.notNull(a);
		Assert.notNull(status);
		if (a.getStatus().equals("PENDING") && (status.equals("ACCEPTED") || status.equals("REJECTED"))) {
			a.setStatus(status);
			this.save(a);
		} else
			System.out.println("No se puede cambiar a ese status");

		this.save(a);
		return a;
	}

	public void NotificationMessage(final Application a) {
		final Administrator system = (Administrator) this.actorService.findOneByUserAccount((UserAccount) this.loginService.loadUserByUsername("system"));
		final Customer customer = a.getFixupTask().getCustomer();
		final HandyWorker handyWorker = a.getHandyWorker();
		final Message messageHW = this.messageService.create(new Folder());
		messageHW.setPriority("NEUTRAL");
		messageHW.setReceiver(handyWorker);
		messageHW.setSender(system);
		messageHW.setBody("The status of your application for the fixup task " + a.getFixupTask().getTicker() + " has changed to " + a.getStatus() + "\n\n" + "El estado de tu solicitud para la tarea " + a.getFixupTask().getTicker() + "ha cambiado a "
			+ a.getStatus());
		messageHW.setSubject("An application's status has changed");
		messageHW.setTags(new ArrayList<String>());
		final Message messageC = this.messageService.create(new Folder());
		messageC.setPriority("NEUTRAL");
		messageC.setReceiver(customer);
		messageC.setSender(system);
		messageC.setBody("The status of the application for your fixup task " + a.getFixupTask().getTicker() + " from the handy worker " + a.getHandyWorker().getMake() + " has changed to " + a.getStatus() + "\n\n"
			+ "El estado de la solicitud para tu tarea " + a.getFixupTask().getTicker() + " del trabajador " + a.getHandyWorker().getMake() + " ha cambiado a " + a.getStatus());
		messageC.setSubject("An application's status has changed");
		messageC.setTags(new ArrayList<String>());
		this.messageService.save(messageC);
		this.messageService.save(messageHW);
	}

	public void flush() {
		this.applicationRepository.flush();
	}

	public Collection<Application> findApplicationsByHandyWorker(final HandyWorker h) {
		Assert.notNull(h);
		Assert.isTrue(h.getId() > 0);
		Assert.notNull(this.handyWorkerService.findOne(h.getId()));
		return this.applicationRepository.findApplicationsByHandyWorker(h.getId());
	}

	public Collection<Application> findAll(final FixupTask f) {
		Assert.notNull(f);
		Assert.isTrue(f.getId() > 0);
		Assert.notNull(this.fixupTaskService.findOne(f.getId()));
		return this.applicationRepository.findApplicationsByHandyWorker(f.getId());
	}

	public Application create(final FixupTask dependency) {
		final Application res = this.create();
		res.setFixupTask(dependency);
		return res;
	}

}
