
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ActorService;
import services.ServiceActorDependantI;
import domain.Actor;
import domain.DomainEntity;

public class GenericControllerActorDependant<R extends DomainEntity, S extends ServiceActorDependantI<R>, T extends ControllerToView<R>> {

	// Service

	@Autowired
	private S				service;
	@Autowired
	private ActorService	actorService;
	@Autowired
	private T				controller;


	// List

	public ModelAndView list(final String viewName, final String listName) {
		final ModelAndView res = new ModelAndView(viewName);
		final Actor actor = this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		Assert.notNull(actor);
		res.addObject(listName, this.service.findAllByActor(actor));
		this.controller.addVariablesListModelAndView(res);
		return res;
	}

	public ModelAndView create() {
		ModelAndView res = null;
		final Actor actor = this.actorService.findOneByUserAccount(LoginService.getPrincipal());
		Assert.notNull(actor);
		final R object = this.service.create(actor);
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
