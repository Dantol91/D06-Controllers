package controllers.handyWorker;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import domain.HandyWorker;

@Controller
@RequestMapping("/handyWorker/registration")
public class HandyWorkerRegistrationController {

	// Services

	@Autowired
	private HandyWorkerService handyWorkerService;

	// Constructors


	public HandyWorkerRegistrationController() {
		super();
	}

	// Creation

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		HandyWorker handyWorker;

		handyWorker = handyWorkerService.create();
		res = createEditModelAndView(handyWorker);

		return res;
	}

	// Edit
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid HandyWorker handyWorker, BindingResult binding) {

		ModelAndView res;

		if (binding.hasErrors()) {

			res = createEditModelAndView(handyWorker);

		} else {
			try {
				handyWorkerService.save(handyWorker);

				res = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				String errorMessage = "application.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				res = createEditModelAndView(handyWorker, errorMessage);

			}

		}
		return res;

	}

	// Ancillary methods

	protected ModelAndView createEditModelAndView(HandyWorker handyWorker) {
		ModelAndView res;

		res = createEditModelAndView(handyWorker, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(HandyWorker handyWorker,
			String message) {
		ModelAndView res;

		res = new ModelAndView("handyWorker/registration");
		res.addObject("handyWorker", handyWorker);
		res.addObject("message", message);

		return res;
	}

}
