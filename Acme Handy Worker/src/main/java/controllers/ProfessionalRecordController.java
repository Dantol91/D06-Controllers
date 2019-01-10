
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

import security.LoginService;
import services.ActorService;
import services.CurriculumService;
import services.ProfessionalRecordService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/handyWorker")
public class ProfessionalRecordController extends AbstractController {

	@Autowired
	ProfessionalRecordService	professionalRecordService;

	@Autowired
	CurriculumService			curriculumService;

	@Autowired
	ActorService				actorService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<ProfessionalRecord> professionalRecords;
		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);
		professionalRecords = this.professionalRecordService.findAll(curriculum);

		result = new ModelAndView("professionalRecord/list");
		result.addObject("professionalRecords", professionalRecords);
		result.addObject("requestURI", "professionalRecord/handyWorker/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;
		professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		result = new ModelAndView("professionalRecord/display");
		result.addObject("professionalRecord", professionalRecord);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		ProfessionalRecord pr;
		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);

		pr = this.professionalRecordService.create(curriculum);
		result = this.createEditModelAndView(pr);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int professionalRecordId) {
		ModelAndView result;
		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
		Assert.notNull(professionalRecord);
		result = this.createEditModelAndView(professionalRecord);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(professionalRecord);
		else
			try {
				this.professionalRecordService.save(professionalRecord);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord, final String messageCode) {
		final ModelAndView result;

		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);

		result = new ModelAndView("professionalRecord/edit");
		result.addObject("professionalRecord", professionalRecord);
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ProfessionalRecord professionalRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(professionalRecord, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;
		try {
			this.professionalRecordService.delete(professionalRecord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(professionalRecord, "professionalRecord.commit.error");

		}
		return result;
	}
}
