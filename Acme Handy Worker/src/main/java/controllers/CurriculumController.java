package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.CurriculumService;
import services.HandyWorkerService;

import domain.Curriculum;
import domain.HandyWorker;

@Controller
@RequestMapping("/curriculum")
public class CurriculumController extends AbstractController{
	
	// Services 
	@Autowired
	private CurriculumService curriculumService;
	@Autowired
	private ActorService actorService;
	@Autowired
	private HandyWorkerService handyWorkerService;
	
	// Display 
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int curriculumId) {
		ModelAndView result;
		Curriculum curriculum;
		
		curriculum = curriculumService.findOne(curriculumId);
		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);				
		
		return result;
	}
	
	// Display
	
	@RequestMapping(value = "/handyWorker/displayMyCurriculum", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		HandyWorker handyWorker = handyWorkerService.findByPrincipal();
		Curriculum handyWorkerCurriculum;
		Curriculum curriculum;
		
		handyWorkerCurriculum = curriculumService.getCurriculumByHandyWorkerId(handyWorker.getId());
		curriculum = handyWorker.getCurriculum();
		result = new ModelAndView("curriculum/display");
		result.addObject("curriculum", curriculum);				
		result.addObject("handyWorkerCurriculum", handyWorkerCurriculum);				
		
		return result;
	}
	
	// Creation

			@RequestMapping(value = "/handyWorker/create", method = RequestMethod.GET)
			public ModelAndView create() {
				ModelAndView result;
				Curriculum curriculum;

				curriculum = curriculumService.create();
				result = createEditModelAndView(curriculum);
				
				return result;
			}
	
	// Edition 

		@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.GET)
		public ModelAndView edit(@RequestParam final int curriculumId) {
			ModelAndView result;
			Curriculum curriculum;

			curriculum = curriculumService.findOneToEdit(curriculumId);

			result = this.createEditModelAndView(curriculum);
			result.addObject("curriculum", curriculum);

			return result;
		}

		@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "save")
		public ModelAndView save(@Valid final Curriculum curriculum,
				final BindingResult binding) {
			ModelAndView result;

			if (binding.hasErrors())
				result = this.createEditModelAndView(curriculum);
			else
				try {
					this.curriculumService.save(curriculum);
					result = new ModelAndView("redirect:displayMyCurriculum.do");
				} catch (final Throwable oops) {
					String errorMessage = "curriculum.commit.error";
					
					if(oops.getMessage().contains("message.error")){
						errorMessage = oops.getMessage();
					}
					result = this.createEditModelAndView(curriculum, errorMessage);
				}

			return result;
		}
	
	// Delete
	
	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Curriculum curriculum, BindingResult bindingResult){
		ModelAndView result;
		
		try {
			curriculumService.delete(curriculum);
			result = new ModelAndView("redirect:/curriculum/handyWorker/displayMyCurriculum.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(
					curriculum, "curriculum.commit.error");
		}
		
		return result;
	}
	
	
	// Ancillary Methods
	
	protected ModelAndView createEditModelAndView(Curriculum curriculum) {
		ModelAndView result;

		result = createEditModelAndView(curriculum, null);
		
		return result;
	}
	
	protected ModelAndView createEditModelAndView(Curriculum curriculum, String message) {
		ModelAndView result;
		
		result = new ModelAndView("curriculum/edit");
		
		result.addObject("curriculum", curriculum);
		result.addObject("message", message);
		
		return result;
	}

}
