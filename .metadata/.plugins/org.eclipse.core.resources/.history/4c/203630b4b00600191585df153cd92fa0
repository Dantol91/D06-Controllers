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
public class SocialPrtofileController {

	// Services 
	@Autowired
	private SocialProfileService socialProfileService;
	@Autowired
	private ActorService actorService;

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

		socialProfile = socialProfileService.create();
		result = createEditModelAndView(socialProfile);
		return result;
	}

	// Edition 

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int socialProfileId) {
		ModelAndView result;

		SocialProfile socialProfile = socialProfileService
				.findOneToEdit(socialProfileId);
		Assert.notNull(socialProfile);

		result = createEditModelAndView(socialProfile);
		Actor actor = actorService.findByPrincipal();
		result.addObject(actor);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid SocialProfile socialProfile,
			BindingResult binding) {
		ModelAndView result;
		String type;
		
		type = actorService.getType(actorService.findByPrincipal().getUserAccount()).toLowerCase();
		result = new ModelAndView("redirect:/actor/" + type + "/edit.do");

		if (binding.hasErrors()) {
			result = createEditModelAndView(socialProfile);
		} else {
			try {
				socialProfileService.save(socialProfile);
				socialProfileService.addToPrincipal(socialProfile);
			} catch (Throwable oops) {
				result = createEditModelAndView(socialProfile,
						"socialProfile.commit.error");
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(SocialProfile socialProfile,
			BindingResult binding) {
		ModelAndView result;
		String type;
		
		type = actorService.getType(actorService.findByPrincipal().getUserAccount()).toLowerCase();
		result = new ModelAndView("redirect:/actor/" + type + "/edit.do");

		try {
			socialProfileService.delete(socialProfile);
		} catch (Throwable oops) {
			result = createEditModelAndView(socialProfile,
					"socialProfile.commit.error");
		}
		return result;
	}

	// Ancillary methods 

	private ModelAndView createEditModelAndView(SocialProfile socialProfile) {
		ModelAndView result;

		result = createEditModelAndView(socialProfile, null);

		return result;
	}

	private ModelAndView createEditModelAndView(SocialProfile socialProfile,
			String message) {
		ModelAndView result;

		result = new ModelAndView("socialProfile/edit");
		result.addObject("socialProfile", socialProfile);

		return result;
	}

}
