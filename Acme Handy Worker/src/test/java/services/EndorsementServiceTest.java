
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
import domain.Endorsement;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EndorsementServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private EndorsementService	endorsementService;


	//Tests

	@Test
	public void testCreate() {
		Endorsement endorsement;
		endorsement = this.endorsementService.create();
		Assert.notNull(endorsement);
	}

	@Test
	public void testDelete() {

		final Endorsement endorsement;
		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		this.endorsementService.delete(endorsement);

	}

	@Test
	public void testFindAll() {
		Collection<Endorsement> endorsements;
		endorsements = this.endorsementService.findAll();
		Assert.notEmpty(endorsements);
		Assert.notNull(endorsements);

	}

	@Test
	public void testFindOne() {
		Endorsement endorsement;

		endorsement = this.endorsementService.findOne(super.getEntityId("endorsement1"));
		Assert.notNull(endorsement);

	}

}
