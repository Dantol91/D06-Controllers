
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CustomerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Customer;
import domain.FixUpTask;
import domain.SocialProfile;

@Service
@Transactional
public class CustomerService {

	// Managed repository
	@Autowired
	private CustomerRepository		customerRepository;

	// Supported services

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructor
	public CustomerService() {
		super();
	}

	// Simple CRUD methods
	public Customer create() {
		final Collection<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		final Customer c;
		UserAccount ua;
		Authority auth;

		c = new Customer();
		ua = this.userAccountService.create();
		auth = new Authority();

		auth.setAuthority("CUSTOMER");
		ua.addAuthority(auth);
		c.setUserAccount(ua);
		c.setSocialProfiles(socialProfiles);
		c.setSuspicious(false);

		return c;
	}

	public Customer save(Customer customer) {
		Assert.notNull(customer);
		if (customer.getId() == 0)
			Assert.isTrue(this.userAccountService.findByUsername(customer.getUserAccount().getUsername()) == null, "message.error.duplicatedUser");
		Boolean create = false;

		// Comprobamos si se está creando el user
		if (customer.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			customer.getUserAccount().setPassword(encoder.encodePassword(customer.getUserAccount().getPassword(), null));
		}

		if (customer.getPhone() != null) {
			final String tlf = this.configurationService.checkPhoneNumber(customer.getPhone());
			customer.setPhone(tlf);
		}
		if (customer.getId() == 0)
			customer.getUserAccount().setBanned(false);

		customer = this.customerRepository.save(customer);
		if (create)
			this.boxService.createSystemBoxes(customer);

		return customer;
	}

	public Collection<Customer> findAll() {
		return this.customerRepository.findAll();
	}

	public Customer findOne(final int customerId) {
		Customer result;
		result = this.customerRepository.findOne(customerId);
		return result;
	}

	// Other Business Methods

	public Customer getCustomerFromApplicationId(final int applicationId) {

		return this.customerRepository.getCustomerFromApplicationId(applicationId);
	}

	public Customer getTopThreeCustomersComplaints() {

		return this.customerRepository.getTopThreeCustomersComplaints();

	}

	//The listing of customers who have published at least 10% more fix-up tasks than the average, 
	//ordered by number of applications.
	public Collection<Customer> getCustomerMoreFixUpTasks() {

		return this.customerRepository.getCustomerMoreFixUpTasks();

	}

	public Collection<Customer> getEndorseCustomers(final int handyWorkerId) {
		Collection<Customer> results;

		results = this.customerRepository.getEndorseCustomers(handyWorkerId);

		return results;
	}

	protected void getFixUpTasksCustomer(final Customer customer, final FixUpTask f) {
		Collection<FixUpTask> fixUpTasks;

		fixUpTasks = customer.getFixUpTasks();
		fixUpTasks.add(f);

		customer.setFixUpTasks(fixUpTasks);
	}

	public Customer findByPrincipal() {
		Customer res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Customer findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Customer res;
		res = this.customerRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Customer findByUserAccountId(final int userAccountId) {
		return this.customerRepository.findByUserAccountId(userAccountId);
	}

}
