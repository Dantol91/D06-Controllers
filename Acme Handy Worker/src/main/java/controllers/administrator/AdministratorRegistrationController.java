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
@RequestMapping("/admin/registration")
public class AdministratorRegistrationController {

	// Services
	@Autowired
	private AdministratorService administratorService;


	// Constructors

	public AdministratorRegistrationController() {
		super();
	}

	// Creation

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Admin admin;

		admin = this.administratorService.create();
		res = this.createEditModelAndView(admin);

		return res;
	}

	// Edit
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Admin admin, BindingResult binding) {
		ModelAndView res = null;

		if (binding.hasErrors()) {
			res = this.createEditModelAndView(admin);
		} else {
			try {

				this.administratorService.save(admin);
				res = new ModelAndView("redirect:/");

			}

			catch (Throwable oops) {

				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}

				res = this.createEditModelAndView(admin, errorMessage);
			}

		}
		return res;
	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(Admin admin) {
		ModelAndView res;

		res = createEditModelAndView(admin, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(Admin admin,
			String message) {
		ModelAndView res;

		res = new ModelAndView("admin/registration");
		res.addObject("admin", admin);
		res.addObject("message", message);

		return res;
	}

}
