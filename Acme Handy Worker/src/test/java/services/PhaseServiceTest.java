// revisar

package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Phase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PhaseServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private PhaseService	phaseService;


	//Tests

	@Test
	public void testCreate() {
		final Phase phase;

		//		phase = this.phaseService.create();
		//	Assert.notNull(phase);
	}

	@Test
	public void testSavePhase() {
		Phase phase;

		phase = this.phaseService.findOne(super.getEntityId("phase1"));

		super.authenticate("handyWorker3");

		phase.setTitle("Títle");
		phase.setDescription("phaseDesc");
		this.phaseService.save(phase);

		System.out.println("Phase guardada:  " + phase);
		super.unauthenticate();
	}

	@Test
	public void testDeletePhase() {
		Phase phase;

		phase = this.phaseService.findOne(super.getEntityId("phase1"));

		super.authenticate("handyWorker3");

		phase.setTitle("TítlePhase1");
		phase.setDescription("DescPhase1");
		this.phaseService.delete(phase);
		super.unauthenticate();
	}

}
