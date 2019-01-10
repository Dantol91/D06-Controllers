
package controllers.handyWorker;

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
import services.ActorService;
import services.CurriculumService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/curriculum/handyWorker")
public class CurriculumController extends AbstractController {

	//Services

	@Autowired
	CurriculumService	curriculumService;

	@Autowired
	ActorService		actorService;


	//Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Curriculum curriculum;
		HandyWorker handyWorker;
		final Collection<EducationRecord> educationRecords = new ArrayList<>();
		final Collection<ProfessionalRecord> professionalRecords = new ArrayList<>();
		final Collection<EndorserRecord> endorserRecords = new ArrayList<>();
		final Collection<MiscellaneousRecord> miscellaneousRecords = new ArrayList<>();

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);
		educationRecords.addAll(curriculum.getEducationRecords());
		professionalRecords.addAll(curriculum.getProfessionalRecords());
		endorserRecords.addAll(curriculum.getEndorserRecords());
		miscellaneousRecords.addAll(curriculum.getMiscellaneousRecords());

		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);
		result.addObject("handyWorker", handyWorker);
		result.addObject("educationRecords", educationRecords);
		result.addObject("professionalRecords", professionalRecords);
		result.addObject("endorserRecords", endorserRecords);
		result.addObject("miscellaneousRecords", miscellaneousRecords);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Curriculum c;

		c = this.curriculumService.create();

		result = this.createEditModelAndView(c);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int curriculumId) {
		ModelAndView result;
		Curriculum c;

		c = this.curriculumService.findOne(curriculumId);
		Assert.notNull(c);
		result = this.createEditModelAndView(c);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(curriculum);
		else
			try {
				this.curriculumService.save(curriculum);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(curriculum, "curriculum.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("curriculum/edit");
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Curriculum curriculum) {
		ModelAndView result;

		result = this.createEditModelAndView(curriculum, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Curriculum curriculum, final BindingResult binding) {
		ModelAndView result;
		try {
			this.curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(curriculum, "curriculum.commit.error");

		}
		return result;
	}

}
