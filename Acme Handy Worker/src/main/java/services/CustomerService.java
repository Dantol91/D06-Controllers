
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.UserAccount;
import domain.Application;
import domain.Complaint;
import domain.Customer;
import domain.FixupTask;
import domain.Note;
import domain.Report;
import domain.Settings;
import domain.Url;

@Service
@Transactional
public class CustomerService {

	// Managed repository 

	@Autowired
	private CustomerRepository	customerRepository;

	// Supporting services 

	@Autowired
	private FolderService		folderService;

	@Autowired
	private ServiceUtils		serviceUtils;

	@Autowired
	private FixupTaskService	fixupTaskService;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private ReportService		reportService;

	@Autowired
	private SettingsService		settingsService;


	// Constructors 

	public CustomerService() {
		super();
	}

	// Simple CRUD methods 

	public Customer create() {
		Customer result;
		result = new Customer();

		result.setUserAccount(new UserAccount());
		final Authority authority = new Authority();
		authority.setAuthority(Authority.CUSTOMER);
		result.getUserAccount().addAuthority(authority);

		result.getUserAccount().setBanned(false);
		result.setSuspicious(false);
		return result;
	}

	public Customer findOne(final int customerId) {
		Customer res;

		res = this.customerRepository.findOne(customerId);
		Assert.notNull(res);

		return res;

	}

	public Collection<Customer> findAll() {
		Collection<Customer> res;

		res = this.customerRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Customer save(final Customer customer) {

		Assert.notNull(customer);
		Boolean isCreating = null;

		if (customer.getId() == 0) {
			isCreating = true;
			customer.setSuspicious(false);

		} else {
			isCreating = false;

			this.serviceUtils.checkIdSave(customer);

			Customer customerBD;
			Assert.isTrue(customer.getId() > 0);

			customerBD = this.customerRepository.findOne(customer.getId());

			customer.setSuspicious(customerBD.getSuspicious());
			customer.setUserAccount(customerBD.getUserAccount());

			this.serviceUtils.checkAuthority("CUSTOMER");
			this.serviceUtils.checkActor(customer);

		}
		if ((!customer.getPhone().startsWith("+")) && StringUtils.isNumeric(customer.getPhone()) && customer.getPhone().length() > 3) {
			final Settings settings = this.settingsService.findSettings();
			customer.setPhone(settings.getCountryCode() + customer.getPhone());
		}
		Customer res;

		res = this.customerRepository.save(customer);
		this.flush();
		if (isCreating)
			this.folderService.createSystemFolders(res);
		return res;
	}

	// Other business methods

	public Collection<Customer> getTop3CustomerWithMoreComplaints() {
		final Collection<Customer> ratio = new ArrayList<>();

		final Collection<Customer> customersCompl = this.customerRepository.getTop3CustomerWithMoreComplaints();
		int i = 0;
		for (final Customer c : customersCompl)
			if (i < 3) {
				ratio.add(c);
				i++;

			}
		return ratio;
	}

	public Collection<Customer> listCustomer10() {
		final Collection<Customer> list = this.customerRepository.listCustomer10();
		return list;
	}

	public Collection<FixupTask> getAllFixupTasks() {
		Collection<FixupTask> res;
		res = this.fixupTaskService.findAll();
		return res;
	}

	public Collection<FixupTask> getFixupTaskByCustomer(final Customer c) {
		final Collection<FixupTask> res = this.fixupTaskService.findByCustomer(c);
		return res;
	}

	public void banActor(final Customer a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.getUserAccount().setBanned(true);
		this.customerRepository.save(a);

	}

	public void unBanActor(final Customer a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.getUserAccount().setBanned(false);
		this.customerRepository.save(a);

	}

	public void flush() {
		this.customerRepository.flush();
	}

	public boolean isSuspicious(final Customer c) {
		final Customer customer = (Customer) this.serviceUtils.checkObject(c);
		Boolean res = false;
		for (final FixupTask f : customer.getFixupTasks()) {
			if (this.actorService.containsSpam(f.getDescription())) {
				res = true;
				break;
			}
			for (final Application a : f.getApplications())
				if (this.actorService.containsSpam(a.getCustomerComments()) || this.actorService.containsSpam(a.getCreditCard().getBrandName()) || this.actorService.containsSpam(a.getCreditCard().getHolderName())) {
					res = true;
					break;
				}
			for (final Complaint com : f.getComplaints()) {
				for (final Url u : com.getAttachments())
					if (this.actorService.containsSpam(u.getUrl())) {
						res = true;
						break;
					}
				if (this.actorService.containsSpam(com.getDescription())) {
					res = true;
					break;
				}
				final Report report = this.reportService.findByComplaint(com);
				for (final Note n : report.getNotes())
					for (final String comment : n.getComments())
						if (this.actorService.containsSpam(comment)) {
							res = true;
							break;
						}
			}
		}
		return res;
	}
}
