
package controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;


import repositories.GenericRepository;
import services.GenericService;
import services.ServiceObjectDependantI;

import domain.DomainEntity;

public class GenericControllerObjectDependant<R extends DomainEntity, S extends ServiceObjectDependantI<R, U>, T extends ControllerToView<R>, U extends DomainEntity, V extends GenericService<U, W>, W extends GenericRepository<U>> {

	// Service

	@Autowired
	private S	service;
	@Autowired
	private V	dependenceService;
	@Autowired
	private T	controller;


	// List

	public ModelAndView list(final String viewName, final String listName, final Integer dependenceId) {
		final ModelAndView res = new ModelAndView(viewName);
		final U dependence = this.dependenceService.findOne(dependenceId);
		Assert.notNull(dependence);
		res.addObject(listName, this.service.findAll(dependence));
		this.controller.addVariablesListModelAndView(res);
		return res;
	}

	public ModelAndView create(final Integer dependenceId) {
		ModelAndView res = null;
		final U dependence = this.dependenceService.findOne(dependenceId);
		Assert.notNull(dependence);
		final R object = this.service.create(dependence);
		res = this.createEditModelAndView(object);
		return res;
	}

	public ModelAndView edit(final Integer objectId) {
		ModelAndView res = null;
		final R object = this.service.findOne(objectId);
		Assert.notNull(object);
		res = this.createEditModelAndView(object);
		return res;
	}

	public ModelAndView save(final R object, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(object);
		else
			try {
				this.service.save(object);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(object, "commit.error");
			}
		return res;
	}

	public ModelAndView delete(final R object, final BindingResult binding) {
		ModelAndView res = null;
		try {
			this.service.delete(object);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable t) {
			res = this.createEditModelAndView(object, "commit.error");
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final R object) {
		return this.controller.createEditModelAndView(object);
	}

	private ModelAndView createEditModelAndView(final R object, final String msg) {
		return this.controller.createEditModelAndView(object, msg);
	}
}
