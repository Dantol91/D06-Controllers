
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
import domain.Sponsor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private SponsorService	sponsorService;


	//Test

	@Test
	public void testCreateSponsor() {
		final Sponsor sponsor;
		sponsor = this.sponsorService.create();
		Assert.notNull(sponsor);
	}

	@Test
	public void testSaveSponsor() {
		super.authenticate("sponsor1");
		final Sponsor sponsor, sponsorSaved;

		sponsor = this.sponsorService.findOne(super.getEntityId("sponsor1"));

		sponsor.setAddress("Pasaje de Granada, Bloque 1, 2B");
		sponsor.setEmail("danex@gmail.com");
		sponsor.setSurname("Herrera");

		sponsorSaved = this.sponsorService.save(sponsor);

		Assert.notNull(sponsorSaved);
		super.unauthenticate();
	}

	@Test
	public void testFindAllSponsor() {
		Collection<Sponsor> sponsors;
		sponsors = this.sponsorService.findAll();
		Assert.notEmpty(sponsors);
		Assert.notNull(sponsors);

	}

	@Test
	public void testFindOneSponsor() {
		Sponsor sponsor;

		sponsor = this.sponsorService.findOne(super.getEntityId("sponsor1"));
		Assert.notNull(sponsor);

	}

}
