
package controllers.handyWorker;

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
import services.EndorserRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;

@Controller
@RequestMapping("/endorserRecord/handyWorker")
public class EndorserRecordController extends AbstractController {

	// Services

	@Autowired
	EndorserRecordService	endorserRecordService;

	@Autowired
	CurriculumService		curriculumService;

	@Autowired
	ActorService			actorService;


	// List 
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<EndorserRecord> endorserRecords;
		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);
		endorserRecords = this.endorserRecordService.findAll(curriculum);

		result = new ModelAndView("endorserRecord/list");
		result.addObject("endorserRecords", endorserRecords);
		result.addObject("requestURI", "endorserRecord/handyWorker/list.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord endorserRecord;
		endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
		result = new ModelAndView("endorserRecord/display");
		result.addObject("endorserRecord", endorserRecord);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		EndorserRecord er;
		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);

		er = this.endorserRecordService.create();

		er.setCurriculum(curriculum);

		result = this.createEditModelAndView(er);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int endorserRecordId) {
		ModelAndView result;
		EndorserRecord er;

		er = this.endorserRecordService.findOne(endorserRecordId);
		Assert.notNull(er);
		result = this.createEditModelAndView(er);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(endorserRecord);
		else
			try {
				this.endorserRecordService.save(endorserRecord);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord, final String messageCode) {
		final ModelAndView result;

		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);

		result = new ModelAndView("endorserRecord/edit");
		result.addObject("endorserRecord", endorserRecord);
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EndorserRecord endorserRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(endorserRecord, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;
		try {
			this.endorserRecordService.delete(endorserRecord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(endorserRecord, "endorserRecord.commit.error");

		}
		return result;
	}

}
