package controllers.sponsor;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.SponsorService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor/registration")
public class SponsorRegistrationController {

	// Services
	
	@Autowired
	private SponsorService sponsorService;

	// Constructors

	public SponsorRegistrationController() {
		super();
	}

	// Creation

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Sponsor sponsor;

		sponsor = this.sponsorService.create();
		res = this.createEditModelAndView(sponsor);

		return res;
	}

	// Edit
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(sponsor);
		} else {
			try {
//				String nuevo = actorService.checkTelefono(sponsor
//						.getPhoneNumber());
//				sponsor.setPhoneNumber(nuevo);
				this.sponsorService.save(sponsor);
				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				
				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				res = this.createEditModelAndView(sponsor,
						errorMessage);

			}
		}
		return res;

	}

	// Ancillary methods
	
	protected ModelAndView createEditModelAndView(Sponsor sponsor) {
		ModelAndView res;

		res = createEditModelAndView(sponsor, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Sponsor sponsor,
			String message) {
		ModelAndView res;

		res = new ModelAndView("sponsor/registration");
		res.addObject("sponsor", sponsor);
		res.addObject("message", message);

		return res;
	}

}
