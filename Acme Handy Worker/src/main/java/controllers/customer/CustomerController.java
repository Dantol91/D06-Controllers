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
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.CustomerService;
import services.SocialProfileService;
import services.UserAccountService;
import controllers.AbstractController;
import domain.Customer;

@Controller
@RequestMapping("/customer")
public class CustomerController extends AbstractController {

	// Constructors 

	public CustomerController() {
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


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Customer customer;

		customer = (Customer) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		result = new ModelAndView("customer/display");
		result.addObject("customer", customer);

		return result;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int customerId) {
		ModelAndView result;
		Customer customer;

		customer = this.customerService.findOne(customerId);
		Assert.notNull(customer);
		result = this.createEditModelAndView(customer);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Customer customer, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			result = this.createEditModelAndView(customer);
		} else

			try {

				this.customerService.save(customer);
				result = new ModelAndView("redirect:display.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(customer, "customer.commit.error");
			}

		return result;
	}
	// Create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		final Customer customer;

		customer = this.customerService.create();

		result = new ModelAndView("customer/create");
		result.addObject("customer", customer);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer) {
		ModelAndView result;

		result = this.createEditModelAndView(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Customer customer, final String messageCode) {
		final ModelAndView result;
		UserAccount userAccount = new UserAccount();
		userAccount = customer.getUserAccount();

		result = new ModelAndView("customer/edit");
		result.addObject("customer", customer);
		result.addObject("userAccount", userAccount);
		result.addObject("message", messageCode);

		return result;
	}

}
