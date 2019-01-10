
package controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.ActorService;
import services.ApplicationService;
import services.CategoryService;
import services.ComplaintService;
import services.CustomerService;
import services.FixupTaskService;
import services.WarrantyService;
import services.WorkPlanService;
import domain.Actor;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.Customer;
import domain.FixupTask;
import domain.Warranty;
import domain.WorkPlan;

@Controller
@RequestMapping("fixupTask")
public class FixupTaskController extends AbstractController {

	@Autowired
	FixupTaskService	fixupTaskService;

	@Autowired
	CustomerService		customerService;

	@Autowired
	WarrantyService		warrantyService;

	@Autowired
	CategoryService		categoryService;

	@Autowired
	WorkPlanService		workPlanService;

	@Autowired
	ComplaintService	complaintService;

	@Autowired
	ApplicationService	applicationService;

	@Autowired
	ActorService		actorService;


	@RequestMapping(value = "handyworker/search", method = RequestMethod.GET)
	public ModelAndView search(@RequestParam(required = false) final String keyword, @RequestParam(required = false) final Integer categoryId, @RequestParam(required = false) final Integer warrantyId, @RequestParam(required = false) final Double minPrice,
		@RequestParam(required = false) final Double maxPrice, @RequestParam(required = false) final String minDate, @RequestParam(required = false) final String maxDate) {
		final ModelAndView res = new ModelAndView("fixupTask/list");
		Date minimumDate = null;
		Date maximumDate = null;
		Category category = null;
		Warranty warranty = null;
		try {
			if (categoryId != null && categoryId != 0) {
				category = this.categoryService.findOne(categoryId);
				System.out.println(categoryId);
				Assert.notNull(category);
			}
			if (warrantyId != null && warrantyId != 0) {
				warranty = this.warrantyService.findOne(warrantyId);
				System.out.println(warrantyId);
				Assert.notNull(warranty);
			}
			if (!StringUtils.isEmpty(minDate)) {
				final String[] splitMinDate = minDate.split("-");
				final Calendar minCalendar = Calendar.getInstance();
				minCalendar.set(new Integer(splitMinDate[0]), new Integer(splitMinDate[1]), new Integer(splitMinDate[2]));
				minimumDate = minCalendar.getTime();
			}
			if (!StringUtils.isEmpty(maxDate)) {
				final String[] splitMaxDate = maxDate.split("-");
				final Calendar maxCalendar = Calendar.getInstance();
				maxCalendar.set(new Integer(splitMaxDate[0]), new Integer(splitMaxDate[1]), new Integer(splitMaxDate[2]));
				maximumDate = maxCalendar.getTime();
			}
			res.addObject("fixupTasks", this.fixupTaskService.search(keyword, category, warranty, minPrice, maxPrice, minimumDate, maximumDate));
		} catch (final Throwable oops) {
			res.addObject("message", "cannot.commit.error");
			res.addObject("fixupTasks", this.fixupTaskService.findAll());
		}

		String requestURI = "fixupTask/handyworker/search.do?";
		if (!StringUtils.isEmpty(keyword))
			requestURI += "keyword=" + keyword + "&";
		if (categoryId != null)
			requestURI += "categoryId=" + categoryId.toString() + "&";
		if (warrantyId != null)
			requestURI += "warrantyId=" + warrantyId.toString() + "&";
		if (minPrice != null)
			requestURI += "minPrice=" + minPrice.toString() + "&";
		if (maxPrice != null)
			requestURI += "maxPrice=" + maxPrice.toString() + "&";
		if (minDate != null)
			requestURI += "minDate" + minDate.toString() + "&";
		if (maxDate != null)
			requestURI += "minDate" + minDate.toString() + "&";
		requestURI = requestURI.substring(0, requestURI.length() - 2);
		res.addObject("categories", this.categoryService.findAll());
		res.addObject("warranties", this.warrantyService.findWarrantyNotDraft());
		res.addObject("requestURI", requestURI);
		return res;
	}
	@RequestMapping(value = "endorsable/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<FixupTask> fixupTasks = new ArrayList<>();
		Actor actor;
		actor = this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		final Authority hw = new Authority();
		hw.setAuthority("HANDYWORKER");
		final Authority c = new Authority();
		c.setAuthority("CUSTOMER");

		if (actor.getUserAccount().getAuthorities().contains(hw))
			fixupTasks = this.fixupTaskService.findAll();
		else if (actor.getUserAccount().getAuthorities().contains(c))
			fixupTasks = this.fixupTaskService.findByCustomer((Customer) actor);

		result = new ModelAndView("fixupTask/list");
		result.addObject("fixupTasks", fixupTasks);
		result.addObject("categories", this.categoryService.findAll());
		result.addObject("warranties", this.warrantyService.findWarrantyNotDraft());
		result.addObject("requestURI", "fixupTask/endorsable/list.do");

		return result;
	}
	@RequestMapping(value = "customer/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		FixupTask fixupTask;
		Collection<Category> categories = new ArrayList<>();
		Collection<Warranty> warranties = new ArrayList<>();

		warranties = this.warrantyService.findAll();
		categories = this.categoryService.findAll();

		fixupTask = this.fixupTaskService.create();
		result = this.createEditModelAndView(fixupTask);
		result.addObject("categories", categories);
		result.addObject("warranties", warranties);

		return result;
	}

