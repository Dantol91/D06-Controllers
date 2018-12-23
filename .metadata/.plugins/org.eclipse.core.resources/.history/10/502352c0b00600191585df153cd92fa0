
package controllers.administrator;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.FixUpTaskService;
import controllers.AbstractController;
import domain.FixUpTask;

@Controller
@RequestMapping("/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Service 

	@Autowired
	private FixUpTaskService	fixUpTaskService;
	
	@Autowired
	private ApplicationService	applicationService;

	@Autowired
	private ComplaintService	complaintService;
	
	@Autowired
	private CustomerService		CustomerService;
	
	
	// Constructor 

	public DashboardAdministratorController() {
		super();
	}

	// Dashboard 

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {
		ModelAndView result;
		result = new ModelAndView("administrator/dashboard");

		final Double ratioApplicationsPending = this.applicationService.getPendingRatio();
		final Double ratioApplicationsAccepted = this.applicationService.getAcceptedRatio();
		final Double ratioApplicationsRejected = this.applicationService.getRejectedRatio();

		result.addObject("ratioApplicationsPending", ratioApplicationsPending);
		result.addObject("ratioApplicationsAccepted", ratioApplicationsAccepted);
		result.addObject("ratioApplicationsCancelled", ratioApplicationsRejected);

		result.addObject("requestURI", "administrator/dashboard.do");

		return result;

	}
}
