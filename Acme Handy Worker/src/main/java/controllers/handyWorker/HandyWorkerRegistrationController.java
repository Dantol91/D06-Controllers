/*
 * HandyWorkerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.ActorService;
import services.HandyWorkerService;
import services.SocialProfileService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.HandyWorker;

@Controller
@RequestMapping("/none/handyWorker")
public class HandyWorkerRegistrationController extends AbstractController {

	// Constructors

	public HandyWorkerRegistrationController() {
		super();
	}


	// -Services

	@Autowired
	HandyWorkerService		handyWorkerService;

	@Autowired
	ActorService			actorService;

	@Autowired
	SocialProfileService	socialProfileService;

	@Autowired
	UserAccountService		userAccountService;


	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final HandyWorker handyWorker;

		handyWorker = this.handyWorkerService.create();
		result = new ModelAndView("none/handyWorker/create");
		result.addObject("handyWorker", handyWorker);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final HandyWorker handyWorker, final BindingResult binding) {
		ModelAndView result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		handyWorker.getUserAccount().setPassword(encoder.encodePassword(handyWorker.getUserAccount().getPassword(), null));
		if (binding.hasErrors()) {
			for (final ObjectError error : binding.getAllErrors())
				System.out.println(error.getDefaultMessage());
			result = this.createEditModelAndView(handyWorker);
			result.addObject("handyWorker", handyWorker);
			result.addObject("message", "handyWorker.commit.error");
		} else
			try {
				final String make = handyWorker.getName() + " " + handyWorker.getMiddleName() + " " + handyWorker.getSurname();
				handyWorker.setMake(make);
				System.out.println(make);
				this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("redirect:/handyWorker/display.do");
			} catch (final Throwable ops) {

				result = new ModelAndView("none/handyWorker/create");
				result.addObject("handyWorker", handyWorker);
				result.addObject("message", "handyWorker.commit.error");
			}

		return result;

	}

	protected ModelAndView createEditModelAndView(final HandyWorker handyWorker) {
		ModelAndView result;

		result = this.createEditModelAndView(handyWorker, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final HandyWorker handyWorker, final String messageCode) {
		final ModelAndView result;
		UserAccount userAccount = new UserAccount();
		userAccount = handyWorker.getUserAccount();

		result = new ModelAndView("none/handyWorker/create");
		result.addObject("handyWorker", handyWorker);
		result.addObject("userAccount", userAccount);
		result.addObject("message", messageCode);

		return result;
	}

}
