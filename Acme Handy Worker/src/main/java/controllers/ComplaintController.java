
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

import services.ComplaintService;
import services.FixupTaskService;
import domain.Complaint;
import domain.FixupTask;

@Controller
@RequestMapping("/complaint")
public class ComplaintController extends AbstractController {

	// Services
	@Autowired
	private ComplaintService	complaintService;

	@Autowired
	private FixupTaskService	fixupTaskService;


	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Complaint> res;

		res = this.complaintService.findAll();

		result = new ModelAndView("complaint/list");
		result.addObject("requestURI", "complaint/list.do");
		result.addObject("complaints", res);

		return result;
	}

	// Display

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int complaintId) {
		ModelAndView result;
		Complaint c;
		c = this.complaintService.findOne(complaintId);
		result = new ModelAndView("complaint/display");
		result.addObject("complaint", c);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Complaint c;
		c = this.complaintService.create();

		result = this.createEditModelAndView(c);

		return result;

	}
	protected ModelAndView createEditModelAndView(final Complaint c) {
		ModelAndView result;

		result = this.createEditModelAndView(c, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Complaint complaint, final String messageCode) {
		final ModelAndView result;
		Collection<FixupTask> fixs = new ArrayList<>();
		fixs = this.fixupTaskService.findAll();

		result = new ModelAndView("complaint/create");
		result.addObject("complaint", complaint);
		result.addObject("FIXUPTASKS", fixs);
		result.addObject("message", messageCode);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Complaint c, final BindingResult binding) {
		ModelAndView result;
		System.out.println("llega al save");

		if (binding.hasErrors()) {
			System.out.println("pasa por el if");

			result = this.createEditModelAndView(c);
		} else
			try {
				System.out.println("pasa por el try");

				this.complaintService.save(c);
				System.out.println("sale por el if");

				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("pasa por el catch");

				result = this.createEditModelAndView(c, "complaint.commit.error");
			}
		return result;
	}
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Complaint c;

		c = this.complaintService.findOne(categoryId);
		Assert.notNull(c);
		result = this.createEditModelAndView(c);

		return result;

	}

}
