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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {
	
	// Services 

	@Autowired
	private ConfigurationService configurationService;

	// Constructors 

	public WelcomeController() {
		super();
	}

	// Index 

	@RequestMapping(value = "/index")
	public ModelAndView index(Locale locale) {
		ModelAndView result;
		String welcomeMessage = "";
		
		if("es".equals(locale.getLanguage())){
			welcomeMessage = configurationService.getConfiguration().getWelcomeMessageES();
		}else if("en".equals(locale.getLanguage())){
			welcomeMessage = configurationService.getConfiguration().getWelcomeMessageEN();
		}
		

		result = new ModelAndView("welcome/index");
		result.addObject("welcomeMessage", welcomeMessage);

		return result;
}
