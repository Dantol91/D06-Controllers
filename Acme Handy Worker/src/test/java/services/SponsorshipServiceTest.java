
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.CreditCard;
import domain.Sponsor;
import domain.Sponsorship;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class SponsorshipServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private SponsorshipService	sponsorshipService;

	@Autowired
	private CreditCardService	creditcardService;


	//Tests

	@Test
	public void createAndSaveSponsorship() {
		this.authenticate("sponsor1");

		final Sponsorship sp = this.sponsorshipService.create();
		sp.setBannerURL("http://as.com");

		final Collection<CreditCard> crt = this.creditcardService.findAll();
		final List<CreditCard> s = new ArrayList<CreditCard>(crt);
		final CreditCard sps = s.get(0);
		sp.setCreditCard(this.creditcardService.findOne(sps.getId()));
		final List<Sponsor> sponsors = new ArrayList<Sponsor>(this.sponsorService.findAll());
		sp.setSponsor(sponsors.get(0));

		this.sponsorshipService.save(sp);

		System.out.println("Search Sponsorship: " + this.sponsorshipService.save(sp));
	}

	@Test
	public void testFindAllSponsorship() {

		final Collection<Sponsorship> sp = this.sponsorshipService.findAll();
		final List<Sponsorship> s = new ArrayList<Sponsorship>(sp);
		final Sponsorship sps = s.get(0);
		this.sponsorshipService.findOne(sps.getId());

		System.out.println("Search Sponsorship: " + this.sponsorshipService.findOne(sps.getId()));
	}

	@Test
	public void testDeleteSponsorship() {
		final Collection<Sponsorship> sp = this.sponsorshipService.findAll();
		final List<Sponsorship> s = new ArrayList<Sponsorship>(sp);
		final Sponsorship sps = s.get(0);
		this.sponsorshipService.delete(sps);

	}
}
