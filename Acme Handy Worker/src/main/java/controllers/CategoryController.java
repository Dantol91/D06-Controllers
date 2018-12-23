package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import domain.Category;

@Controller
@RequestMapping("/category")
public class CategoryController extends AbstractController{
	
	// Services 

	@Autowired
	private CategoryService categoryService;
	
	// Constructors 

	public CategoryController() {
		super();
	}

	// Listing 

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required=false) final Integer parentCategoryId) {
		ModelAndView result;
		Collection<Category> categories;
		
		if(parentCategoryId==null){
			categories = categoryService.getParentCategories();
		}else{
			categories = categoryService.getChildCategories(parentCategoryId);
		}
		
		result = new ModelAndView("category/list");
		result.addObject("requestURI", "category/list.do");
		result.addObject("categories",categories);

		return result;
	}
	
	// Creation 
	
	@RequestMapping(value = "/administrator/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Category category;
		
		
		category = this.categoryService.create();
		result = this.createEditModelAndView(category);
		
		return result;
	}
	
	// Edition 
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int categoryId) {
		ModelAndView result;
		Category category;

		category = categoryService.findOne(categoryId);
		
		result = this.createEditModelAndView(category);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Category category, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(category);
		else
			try {
				this.categoryService.save(category);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				String errorMessage = "category.commit.error";
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(category, errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Category category, final BindingResult binding) {
		ModelAndView result;

		try {
			this.categoryService.delete(category);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(category, "category.commit.error");
		}

		return result;
	}
	
	// Ancillary methods 

	protected ModelAndView createEditModelAndView(final Category category) {
		ModelAndView result;

		result = this.createEditModelAndView(category, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Category category, final String message) {
		ModelAndView result;
		Collection<Category> categories;


		result = new ModelAndView("category/edit");
		categories = categoryService.findAll();
		categories.remove(category);
		categories.removeAll(category.getChildCategories());
		
		result.addObject("category", category);
		result.addObject("message", message);
		result.addObject("categories", categories);

		return result;
	}
}
