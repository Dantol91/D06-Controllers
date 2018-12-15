
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
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private HandyWorkerService	handyWorkerService;


	//Test

	@Test
	public void testCreateHandyWorker() {
		final HandyWorker handyWorker;
		handyWorker = this.handyWorkerService.create();
		Assert.notNull(handyWorker);
	}

	@Test
	public void testSaveHandyWorker() {
		super.authenticate("handyWorker1");
		final HandyWorker handyWorker, handyWorkerSaved;

		handyWorker = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));

		handyWorker.setAddress("Pasaje del Guadalquivir, Bloque 1, 3B");
		handyWorker.setEmail("perte@gmail.com");
		handyWorker.setSurname("Perez");

		handyWorkerSaved = this.handyWorkerService.save(handyWorker);

		Assert.notNull(handyWorkerSaved);
		super.unauthenticate();
	}

	@Test
	public void testFindAllHandyWorker() {
		Collection<HandyWorker> handyWorkers;
		handyWorkers = this.handyWorkerService.findAll();
		Assert.notEmpty(handyWorkers);
		Assert.notNull(handyWorkers);

		System.out.println("findAllHandyWorker:  " + handyWorkers);

	}

	@Test
	public void testFindOneHandyWorker() {
		final HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.findOne(super.getEntityId("handyWorker1"));
		Assert.notNull(handyWorker);

	}

}
