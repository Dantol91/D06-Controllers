
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.HandyWorkerService;
import services.WarrantyService;
import domain.Warranty;

@Controller
@RequestMapping("warranty/administrator")
public class WarrantyController extends AbstractController {

	Boolean				draft	= true;
	//Services
	@Autowired
	HandyWorkerService	handyWorkerService;

	@Autowired
	WarrantyService		warrantyService;


	//create

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		Warranty warranty;
		warranty = this.warrantyService.create();
		this.draft = true;
		res = this.createEditModelAndView(warranty);

		return res;
	}

	//Edit

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int warrantyId) {
		ModelAndView res;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		Assert.notNull(warranty);
		this.draft = warranty.getDraft();
		res = this.createEditModelAndView(warranty);

		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Warranty warranty, final BindingResult binding) {
		ModelAndView result;
		try {
			this.warrantyService.delete(warranty);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(warranty, "warranty.commit.error");
		}
		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int warrantyId) {
		final ModelAndView result;
		Warranty warranty;

		warranty = this.warrantyService.findOne(warrantyId);
		result = new ModelAndView("warranty/display");
		result.addObject("warranty", warranty);

		return result;
	}

	//List

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res;
		Collection<Warranty> warranties = new ArrayList<>();

		res = new ModelAndView("warranty/list");
		warranties = this.warrantyService.findAll();

		res.addObject("warranties", warranties);
		res.addObject("requestURI", "warranty/administrator/list.do");

		return res;
	}

	//Ancillary methods

	private ModelAndView createEditModelAndView(final Warranty warranty) {
		final ModelAndView res;

		res = this.createEditModelAndView(warranty, null);

		return res;

	}

	protected ModelAndView createEditModelAndView(final Warranty warranty, final String message) {
		ModelAndView result;

		result = new ModelAndView("warranty/edit");
		result.addObject("warranty", warranty);

		return result;
	}

}
