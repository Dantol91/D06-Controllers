
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixupTaskRepository;
import security.Authority;
import security.LoginService;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixupTask;
import domain.HandyWorker;
import domain.Warranty;

@Service
@Transactional
public class FixupTaskService {

	//Managed Repository

	@Autowired
	private FixupTaskRepository	fixupTaskRepository;

	// Supporting Service

	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private WarrantyService		warrantyService;
	@Autowired
	private ServiceUtils		serviceUtils;
	@Autowired
	private ActorService		actorService;
	@Autowired
	TickerService				ticketableService;


	//

	public FixupTaskService() {
		super();
	}
	// Simple CRUD methods

	public FixupTask create() {
		final FixupTask fixupTask = new FixupTask();

		final Collection<Application> applications = new ArrayList<>();
		final Collection<Complaint> complaints = new ArrayList<>();

		fixupTask.setApplications(applications);
		fixupTask.setComplaints(complaints);
		fixupTask.setMoment(new Date(System.currentTimeMillis() - 1000));
		fixupTask.setCustomer((Customer) this.actorService.findOneByUserAccount(LoginService.getPrincipal()));
		fixupTask.setTicker(this.ticketableService.createTicker());
		fixupTask.setWarranty(new Warranty());

		return fixupTask;
	}

	public Collection<FixupTask> findAll() {
		Collection<FixupTask> ft;
		ft = this.fixupTaskRepository.findAll();
		return ft;
	}

	public FixupTask findOne(final int fixupTaskId) {
		FixupTask res;
		res = this.fixupTaskRepository.findOne(fixupTaskId);
		return res;

	}

	public FixupTask save(final FixupTask fixupTask) {

		Assert.notNull(fixupTask);

		if (fixupTask.getId() == 0) {
		} else {
			this.serviceUtils.checkIdSave(fixupTask);
			final FixupTask fBD;
			Assert.isTrue(fixupTask.getId() > 0);
			fBD = this.fixupTaskRepository.findOne(fixupTask.getId());

			fixupTask.setCustomer(fBD.getCustomer());
		}

		FixupTask res;
		res = this.fixupTaskRepository.save(fixupTask);
		return res;
	}
	public void flush() {
		this.fixupTaskRepository.flush();
	}
	public void delete(final FixupTask f) {
		Assert.notNull(f);
		//Assert.isTrue(p.getId() != 0);
		this.fixupTaskRepository.delete(f);
	}

	//Other methods

	public Map<String, Double> appsStats() {
		final Double[] statistics = this.fixupTaskRepository.appsStats();
		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);
		return res;
	}

	public Map<String, Double> maxFixupStaskStats() {
		final Double[] statistics = this.fixupTaskRepository.maxFixupStaskStats();
		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;
	}

	public Map<String, Double> getRatioFixupTasksWithComplaints() {
		final Double ratio = this.fixupTaskRepository.ratiofixupComplaint();
		final Map<String, Double> res = new HashMap<>();

		res.put("Ratio", ratio);

		return res;
	}

	public Map<String, Double> fixupComplaintsStats() {
		final Double[] statistics = this.fixupTaskRepository.fixupComplaintsStats();
		final Map<String, Double> res = new HashMap<>();
		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

	public Collection<FixupTask> findByCategory(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() > 0);
		Assert.notNull(this.categoryService.findOne(category.getId()));
		return this.fixupTaskRepository.findByCategoryId(category.getId());
	}

	public Collection<FixupTask> findByCustomer(final Customer customer) {
		Assert.notNull(customer);
		Assert.isTrue(customer.getId() > 0);
		Assert.notNull(this.customerService.findOne(customer.getId()));
		return this.fixupTaskRepository.findByCustomerId(customer.getId());
	}

	public Collection<FixupTask> findByWarranty(final Warranty warranty) {
		Assert.notNull(warranty);
		Assert.isTrue(warranty.getId() > 0);
		Assert.notNull(this.warrantyService.findOne(warranty.getId()));
		return this.fixupTaskRepository.findByWarrantyId(warranty.getId());
	}

	public Collection<FixupTask> findAcceptedFixupTasks() {
		return this.fixupTaskRepository.findAcceptedFixupTasks();
	}

	public Collection<FixupTask> findAcceptedFixupTasksByHandyWorker(final HandyWorker h) {
		final HandyWorker handyWorker = (HandyWorker) this.serviceUtils.checkObject(h);
		return this.fixupTaskRepository.findAcceptedFixupTasksByHandyWorker(handyWorker.getId());
	}

	public Collection<FixupTask> findFixupTasksNotAppliedByHandyWorker(final HandyWorker h) {
		final HandyWorker handyWorker = (HandyWorker) this.serviceUtils.checkObject(h);
		return this.fixupTaskRepository.findFixupTasksNotAppliedByHandyWorker(handyWorker.getId());
	}

	public Collection<FixupTask> search(final String keyword, final Category category, final Warranty warranty, final Double minPrice, final Double maxPrice, final Date minDate, final Date maxDate) {
		final Collection<FixupTask> res = this.findAll();
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		System.out.println(keyword);
		System.out.println(category);
		System.out.println(warranty);
		System.out.println(minDate);
		System.out.println(maxDate);
		System.out.println(minPrice);
		System.out.println(maxPrice);
		if (!StringUtils.isEmpty(keyword)) {
			final Collection<FixupTask> keywordRes = this.fixupTaskRepository.findByKeyword(keyword);
			res.retainAll(keywordRes);
		}
		if (category != null) {
			this.serviceUtils.checkObject(category);
			final Collection<FixupTask> categoryRes = this.fixupTaskRepository.findByCategoryId(category.getId());
			res.retainAll(categoryRes);
		}
		if (warranty != null) {
			this.serviceUtils.checkObject(warranty);
			final Collection<FixupTask> warrantyRes = this.fixupTaskRepository.findByWarrantyIdAndNotDraft(warranty.getId());
			res.retainAll(warrantyRes);
		}
		if (minPrice != null) {
			final Collection<FixupTask> minPriceRes = this.fixupTaskRepository.findByMoreThanMinPrice(minPrice);
			res.retainAll(minPriceRes);
		}
		if (maxPrice != null) {
			final Collection<FixupTask> maxPriceRes = this.fixupTaskRepository.findByLessThanMaxPrice(maxPrice);
			res.retainAll(maxPriceRes);
		}
		if (minDate != null) {
			final Collection<FixupTask> minDateRes = this.fixupTaskRepository.findByAfterMinDate(minDate);
			res.retainAll(minDateRes);
		}
		if (maxDate != null) {
			final Collection<FixupTask> maxDateRes = this.fixupTaskRepository.findByBeforeMaxDate(maxDate);
			res.retainAll(maxDateRes);
		}
		System.out.println(res);
		return res;
	}
}
