
package controllers.referee;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;


import services.ActorService;
import services.RefereeService;
import domain.Actor;
import domain.Administrator;
import domain.Referee;

@Controller
@RequestMapping("referee")
public class RefereeController extends AbstractController {

	// Services

	@Autowired
	private RefereeService	refereeService;
	@Autowired
	private ActorService	actorService;


	// List

	@RequestMapping("/referee/profile")
	public ModelAndView profile(@RequestParam(required = true) final Integer refereeId) {
		final ModelAndView res = new ModelAndView("referee/profile");
		final Referee referee = this.refereeService.findOne(refereeId);
		res.addObject("referee", referee);
		res.addObject("requestURI", "referee/profile.do");
		this.isPrincipalAuthorizedEdit(res, referee);
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("administrator/create")
	private ModelAndView create() {
		final Referee referee = this.refereeService.create();
		final ModelAndView res = this.createEditModelAndView(referee);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("referee-administrator/edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer refereeId) {
		final Referee referee = this.refereeService.findOne(refereeId);
		Assert.notNull(referee);
		final ModelAndView res = this.createEditModelAndView(referee);
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "referee-administrator/edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final Referee referee, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(referee);
		else
			try {
				this.refereeService.save(referee);
				res = new ModelAndView("redirect:/");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(referee, "cannot.commit.error");
			}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final Referee referee) {
		return this.createEditModelAndView(referee, null);
	}

	private ModelAndView createEditModelAndView(final Referee referee, final String message) {
		final ModelAndView res = new ModelAndView("referee/edit");
		res.addObject("referee", referee);
		res.addObject("message", message);
		this.isPrincipalAuthorizedEdit(res, referee);
		return res;
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Referee referee) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		if (referee.getId() == 0)
			if (principal instanceof Administrator)
				res = true;
			else if (principal.equals(referee))
				res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
