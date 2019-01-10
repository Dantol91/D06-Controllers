
package controllers;

import java.util.ArrayList;
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
import services.ApplicationService;
import services.FixupTaskService;
import domain.Actor;
import domain.Application;
import domain.Customer;
import domain.FixupTask;
import domain.HandyWorker;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController {

	// Services
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private FixupTaskService	fixupTaskService;

	@Autowired
	private ActorService		actorService;


	// List

	@RequestMapping(value = "handyworker/list", method = RequestMethod.GET)
	public ModelAndView listHandyWorker() {
		ModelAndView result;
		Collection<Application> applications;

		Collection<Application> allApplications;

		final HandyWorker h = (HandyWorker) this.actorService.findPrincipal();
		applications = this.applicationService.findApplicationsByHandyWorker(h);
		allApplications = this.applicationService.findAll();

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/handyworker/list.do");
		result.addObject("applications", applications);
		result.addObject("allApplications", allApplications);

		return result;
	}

	@RequestMapping(value = "customer/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = true) final Integer fixupTaskId) {
		ModelAndView result;
		Collection<Application> applications;

		final FixupTask f = this.fixupTaskService.findOne(fixupTaskId);
		applications = f.getApplications();

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/customer/list.do?fixupTaskId=" + fixupTaskId.toString());
		result.addObject("applications", applications);

		return result;
	}

	// Display

	@RequestMapping(value = "endorsable/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int applicationId) {
		ModelAndView result;
		Application c;
		c = this.applicationService.findOne(applicationId);
		result = new ModelAndView("application/display");
		result.addObject("application", c);

		return result;
	}

	@RequestMapping(value = "handyworker/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Application c;
		final Boolean fromFixup = false;

		Collection<FixupTask> allFixupTasks = new ArrayList<>();
		allFixupTasks = this.fixupTaskService.findAll();
		c = this.applicationService.create();

		result = this.createEditModelAndView(c);
		result.addObject("allFixupTasks", allFixupTasks);
		result.addObject("fromFixup", fromFixup);

		return result;

	}

	@RequestMapping(value = "handyworker/create2", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int fixupTaskId) {
		ModelAndView result;
		Application c;
		Collection<FixupTask> allFixupTasks = new ArrayList<>();
		allFixupTasks = this.fixupTaskService.findAll();
		final Boolean fromFixup = true;
		FixupTask fixupTask = new FixupTask();
		fixupTask = this.fixupTaskService.findOne(fixupTaskId);
		c = this.applicationService.create();

		result = this.createEditModelAndView(c);
		result.addObject("fromFixup", fromFixup);
		result.addObject("fixupTask", fixupTask);
		result.addObject("allFixupTasks", allFixupTasks);

		return result;

	}

	@RequestMapping(value = "endorsable/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application c;
		Collection<FixupTask> allFixupTasks = new ArrayList<>();
		allFixupTasks = this.fixupTaskService.findAll();
		c = this.applicationService.findOne(applicationId);
		Assert.notNull(c);
		result = this.createEditModelAndView(c);
		result.addObject("allFixupTasks", allFixupTasks);

		return result;

	}

	protected ModelAndView createEditModelAndView(final Application c) {
		ModelAndView result;

		result = this.createEditModelAndView(c, null);

		return result;
	}
	//
	protected ModelAndView createEditModelAndView(final Application app, final String messageCode) {
		final ModelAndView result;
		result = new ModelAndView("application/edit");
		final Actor principal = this.actorService.findPrincipal();

		if (principal instanceof HandyWorker) {
			final HandyWorker hw = (HandyWorker) principal;
			final Collection<FixupTask> fixs = this.fixupTaskService.findFixupTasksNotAppliedByHandyWorker(hw);
			result.addObject("fixupTasks", fixs);
		} else if (principal instanceof Customer) {
			final Customer c = (Customer) principal;
			final Collection<FixupTask> fixs = this.fixupTaskService.findByCustomer(c);
			result.addObject("fixupTasks", fixs);
		}
		if (app.getCreditCard() == null)
			result.addObject("useCreditCard", false);
		else
			result.addObject("useCreditCard", true);
		result.addObject("application", app);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "endorsable/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application a, final BindingResult binding) {
		ModelAndView result;
		Collection<FixupTask> allFixupTasks = new ArrayList<>();
		allFixupTasks = this.fixupTaskService.findAll();

		if (binding.hasErrors()) {

			result = this.createEditModelAndView(a);
			result.addObject("allFixupTasks", allFixupTasks);
		} else
			try {
				this.applicationService.save(a);
				final Actor principal = this.actorService.findPrincipal();
				if (principal instanceof HandyWorker)
					result = new ModelAndView("redirect:/application/handyworker/list.do");
				else if (principal instanceof Customer)
					result = new ModelAndView("redirect:/application/customer/list.do");
				else
					result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(a, "cannot.commit.error");
				result.addObject("allFixupTasks", allFixupTasks);

			}
		return result;
	}
	@RequestMapping(value = "endorsable/edit", method = RequestMethod.POST, params = "addCreditCard")
	public ModelAndView addCreditCard(final Application a, final BindingResult binding) {
		ModelAndView result;
		result = this.createEditModelAndView(a);
		result.addObject("useCreditCard", true);
		return result;
	}

	@RequestMapping(value = "endorsable/edit", method = RequestMethod.POST, params = "removeCreditCard")
	public ModelAndView removeCreditCard(final Application a, final BindingResult binding) {
		ModelAndView result;
		result = this.createEditModelAndView(a);
		result.addObject("useCreditCard", false);
		return result;
	}

}
