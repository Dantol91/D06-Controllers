
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CustomerServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private CustomerService	customerService;


	//Test

	@Test
	public void testCreateCustomer() {
		final Customer customer;
		customer = this.customerService.create();
		Assert.notNull(customer);
	}
	@Test
	public void testSaveCustomer() {
		super.authenticate("customer1");
		final Customer customer, customerSaved;

		customer = this.customerService.findOne(super.getEntityId("customer1"));

		customer.setAddress("Calle Menendez Garcia, Bloque 1, 2B");
		customer.setEmail("maran@gmail.com");
		customer.setSurname("Villalba");

		customerSaved = this.customerService.save(customer);

		Assert.notNull(customerSaved);
		super.unauthenticate();
	}

	@Test
	public void testFindAllCustomer() {
		Collection<Customer> customers;
		customers = this.customerService.findAll();
		Assert.notEmpty(customers);
		Assert.notNull(customers);

	}

	@Test
	public void testFindOnecustomer() {
		Customer customer;

		customer = this.customerService.findOne(super.getEntityId("customer1"));
		Assert.notNull(customer);

	}

}
