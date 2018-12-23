package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.HandyWorkerService;
import services.CustomerService;
import services.MessageService;
import services.FixUpTaskService;
import domain.Application;
import domain.HandyWorker;
import domain.Customer;
import domain.FixUpTask;

@Controller
@RequestMapping("/application")
public class ApplicationController extends AbstractController{
	
	// Services 
	
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private FixUpTaskService fixUpTaskService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private CustomerService customerService;
	
	// Constructors 

	public ApplicationController() {
		super();
	}

	// Listing 

	@RequestMapping(value = "/handyWorker/list", method = RequestMethod.GET)
	public ModelAndView listHandyWorker(@RequestParam (required=false)String statusSelection) {
		ModelAndView result;
		Collection<Application> applications;
		HandyWorker h;
		Collection<String> statusSet;
		
		h = (HandyWorker) actorService.findByPrincipal();
		applications = applicationService.getHandyWorkerApplications(e.getId());
		statusSet = applicationService.getSetOfStatus(h.getId());
		
		if(statusSelection!=null){
			applications = applicationService.getApplicationsByStatusAndHandyWorkerId(e.getId(), statusSelection);
		}

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/handyWorker/list.do");
		result.addObject("applications", applications);
		result.addObject("statusSet", statusSet);

		return result;
	}
	
	@RequestMapping(value = "/customer/list", method = RequestMethod.GET)
	public ModelAndView listCustomer() {
		ModelAndView result;
		Collection<Application> applications;
		Customer c;
		
		c = (customer) actorService.findByPrincipal();
		applications = applicationService.getCustomerFixUPTasksApplications(c.getId());

		result = new ModelAndView("application/list");
		result.addObject("requestURI", "application/customer/list.do");
		result.addObject("applications", applications);

		return result;
	}
	

	// Creation 

	@RequestMapping(value = "/handyWorker/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam (required = false) final Integer fixUpTaskId) {
		ModelAndView result;
		HandyWorker h;
		Collection<FixUpTask> fixUpTasks;
		
		h = (HandyWorker) actorService.findByPrincipal();
		fixUpTasks = fixUpTaskService.getWithoutApplicationFixUpTasks(h.getId());
		application = this.applicationService.create();
		result = this.createEditModelAndView(application);
		result.addObject("fixUpTasks",fixUpTasks);
		

		if(fixUpTaskId!=null){
			result.addObject("fixUpTaskId",fixUpTaskId);
		}

		return result;
	}
	
	// Edition 

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int applicationId) {
		ModelAndView result;
		Application application;

		application = applicationService.findOne(applicationId);
		
		result = this.createEditModelAndView(application);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				Application saved = this.applicationService.save(application);
				HandyWorker handyWorker = handyWorkerService.findByApplicationId(saved.getId());
				Customer customer = customerService.getCustomerFromApplicationId(saved.getId());
				messageService.sendApplicationNotification(handyWorker, customer);
				result = new ModelAndView("redirect:handyWorker/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "application.commit.error";
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(application, errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Application application, final BindingResult binding) {
		ModelAndView result;

		try {
			this.applicationService.delete(application);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(application, "application.commit.error");
		}

		return result;
	}

	
	// Change Status 
	
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "changeStatus")
	public ModelAndView changeStatus(@Valid final Application application, final BindingResult binding) {
		ModelAndView result;
		

		if (binding.hasErrors())
			result = this.createEditModelAndView(application);
		else
			try {
				applicationService.changeStatus(application);
				HandyWorker handyWorker = handyWorkerService.findByApplicationId(application.getId());
				Customer customer = customerService.getCustomerFromApplicationId(application.getId());
				messageService.sendApplicationNotification(handyWorker, customer);
				
				result = new ModelAndView("redirect:customer/list.do");
			} catch (final Throwable oops) {
				String errorMessage = "application.commit.error";
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(application, errorMessage);
			}

		return result;
	}
	
	
	// Ancillary methods 

	protected ModelAndView createEditModelAndView(final Application application) {
		ModelAndView result;

		result = this.createEditModelAndView(application, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Application application, final String message) {
		ModelAndView result;


		result = new ModelAndView("application/edit");
		result.addObject("application", application);
		result.addObject("message", message);

		return result;
	}

}
