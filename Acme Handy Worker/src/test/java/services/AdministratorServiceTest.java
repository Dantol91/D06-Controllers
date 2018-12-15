
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.UserAccount;
import utilities.AbstractTest;
import domain.Administrator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class AdministratorServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testCreate() {
		Administrator admin;

		admin = this.administratorService.create();

		Assert.notNull(admin);
		Assert.notNull(admin.getUserAccount());
		Assert.isNull(admin.getAddress());
		Assert.isNull(admin.getEmail());
		Assert.isNull(admin.getMiddleName());
		Assert.isNull(admin.getName());
		Assert.isNull(admin.getPhone());
		Assert.isNull(admin.getPhoto());
		Assert.isNull(admin.getSurname());
		Assert.isTrue(admin.getSuspicious() == false);

	}

	@Test
	public void testAdminSave() {
		Administrator admin, AdminSaved, AdminFindOne;
		UserAccount userAccount;

		admin = this.administratorService.create();
		admin.setAddress("Calle Araquil");
		admin.setEmail("prueba@gmail.com");
		admin.setName("admin1");
		admin.setPhone("+34 697345611");
		admin.setSurname("Surname 1");

		userAccount = admin.getUserAccount();
		userAccount.setUsername("customer1");
		userAccount.setPassword("customer1");

		AdminSaved = this.administratorService.save(admin);

		AdminFindOne = this.administratorService.findOne(AdminSaved.getId());

		Assert.notNull(AdminFindOne);

		System.out.println("AdminSaved: " + this.administratorService.save(admin));
	}

}
