
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
import domain.Referee;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class RefereeServiceTest extends AbstractTest {

	// Service under test 

	@Autowired
	private RefereeService	refereeService;


	// Tests 

	@Test
	public void testCreate() {
		Referee referee;
		referee = this.refereeService.create();
		Assert.notNull(referee);
	}

	@Test
	public void testSaveReferee() {
		super.authenticate("referee1");
		final Referee referee, refereeSaved;

		referee = this.refereeService.findOne(super.getEntityId("referee1"));

		referee.setAddress("Pasaje de Huelva, Bloque 1, 1B");
		referee.setEmail("darex@gmail.com");
		referee.setSurname("Toledo");

		refereeSaved = this.refereeService.save(referee);

		Assert.notNull(refereeSaved);
		super.unauthenticate();
	}

	@Test
	public void testDelete() {

		final Referee referee;
		referee = this.refereeService.findOne(super.getEntityId("referee1"));
		//		this.refereeService.delete(referee);

	}

	@Test
	public void testFindAll() {
		Collection<Referee> referees;
		referees = this.refereeService.findAll();
		Assert.notEmpty(referees);
		Assert.notNull(referees);

	}

}
