package controllers.sponsor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import services.SponsorshipService;
import domain.Sponsor;
import domain.Sponsorship;

@Controller
@RequestMapping("/sponsor")
public class SponsorController {

	// Services

	@Autowired
	private SponsorService		sponsorService;

	@Autowired
	private SponsorshipService	sponsorShipService;


	// Constructor
	
	public SponsorController() {
		super();
	}

	@RequestMapping(value = "/sponsor/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		final Collection<Sponsor> sponsors;
		final Sponsor spo = this.sponsorService.findByPrincipal();
		sponsors = this.sponsorService.findAll();

		res = new ModelAndView("sponsor/list");
		res.addObject("requestURI", "sponsor/list.do");
		res.addObject("sponsors", sponsors);
		res.addObject(spo);

		return res;
	}

	@RequestMapping(value = "/displaySponsor", method = RequestMethod.GET)
	public ModelAndView displayAll(@RequestParam final int sponsorId) {
		ModelAndView result;
		Sponsor sponsor;
		Sponsorship s;

		sponsor = this.sponsorService.findOne(sponsorId);
		s = this.sponsorShipService.randomSponsorship();

		result = new ModelAndView("sponsor/displaySponsor");
		result.addObject("sponsor", sponsor);
		result.addObject("sponsorship", s);

		return result;
	}

}
