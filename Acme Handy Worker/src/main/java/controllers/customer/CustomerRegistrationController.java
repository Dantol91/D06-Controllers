/*
 * CustomerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.ActorService;
import services.CustomerService;
import services.SocialProfileService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Customer;

@Controller
@RequestMapping("/none/customer")
public class CustomerRegistrationController extends AbstractController {

	// Constructors 

	public CustomerRegistrationController() {
		super();
	}


	//Services

	@Autowired
	CustomerService			customerService;

	@Autowired
	ActorService			actorService;

	@Autowired
	SocialProfileService	socialProfileService;

	@Autowired
	UserAccountService		userAccountService;


	//Create
	// Creation
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Customer customer;

		customer = this.customerService.create();
		result = new ModelAndView("none/customer/create");
		result.addObject("customer", customer);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;

		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		customer.getUserAccount().setPassword(encoder.encodePassword(customer.getUserAccount().getPassword(), null));
		if (binding.hasErrors()) {
			result = this.createEditModelAndView(customer);
			result.addObject("customer", customer);
			result.addObject("message", "customer.commit.error");
		} else
			try {
				this.customerService.save(customer);
				result = new ModelAndView("redirect:/customer/display.do");
			} catch (final Throwable ops) {

				result = new ModelAndView("none/customer/create");
				result.addObject("customer", customer);
				result.addObject("message", "customer.commit.error");
			}

		return result;

	}

	//

	protected ModelAndView createEditModelAndView(final Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer, final String messageCode) {
		final ModelAndView result;
		UserAccount userAccount = new UserAccount();
		userAccount = customer.getUserAccount();

		result = new ModelAndView("none/customer/create");
		result.addObject("customer", customer);
		result.addObject("userAccount", userAccount);
		result.addObject("message", messageCode);

		return result;
	}

}
