
package controllers.administrator;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.UserAccount;
import services.ActorService;
import controllers.AbstractController;
import domain.Actor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services 
	@Autowired
	private ActorService	actorService;


	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam final int actorId) {
		ModelAndView result;
		Assert.notNull(actorId);
		final Actor actor = this.actorService.findOne(actorId);
		final UserAccount ua = actor.getUserAccount();

		ua.setBanned(true);

		result = this.createEditModelAndView2(actor);

		return result;

	}

	@RequestMapping(value = "/unBan", method = RequestMethod.GET)
	public ModelAndView unBan(@RequestParam final int actorId) {
		ModelAndView result;
		Assert.notNull(actorId);
		final Actor actor = this.actorService.findOne(actorId);
		final UserAccount ua = actor.getUserAccount();

		ua.setBanned(false);

		result = this.createEditModelAndView2(actor);

		return result;

	}

	protected ModelAndView createEditModelAndView2(final Actor admin) {
		ModelAndView result;

		result = this.createEditModelAndView2(admin, null);

		return result;
	}

	protected ModelAndView createEditModelAndView2(final Actor admin, final String messageCode) {
		final ModelAndView result;

		final Collection<Actor> allExceptMe = this.actorService.findAllExceptMe(admin);
		result = new ModelAndView("actor/list");
		result.addObject("administrator", admin);
		result.addObject("actors", allExceptMe);
		result.addObject("message", messageCode);

		return result;
	}
}
