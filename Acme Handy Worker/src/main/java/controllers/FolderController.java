
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.FolderService;
import domain.Actor;
import domain.Folder;

@Controller
@RequestMapping("folder/actor")
public class FolderController extends AbstractController {

	// Services

	@Autowired
	private FolderService	folderService;
	@Autowired
	private ActorService	actorService;


	// List

	@RequestMapping("list")
	public ModelAndView list(@RequestParam(required = false) final Integer parentId) {
		final ModelAndView res = new ModelAndView("folder/list");
		final Actor actor = this.actorService.findPrincipal();
		Collection<Folder> folders = null;
		if (parentId == null) {
			folders = this.folderService.findByActorWithoutParent(actor);
			res.addObject("showBack", false);
			res.addObject("isPrincipalAuthorizedEdit", true);
		} else {
			final Folder parent = this.folderService.findOne(parentId);
			folders = this.folderService.findByActorAndParent(actor, parent);
			if (!parent.getParentFolder().equals(parent))
				res.addObject("backFolderId", parent.getParentFolder().getId());
			res.addObject("showBack", true);
			this.isPrincipalAuthorizedEdit(res, parent, false);
		}
		res.addObject("folders", folders);
		res.addObject("requestURI", "folder/actor/list.do");
		return res;
	}

	// Create

	@SuppressWarnings("unused")
	@RequestMapping("create")
	private ModelAndView create() {
		final Actor actor = this.actorService.findPrincipal();
		final Folder folder = this.folderService.create(actor);
		final ModelAndView res = this.createEditModelAndView(folder);
		return res;
	}

	// Edit

	@SuppressWarnings("unused")
	@RequestMapping("edit")
	private ModelAndView edit(@RequestParam(required = true) final Integer folderId) {
		final Folder folder = this.folderService.findOne(folderId);
		Assert.notNull(folder);
		final ModelAndView res = this.createEditModelAndView(folder);
		return res;
	}

	// Save

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "save")
	private ModelAndView save(@Valid final Folder folder, final BindingResult binding) {
		ModelAndView res = null;
		if (binding.hasErrors())
			res = this.createEditModelAndView(folder);
		else
			try {
				this.folderService.save(folder);
				res = new ModelAndView("redirect:list.do");
			} catch (final Throwable t) {
				res = this.createEditModelAndView(folder, "cannot.commit.error");
			}
		return res;
	}

	// Delete

	@SuppressWarnings("unused")
	@RequestMapping(value = "edit", method = RequestMethod.POST, params = "delete")
	private ModelAndView delete(final Folder folder, final BindingResult binding) {
		ModelAndView res = null;
		try {
			this.folderService.delete(folder);
			res = new ModelAndView("redirect:list.do");
		} catch (final Throwable t) {
			res = this.createEditModelAndView(folder, "cannot.commit.error");
		}
		return res;
	}

	// Ancillary methods

	private ModelAndView createEditModelAndView(final Folder folder) {
		return this.createEditModelAndView(folder, null);
	}

	private ModelAndView createEditModelAndView(final Folder folder, final String message) {
		final ModelAndView res = new ModelAndView("folder/edit");
		final Actor principal = this.actorService.findPrincipal();
		final Collection<Folder> folders = this.folderService.findAllByActor(principal);
		folders.remove(folder);
		res.addObject("folder", folder);
		res.addObject("message", message);
		res.addObject("folders", folders);
		this.isPrincipalAuthorizedEdit(res, folder, true);
		return res;
	}

	private void isPrincipalAuthorizedEdit(final ModelAndView modelAndView, final Folder folder, final Boolean isEdit) {
		Boolean res = false;
		final Actor principal = this.actorService.findPrincipal();
		if (folder.getActor().equals(principal) && !folder.getSystem() && isEdit)
			res = true;
		else if (folder.getActor().equals(principal) && !isEdit)
			res = true;
		modelAndView.addObject("isPrincipalAuthorizedEdit", res);
	}

}
