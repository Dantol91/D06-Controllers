
package controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.SettingsService;
import domain.Actor;
import domain.Administrator;
import domain.Settings;

@Controller
@RequestMapping("settings/administrator")
public class SettingsController extends AbstractController {

	// Services

	@Autowired
	private SettingsService	settingsService;
	@Autowired
	private ActorService	actorService;


	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit() {
		final Settings settings = this.settingsService.findSettings();
		Assert.notNull(settings);
		final ModelAndView res = this.createEditModelAndView(settings);
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final Settings settings, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(settings);
		else
			try {
				this.settingsService.save(settings);
				res = new ModelAndView("redirect:/");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(settings, "cannot.commit.error");
			}
		return res;
	}

	// Other methods

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "addSpamWord")
	private ModelAndView addSpamWord(final Settings settings, final BindingResult binding) {
		ModelAndView res = null;
		try {
			final String spamWord = "";
			if (settings.getSpamWords() == null)
				settings.setSpamWords(new ArrayList<String>());
			settings.getSpamWords().add(spamWord);
			res = this.createEditModelAndView(settings);
		} catch (final Throwable t) {
			res = this.createEditModelAndView(settings, "cannot.commit.error");
		}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "removeSpamWord")
	private ModelAndView removeSpamWord(final Settings settings, final BindingResult binding) {
		ModelAndView res = null;
		try {
			settings.getSpamWords().remove(settings.getSpamWords().size() - 1);
			res = this.createEditModelAndView(settings);
		} catch (final Throwable t) {
			res = this.createEditModelAndView(settings, "cannot.commit.error");
		}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "addPositiveWord")
	private ModelAndView addPositiveWord(final Settings settings, final BindingResult binding) {
		ModelAndView res = null;
		try {
			final String positiveWord = "";
			if (settings.getPositiveWords() == null)
				settings.setPositiveWords(new ArrayList<String>());
			settings.getPositiveWords().add(positiveWord);
			res = this.createEditModelAndView(settings);
		} catch (final Throwable t) {
			res = this.createEditModelAndView(settings, "cannot.commit.error");
		}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "removePositiveWord")
	private ModelAndView removePositiveWord(final Settings settings, final BindingResult binding) {
		ModelAndView res = null;
		try {
			settings.getPositiveWords().remove(settings.getPositiveWords().size() - 1);
			res = this.createEditModelAndView(settings);
		} catch (final Throwable t) {
			res = this.createEditModelAndView(settings, "cannot.commit.error");
		}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "addNegativeWord")
	private ModelAndView addNegativeWord(final Settings settings, final BindingResult binding) {
		ModelAndView res = null;
		try {
			final String negativeWord = "";
			if (settings.getNegativeWords() == null)
				settings.setNegativeWords(new ArrayList<String>());
			settings.getNegativeWords().add(negativeWord);
			res = this.createEditModelAndView(settings);
		} catch (final Throwable t) {
			res = this.createEditModelAndView(settings, "cannot.commit.error");
		}
		return res;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "removeNegativeWord")
	private ModelAndView removeNegativeWord(final Settings settings, final BindingResult binding) {
		ModelAndView res = null;
		try {
			settings.getNegativeWords().remove(settings.getNegativeWords().size() - 1);
			res = this.createEditModelAndView(settings);
		} catch (final Throwable t) {
			res = this.createEditModelAndView(settings, "cannot.commit.error");
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final Settings settings) {
		return this.createEditModelAndView(settings, null);
	}

	private ModelAndView createEditModelAndView(final Settings settings, final String message) {
		final ModelAndView res = new ModelAndView("settings/edit");
		res.addObject("settings", settings);
		res.addObject("message", message);
		this.isPrincipalAuthorizedEdit(res);
		return res;
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		if (principal instanceof Administrator)
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
