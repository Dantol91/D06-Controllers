
package controllers;

import org.springframework.web.servlet.ModelAndView;

import domain.DomainEntity;

public interface ControllerToView<R extends DomainEntity> {

	void addVariablesListModelAndView(ModelAndView mav);
	ModelAndView createEditModelAndView(R object);
	ModelAndView createEditModelAndView(R object, String msg);

}
