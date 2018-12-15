
package services;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Application;
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ApplicationServiceTest extends AbstractTest {

	// Service under test 
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixUpTaskService	fixUpTaskService;


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
		app.setRegisterMoment(registerMoment);

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

	@Test
	public void testCancelApplicationAccepted() {
		super.authenticate("handyWorker1");

		Application app;
		List<FixUpTask> fixUpTasks = new ArrayList<>();
		Application appSaved;

		fixUpTasks = (List<FixUpTask>) this.fixUpTaskService.findAll();
		app = this.applicationService.create();

		app.setStatus("ACCEPTED");
		app.setComment("comment1");

		app.setFixUpTask(fixUpTasks.get(0));
		app.getFixUpTask().setStartDate(new Date(System.currentTimeMillis() + 10000));

		appSaved = this.applicationService.save(app);

		appSaved.getFixUpTask().setStartDate(Date.valueOf("2019-01-02"));

		this.applicationService.cancelApplicationAccepted(appSaved);
		Assert.isTrue(appSaved.getStatus().equals("CANCELLED"));
		super.authenticate(null);

	}
}
