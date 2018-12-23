package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CreditCardService;
import domain.Application;
import domain.CreditCard;

@Controller
@RequestMapping("/creditCard")
public class CreditCardController extends AbstractController{
	
	// Services 

	@Autowired
	private CreditCardService creditCardService;
	@Autowired
	private ApplicationService applicationService;
	
	// Constructors 

	public CreditCardController() {
		super();
	}
	
	// Creation 

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam (required = false) final Integer applicationId) {
		ModelAndView result;
		CreditCard creditCard;
		
		creditCard = creditCardService.create();
		result = this.createEditModelAndView(creditCard);
		
		if(applicationId != null){
		Application application;
			
		}
		
		return result;
	}
	
	// Ancillary methods 

	protected ModelAndView createEditModelAndView(final CreditCard creditCard) {
		ModelAndView result;

		result = this.createEditModelAndView(creditCard, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CreditCard creditCard, final String message) {
		ModelAndView result;


		result = new ModelAndView("creditCard/edit");
		result.addObject("creditCard", creditCard);
		result.addObject("message", message);

		return result;
	}

}
