
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
import services.MiscellaneousRecordService;
import controllers.AbstractController;
import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Controller
@RequestMapping("/miscellaneousRecord/handyWorker")
public class MiscellaneousRecordController extends AbstractController {

	// Services
	@Autowired
	MiscellaneousRecordService	miscellaneousRecordService;

	@Autowired
	CurriculumService			curriculumService;

	@Autowired
	ActorService				actorService;


	// List 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);
		miscellaneousRecords = this.miscellaneousRecordService.findAll(curriculum);

		result = new ModelAndView("miscellaneousRecord/list");
		result.addObject("miscellaneousRecords", miscellaneousRecords);
		result.addObject("requestURI", "miscellaneousRecord/handyWorker/list.do");

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;
		miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		result = new ModelAndView("miscellaneousRecord/display");
		result.addObject("miscellaneousRecord", miscellaneousRecord);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;
		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);

		miscellaneousRecord = this.miscellaneousRecordService.create();

		miscellaneousRecord.setCurriculum(curriculum);

		result = this.createEditModelAndView(miscellaneousRecord);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int miscellaneousRecordId) {
		ModelAndView result;
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.findOne(miscellaneousRecordId);
		Assert.notNull(miscellaneousRecord);
		result = this.createEditModelAndView(miscellaneousRecord);

		return result;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(miscellaneousRecord);
		else
			try {
				this.miscellaneousRecordService.save(miscellaneousRecord);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");
			}
		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord, final String messageCode) {
		final ModelAndView result;

		Curriculum curriculum;
		HandyWorker handyWorker;

		handyWorker = (HandyWorker) this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		curriculum = this.curriculumService.findByHandyWorker(handyWorker);

		result = new ModelAndView("miscellaneousRecord/edit");
		result.addObject("miscellaneousRecord", miscellaneousRecord);
		result.addObject("curriculum", curriculum);
		result.addObject("message", messageCode);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MiscellaneousRecord miscellaneousRecord) {
		ModelAndView result;

		result = this.createEditModelAndView(miscellaneousRecord, null);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final MiscellaneousRecord miscellaneousRecord, final BindingResult binding) {
		ModelAndView result;
		try {
			this.miscellaneousRecordService.delete(miscellaneousRecord);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(miscellaneousRecord, "miscellaneousRecord.commit.error");

		}
		return result;
	}

}
