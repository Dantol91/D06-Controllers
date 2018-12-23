package controllers.customer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CustomerService;
import domain.Customer;

@Controller
@RequestMapping("/customer/registration")
public class CustomerRegistrationController {

	// Services

	@Autowired
	private CustomerService customerService;

	// Constructors

	public CustomerRegistrationController() {
		super();
	}

	// Creation

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Customer customer;

		customer = this.customerService.create();
		res = this.createEditModelAndView(customer);

		return res;
	}

	// Edit
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Customer customer, BindingResult binding) {
		ModelAndView res;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(customer);
		} else {
			try {
				
				this.customerService.save(customer);
				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				res = this.createEditModelAndView(customer,
						errorMessage);

			}
		}
		return res;

	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(Customer customer) {
		ModelAndView res;

		res = createEditModelAndView(customer, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Customer customer,
			String message) {
		ModelAndView res;

		res = new ModelAndView("customer/registration");
		res.addObject("customer", customer);
		res.addObject("message", message);

		return res;
	}

}
