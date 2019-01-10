
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

import security.LoginService;
import security.UserAccount;
import services.ActorService;
import services.FixupTaskService;
import services.HandyWorkerService;
import services.PhaseService;
import services.WorkPlanService;
import domain.FixupTask;
import domain.HandyWorker;
import domain.Phase;
import domain.WorkPlan;

@Controller
@RequestMapping("workplan/handyWorker")
public class WorkplanController extends AbstractController {

	//Services
	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	WorkPlanService		workplanService;

	@Autowired
	FixupTaskService	fixupTaskService;

	@Autowired
	PhaseService		phaseService;

	@Autowired
	ActorService		actorService;


	//create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		WorkPlan workplan;
		HandyWorker handyWorker;
		final UserAccount uA = LoginService.getPrincipal();
		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(uA);
		final Collection<Phase> phases = new ArrayList<Phase>();
		workplan = this.workplanService.create();
		workplan.setPhases(phases);
		workplan.setHandyWorker(handyWorker);
		res = this.createEditModelAndView(workplan);

		return res;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int workplanId) {
		ModelAndView res;
		WorkPlan workplan;

		workplan = this.workplanService.findOne(workplanId);
		Assert.notNull(workplan);
		res = this.createEditModelAndView(workplan);

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid WorkPlan workplan, final BindingResult binding) {
		ModelAndView result;
		FixupTask f;
		f = workplan.getFixupTask();
		//f.setWorkPlan(workplan);
		//this.fixupTaskService.save(f);
		if (binding.hasErrors())
			result = this.createEditModelAndView(workplan);
		else
			try {
				workplan = this.workplanService.save(workplan);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(workplan, "workplan.commit.error");
			}

		return result;
	}
	//	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	//	public ModelAndView save(@Valid final WorkPlan workplan, final BindingResult binding) {
	//		ModelAndView res;
	//
	//		if (binding.hasErrors())
	//			res = this.createEditModelAndView(workplan);
	//		else
	//			try {
	//				this.handyWorkerService.save(workplan);
	//				res = new ModelAndView("redirect:list.do");
	//			} catch (final Throwable oops) {
	//				res = this.createEditModelAndView(workplan, "workplan.commit.error");
	//
	//			}
	//		return res;
	//	}
	//display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int workplanId) {
		final ModelAndView result;
		WorkPlan workplan;

		workplan = this.workplanService.findOne(workplanId);
		result = new ModelAndView("workplan/display");
		result.addObject("workplan", workplan);

		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<WorkPlan> workplans = new ArrayList<>();

		final UserAccount uA = LoginService.getPrincipal();
		final HandyWorker handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(uA);
		res = new ModelAndView("workplan/list");
		workplans = this.workplanService.findWorkPlanByHandyWorker(handyWorker);

		res.addObject("workplans", workplans);
		res.addObject("requestURI", "workplan/handyWorker/list.do");

		return res;
	}

	//delete

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final WorkPlan workplan, final BindingResult binding) {
		ModelAndView result;
		try {
			this.workplanService.delete(workplan);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(workplan, "workplan.commit.error");
		}
		return result;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final WorkPlan workplan) {
		final ModelAndView res;

		res = this.createEditModelAndView(workplan, null);

		return res;

	}

	protected ModelAndView createEditModelAndView(final WorkPlan workplan, final String message) {
		final ModelAndView result;
		Collection<FixupTask> fixUp;
		fixUp = this.fixupTaskService.findAcceptedFixupTasks();
		result = new ModelAndView("workplan/edit");
		result.addObject("workplan", workplan);
		result.addObject("fixUp", fixUp);
		result.addObject("message", message);

		return result;
	}

	//	private ModelAndView createEditModelAndView(final WorkPlan workplan, final String string) {
	//		final Date date = new SimpleDateFormat("MM/dd/yyyy").getCalendar().getTime();
	//		ModelAndView res;
	//		final WorkPlan workplan;
	//		final Trip trip;
	//		Date momentOfCreate = date;
	//		String title;
	//		String description;
	//		Collection<URL> attachments;
	//
	//		if (workplan.getWorkPlan() == null) {
	//
	//			momentOfCreate = null;
	//			title = null;
	//			description = null;
	//			attachments = null;
	//
	//			res = new ModelAndView("workplan/edit");
	//
	//		} else {
	//			momentOfCreate = workplan.getMomentOfCreate();
	//			title = workplan.getTitle();
	//			description = workplan.getDescription();
	//			attachments = workplan.getAttachments();
	//
	//			res = new ModelAndView("workplan/edit");
	//		}
	//		res.addObject("workplan", workplan);

	//		res.addObject("momentOfCreate", momentOfCreate);
	//		res.addObject("title", title);
	//		res.addObject("description", description);
	//		res.addObject("attachments", attachments);
	//
	//		res.addObject("message", string);

	//		return res;
	//	}

}
