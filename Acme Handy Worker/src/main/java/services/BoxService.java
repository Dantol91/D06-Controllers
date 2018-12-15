
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.BoxRepository;
import domain.Actor;
import domain.Box;
import domain.Message;

@Service
@Transactional
public class BoxService {

	// Managed repository
	@Autowired
	private BoxRepository	boxRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;

	@Autowired
	private MessageService	messageService;


	// Constructor
	public BoxService() {
		super();
	}

	// Simple CRUD methods

	public Box create() {

		final Box box = new Box();

		box.setSystemBox(false);
		box.setMessages(new ArrayList<Message>());

		Actor actor;

		actor = this.actorService.findByPrincipal();

		final Collection<Box> actorBoxes = actor.getBoxes();

		actorBoxes.add(box);

		actor.setBoxes(actorBoxes);

		box.setActor(this.actorService.findByPrincipal());

		return box;
	}

	public Box save(final Box box) {
		Assert.notNull(box);
		Assert.isTrue(!box.getSystemBox());

		if (box.getId() != 0)
			this.checkPrincipal(box);

		Actor actor;
		actor = this.actorService.findByPrincipal();

		box.setActor(actor);

		final Box boxSaved = this.boxRepository.save(box);
		final Collection<Box> actorBoxes = actor.getBoxes();

		actorBoxes.add(boxSaved);
		actor.setBoxes(actorBoxes);
		this.actorService.save(actor);

		return boxSaved;

	}

	public Box findOne(final int boxId) {
		Assert.notNull(boxId);
		Assert.isTrue(boxId != 0);
		final Box box;
		box = this.boxRepository.findOne(boxId);
		return box;
	}

	public void delete(final Box box) {
		Assert.notNull(box);
		Assert.isTrue(!box.getSystemBox());

		this.checkPrincipal(box);
		final Collection<Message> messages = box.getMessages();
		final Actor a = this.actorService.findByPrincipal();
		a.getBoxes().remove(box);
		this.actorService.save(a);

		final Collection<Box> childBoxes = this.boxRepository.getChildBoxes(box.getId());

		if (childBoxes.size() > 0)
			for (final Box child : childBoxes)
				this.delete(child);

		this.boxRepository.delete(box);
		this.messageService.delete(messages);

	}

	public void delete(final Iterable<Box> boxes) {
		Assert.notNull(boxes);

		this.boxRepository.delete(boxes);
	}

	public Collection<Box> findAll() {
		return this.boxRepository.findAll();
	}

	// Other business methods

	public void checkPrincipal(final Box box) {
		Actor actor;
		actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor.getBoxes().contains(box));
	}

	// Other business methods

	public void createSystemBoxes(final Actor actor) {

		Box inbox;
		Box outbox;
		Box trashbox;
		Box spambox;
		final Collection<Box> boxes = new ArrayList<Box>();

		inbox = new Box();
		inbox.setSystemBox(true);
		inbox.setName("in box");
		inbox.setMessages(new ArrayList<Message>());
		inbox.setActor(actor);
		boxes.add(inbox);
		this.boxRepository.save(inbox);

		outbox = new Box();
		outbox.setSystemBox(true);
		outbox.setName("out box");
		outbox.setMessages(new ArrayList<Message>());
		outbox.setActor(actor);
		boxes.add(outbox);
		this.boxRepository.save(outbox);

		trashbox = new Box();
		trashbox.setSystemBox(true);
		trashbox.setName("trash box");
		trashbox.setMessages(new ArrayList<Message>());
		trashbox.setActor(actor);
		boxes.add(trashbox);
		this.boxRepository.save(trashbox);

		spambox = new Box();
		spambox.setSystemBox(true);
		spambox.setName("spam box");
		spambox.setMessages(new ArrayList<Message>());
		spambox.setActor(actor);
		boxes.add(spambox);
		this.boxRepository.save(spambox);

		this.boxRepository.save(boxes);
		final Box savedinbox = this.boxRepository.save(inbox);
		final Box savedoutbox = this.boxRepository.save(outbox);
		final Box savedtrashbox = this.boxRepository.save(trashbox);
		final Box savedspambox = this.boxRepository.save(spambox);

		boxes.add(savedinbox);
		boxes.add(savedoutbox);
		boxes.add(savedtrashbox);
		boxes.add(savedspambox);

		actor.setBoxes(boxes);

		this.boxRepository.save(boxes);

		// managerService.save((Manager)actor);

	}
	public Box getOutBoxFromActorId(final int id) {
		return this.boxRepository.getOutBoxFolderFromActorId(id);
	}

	public Box getInBoxFromActorId(final int id) {
		return this.boxRepository.getInBoxFromActorId(id);
	}

	public Box getSpamBoxFromActorId(final int id) {
		return this.boxRepository.getSpamBoxFromActorId(id);
	}

	public Box getTrashBoxFolderFromActorId(final int id) {
		return this.boxRepository.getTrashBoxFromActorId(id);
	}

	public Collection<Box> getFirstLevelBoxFromActorId(final int actorId) {

		return this.boxRepository.getFirstLevelBoxesFromActorId(actorId);
	}

	public Box getBoxFromMessageId(final int messageId) {

		return this.boxRepository.getBoxFromMessageId(messageId);
	}

	public Collection<Box> getChildBoxes(final int folderId) {

		return this.boxRepository.getChildBoxes(folderId);
	}

	public Box getBoxFromMessageAndActorId(final int messageId, final int actorId) {

		return this.boxRepository.getBoxFromMessageAndActorId(messageId, actorId);
	}

}