	@RequestMapping(value = "endorsable/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int fixupTaskId) {
		ModelAndView result;
		FixupTask fixupTask;
		Collection<Application> apps = new ArrayList<>();
		Collection<Complaint> compls = new ArrayList<>();

		fixupTask = this.fixupTaskService.findOne(fixupTaskId);
		apps = fixupTask.getApplications();
		compls = fixupTask.getComplaints();

		result = new ModelAndView("fixupTask/display");
		result.addObject("fixupTask", fixupTask);
		result.addObject("apps", apps);
		result.addObject("compls", compls);

		return result;
	}

	//Edit

	@RequestMapping(value = "customer/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int fixupTaskId) {
		ModelAndView result;
		FixupTask fixupTask;

		fixupTask = this.fixupTaskService.findOne(fixupTaskId);
		Assert.notNull(fixupTask);
		result = this.createEditModelAndView(fixupTask);

		return result;
	}

	//Save

	@RequestMapping(value = "customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixupTask fixupTask, final BindingResult binding) {
		ModelAndView result;
		final Exception dateErr = new Exception("fechas MAL");

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(fixupTask);
			System.out.println(binding.getAllErrors());
		} else
			try {
				if (!fixupTask.getStart().before(fixupTask.getEnd()))
					throw dateErr;
				this.fixupTaskService.save(fixupTask);
				result = new ModelAndView("redirect:/fixupTask/endorsable/list.do");
			} catch (final Throwable oops) {
				if (oops.equals(dateErr))
					result = this.createEditModelAndView(fixupTask, "date.Error");
				else
					result = this.createEditModelAndView(fixupTask, "fixupTask.commit.error");
			}
		return result;
	}

	//delete

	@RequestMapping(value = "customer/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final FixupTask fixupTask, final BindingResult binding) {
		ModelAndView result;
		try {
			this.fixupTaskService.delete(fixupTask);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(fixupTask, "fixupTask.commit.error");
		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final FixupTask fixupTask) {
		ModelAndView result;

		result = this.createEditModelAndView(fixupTask, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final FixupTask fixupTask, final String messageCode) {
		final ModelAndView result;
		Collection<Warranty> warranties;
		Collection<Category> categories;
		Collection<WorkPlan> workplans;
		Collection<Customer> customers;
		Collection<Complaint> complaints;
		Collection<Application> applications;

		warranties = this.warrantyService.findAll();
		categories = this.categoryService.findAll();
		workplans = this.workPlanService.findAll();
		customers = this.customerService.findAll();
		complaints = this.complaintService.findAll();
		applications = this.applicationService.findAll();

		result = new ModelAndView("fixupTask/edit");
		result.addObject("fixupTask", fixupTask);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		result.addObject("workplans", workplans);
		result.addObject("complaints", complaints);
		result.addObject("customers", customers);
		result.addObject("applications", applications);

		result.addObject("message", messageCode);

		return result;
	}

	//	<input type ="button"
	//		name="cancel"
	//		value="<spring:message code="fixupTask.edit.cancel"/>"
	//		onclick = "javascript:
	//				relativeRedir('fixupTask/customer/list.do');" />
}
