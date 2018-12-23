
package controllers.administrator;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import domain.Administrator;

@Controller
@RequestMapping("/administrator/registration")
public class AdministratorRegistrationController {

	// Services
	@Autowired
	private AdministratorService	administratorService;


	// Constructors

	public AdministratorRegistrationController() {
		super();
	}

	// Creation

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Administrator administrator;

		administrator = this.administratorService.create();
		res = this.createEditModelAndView(administrator);

		return res;
	}

	// Edit
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Administrator administrator, final BindingResult binding) {
		ModelAndView res = null;

		if (binding.hasErrors())
			res = this.createEditModelAndView(administrator);
		else
			try {

				this.administratorService.save(administrator);
				res = new ModelAndView("redirect:/");

			}

			catch (final Throwable oops) {

				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();

				res = this.createEditModelAndView(administrator, errorMessage);
			}
		return res;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(final Administrator administrator) {
		ModelAndView res;

		res = this.createEditModelAndView(administrator, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final Administrator administrator, final String message) {
		ModelAndView res;

		res = new ModelAndView("administrator/registration");
		res.addObject("administrator", administrator);
		res.addObject("message", message);

		return res;
	}

}