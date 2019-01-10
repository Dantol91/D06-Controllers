
package controllers;

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

import services.PhaseService;
import services.WorkPlanService;
import domain.Phase;
import domain.WorkPlan;

@Controller
@RequestMapping("phase/handyWorker")
public class PhaseController extends AbstractController {

	WorkPlan				w;

	// Services
	@Autowired
	private PhaseService	phaseService;
	@Autowired
	private WorkPlanService	workPlanService;


	// List-

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int workplanId) {
		ModelAndView result;
		Collection<Phase> phases;

		this.w = this.workPlanService.findOne(workplanId);
		phases = this.w.getPhases();

		result = new ModelAndView("phase/list");
		result.addObject("requestURI", "phase/handyWorker/list.do");
		result.addObject("phases", phases);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Phase pr;

		pr = this.phaseService.create2();
		pr.setWorkPlan(this.w);
		result = this.createEditModelAndView(pr);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int phase) {
		ModelAndView result;
		Phase phaseP;

		phaseP = this.phaseService.findOne(phase);
		Assert.notNull(phaseP);
		result = this.createEditModelAndView(phaseP);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Phase phase, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(phase);
		else
			try {
				this.phaseService.save(phase);
				result = new ModelAndView("redirect:list.do?workplanId=" + phase.getWorkPlan().getId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(phase, "phase.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Phase phase, final BindingResult binding) {
		ModelAndView result;
		final int aux = phase.getWorkPlan().getId();
		try {
			this.phaseService.delete(phase);
			result = new ModelAndView("redirect:list.do?workplanId=" + aux);
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(phase, "phase.commit.error");

		}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Phase phase) {
		ModelAndView result;

		result = this.createEditModelAndView(phase, null);

		return result;
	}
	protected ModelAndView createEditModelAndView(final Phase phase, final String messageCode) {
		final ModelAndView result;

		final Collection<WorkPlan> workPlans;

		result = new ModelAndView("phase/edit");
		result.addObject("phase", phase);
		result.addObject("message", messageCode);

		return result;
	}

}
