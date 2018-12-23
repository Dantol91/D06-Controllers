
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SocialProfileService;
import domain.Actor;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile")
public class SocialProfileController {

	// Services 
	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ActorService			actorService;


	// Constructors 

	public SocialProfileController() {
		super();
	}

	// Listing 

	// Creation

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();
		result = this.createEditModelAndView(socialProfile);
		return result;
	}

	// Edition 

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int socialProfileId) {
		ModelAndView result;

		final SocialProfile socialProfile = this.socialProfileService.findOneToEdit(socialProfileId);
		Assert.notNull(socialProfile);

		result = this.createEditModelAndView(socialProfile);
		final Actor actor = this.actorService.findByPrincipal();
		result.addObject(actor);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;
		String type;

		type = this.actorService.getType(this.actorService.findByPrincipal().getUserAccount()).toLowerCase();
		result = new ModelAndView("redirect:/actor/" + type + "/edit.do");

		if (binding.hasErrors())
			result = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialProfileService.save(socialProfile);
				this.socialProfileService.addToPrincipal(socialProfile);
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;
		String type;

		type = this.actorService.getType(this.actorService.findByPrincipal().getUserAccount()).toLowerCase();
		result = new ModelAndView("redirect:/actor/" + type + "/edit.do");

		try {
			this.socialProfileService.delete(socialProfile);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(socialProfile, "socialProfile.commit.error");
		}
		return result;
	}

	// Ancillary methods 

	private ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		ModelAndView result;

		result = this.createEditModelAndView(socialProfile, null);

		return result;
	}

	private ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String message) {
		ModelAndView result;

		result = new ModelAndView("socialProfile/edit");
		result.addObject("socialProfile", socialProfile);

		return result;
	}

}
