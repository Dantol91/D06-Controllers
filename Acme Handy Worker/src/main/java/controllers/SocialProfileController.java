
package controllers;

import java.util.Collection;

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
@RequestMapping("socialprofile/actor")
public class SocialProfileController extends AbstractController {

	// Services

	@Autowired
	private SocialProfileService	socialProfileService;
	@Autowired
	private ActorService			actorService;


	// List

	@RequestMapping("list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("socialprofile/list");
		final Actor principal = this.actorService.findPrincipal();
		final Collection<SocialProfile> socialProfiles = this.socialProfileService.findAllByActor(principal);
		res.addObject("socialProfiles", socialProfiles);
		res.addObject("requestURI", "socialprofile/actor/list.do");
		this.isPrincipalAuthorizedEdit(res, principal.getId());
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create() {
		final Actor actor = this.actorService.findPrincipal();
		final SocialProfile socialProfile = this.socialProfileService.create(actor);
		final ModelAndView res = this.createEditModelAndView(socialProfile);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer socialProfileId) {
		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
		Assert.notNull(socialProfile);
		final ModelAndView res = this.createEditModelAndView(socialProfile);
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(socialProfile);
		else
			try {
				this.socialProfileService.save(socialProfile);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable t) {
				res = new ModelAndView("cannot.commit.error");
			}
		return res;
	}

	// Delete

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView res = null;
		try {
			this.socialProfileService.delete(socialProfile);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable t) {
			res = new ModelAndView("cannot.commit.error");
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final SocialProfile socialProfile) {
		return this.createEditModelAndView(socialProfile, null);
	}

	private ModelAndView createEditModelAndView(final SocialProfile socialProfile, final String message) {
		final ModelAndView res = new ModelAndView("socialprofile/edit");
		res.addObject("socialProfile", socialProfile);
		res.addObject("message", message);
		this.isPrincipalAuthorizedEdit(res, socialProfile);
		return res;
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final SocialProfile socialProfile) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		if (principal.equals(socialProfile.getActor()))
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Integer actorId) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		final Actor actor = this.actorService.findOne(actorId);
		if (principal.equals(actor))
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
