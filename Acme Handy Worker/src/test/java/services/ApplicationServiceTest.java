
package services;

import java.sql.Date;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Service under test 
	@Autowired
	private ApplicationService	applicationService;


	// Tests 

	@Test
	public void testCreate() {
		Application app;
		app = this.applicationService.create();
		Assert.notNull(app);
	}

	@Test
	public void testSaveApplication() {
		final Application app, appsaved;
		final String status;
		final Date registerMoment;

		super.authenticate("handyworker1");
		app = this.applicationService.create();

		status = "ACCEPTED";
		registerMoment = new Date(0);

		app.setStatus(status);
		//	app.setRegisterMoment(registerMoment);

		appsaved = this.applicationService.save(app);

		super.authenticate(null);

	}

	@Test
	public void testFindAll() {
		final Collection<Application> apps;
		apps = this.applicationService.findAll();
		Assert.notEmpty(apps);
		Assert.notNull(apps);

	}

	@Test
	public void testFindOne() {
		Application app;

		app = this.applicationService.findOne(super.getEntityId("application2"));
		Assert.notNull(app);

	}

}
