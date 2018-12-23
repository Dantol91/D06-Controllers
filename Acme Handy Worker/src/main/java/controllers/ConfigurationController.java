/*
 * WelcomeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;



import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import domain.Configuration;

import services.ConfigurationService;

@Controller
@RequestMapping("/configuration")
public class ConfigurationController extends AbstractController {
	
	// Services 

	@Autowired
	private ConfigurationService configurationService;

	// Constructors 

	public ConfigurationController() {
		super();
	}
	
	// Listing 

	@RequestMapping(value = "/admin/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Configuration configuration;
		
		configuration = (Configuration) configurationService.findAll().toArray()[0];
		
		result = new ModelAndView("configuration/display");
		result.addObject("configuration", configuration);

		return result;
	}

	// Configuration 

	@RequestMapping(value="/loadBanner", method=RequestMethod.GET)
	@ResponseBody
	public String banner() {
		String imgCode;
		String bannerURL;

		bannerURL = configurationService.getBannerURL();
		imgCode = "<img height='250px' width='50%' src='" + bannerURL + "' alt='Acme HandyWorker, Inc.' />";


		return bannerURL;
	}
	
	
	// Edition 

	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int configurationId) {
		ModelAndView result;
		Configuration configuration;

		configuration = configurationService.findOne(configurationId);
		
		result = this.createEditModelAndView(configuration);

		return result;
	}

	@RequestMapping(value = "/admin/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(configuration);
		else
			try {
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:display.do");
			} catch (final Throwable oops) {
				String errorMessage = "application.commit.error";
				
				if(oops.getMessage().contains("message.error")){
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(configuration, errorMessage);
			}

		return result;
	}

	@RequestMapping(value = "/admin/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final Configuration configuration, final BindingResult binding) {
		ModelAndView result;

		try {
			this.configurationService.delete(configuration);
			result = new ModelAndView("redirect:display.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(configuration, "application.commit.error");
		}

		return result;
	}
	
	// Ancillary methods 

	protected ModelAndView createEditModelAndView(final Configuration configuration) {
		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String message) {
		ModelAndView result;


		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("message", message);

		return result;
	}
}
