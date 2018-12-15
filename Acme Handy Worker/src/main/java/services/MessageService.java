
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.Message;
import domain.Sponsor;

@Service
@Transactional
public class MessageService {

	// Managed repository
	@Autowired
	private MessageRepository		messageRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;
	@Autowired
	private BoxService				boxService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructor
	public MessageService() {
		super();
	}

	// Simple CRUD methods

	public Message create() {
		Message res;
		Date moment;
		res = new Message();
		final Actor actor = this.actorService.findByPrincipal();
		moment = new Date(System.currentTimeMillis() - 1);
		res.setSender(actor);
		res.setMoment(moment);
		return res;
	}

	public Message save(final Message message) {

		// Compruebo que no sea nulo el mensaje que me pasan
		Assert.notNull(message);
		Assert.notNull(message.getRecipient());

		// Se inicializa el momento en el que se envía
		Date moment;

		// Se inicializa el Box del destinatario
		final Box recipientBox;

		// Se inicializa el mensaje guardado
		Message saved = null;

		// Si el mensaje que me pasan ya había estado guardado en la base de
		// datos (se quiere cambiar de Box)

		// Si el mensaje se está guardando en la base de datos por
		// primera vez:
		// Se instancia el momento en que se envía como el momento actual
		moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);

		// Se guarda el mensaje en la base de datos
		saved = this.messageRepository.save(message);

		// Se hace una copia del mensaje original, guardo la copia en la base
		// de datos y
		// lo añado a la colección de mensajes del sender
		final Message copiedMessage = message;
		moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);
		final Message copiedAndSavedMessage = this.messageRepository.save(copiedMessage);

		// Se comprueba si el mensaje contiene texto marcado como spam
		// si contiene spam
		if (this.administratorService.checkIsSpam(saved.getBody()) || this.administratorService.checkIsSpam(saved.getSubject()))

			// Se instancia el box del destinatario como el spambox
			recipientBox = this.boxService.getSpamBoxFromActorId(saved.getRecipient().getId());
		else
			// Se instancia el box del destinatario como inbox

			recipientBox = this.boxService.getInBoxFromActorId(saved.getRecipient().getId());
		// Se cogen los mensajes del box del destinatario
		final Collection<Message> recipientBoxMessages = recipientBox.getMessages();

		// Se añade el mensaje guardado
		recipientBoxMessages.add(saved);

		// Se actualiza el conjunto de mensajes
		recipientBox.setMessages(recipientBoxMessages);

		// Se coge el sender
		final Actor sender = this.actorService.findByPrincipal();

		// Se coge el outbox del sender
		final Box senderOutbox = this.boxService.getOutBoxFromActorId(sender.getId());

		// Se cogen los mensajes del outbox del sender
		final Collection<Message> senderOutboxMessages = senderOutbox.getMessages();

		// Se añade el mensaje guardado a los mensajes del outbox del sender
		senderOutboxMessages.add(copiedAndSavedMessage);

		// Se actualizan los mensajes del outbox del sender
		senderOutbox.setMessages(senderOutboxMessages);
		this.boxService.save(senderOutbox);

		return saved;
	}

	public void delete(final Message message) {

		// Se comprueba que el mensaje que me pasan no sea nulo
		//Assert.notNull(message);

		// Se obtiene el actor que está logueado
		Actor actor = this.actorService.findByPrincipal();

		// Se comprueba que el mensaje que se pasa sea del actor que está logueado
		final String type = this.actorService.getType(actor.getUserAccount());

		if (type.equals("HANDYWORKER"))
			actor = (HandyWorker) actor;
		else if (type.equals("CUSTOMER"))
			actor = (Customer) actor;
		else if (type.equals("ADMIN"))
			actor = (Administrator) actor;
		else if (type.equals("SPONSOR"))
			actor = (Sponsor) actor;

		this.checkPrincipal(message, actor);

		// Se coge el trashbox del actor logueado
		final Box trashbox = this.boxService.getTrashBoxFolderFromActorId(actor.getId());

		// Se comprueba que el trashbox del actor logueado no sea nulo
		Assert.notNull(trashbox);

		// Si el mensaje que se pasa está en el trashbox del actor logueado:
		if (trashbox.getMessages().contains(message)) {

			// Se saca la collection de mensajes del trashbox del actor logueado
			final Collection<Message> trashboxMessages = trashbox.getMessages();
			// borro el mensaje que me pasan de la collection de mensajes del

			// Se borran los mensaje de Trashbox
			trashboxMessages.remove(message);

			// Se actualiza los mensajes del trashbox
			trashbox.setMessages(trashboxMessages);

			// borro el mensaje del sistema
			this.messageRepository.delete(message);

		} else {// Si el mensaje que se quiere borrar no está en el trashbox:

			// Se borra el mensaje del box en el que estaba

			final Box messageFolder = this.boxService.getBoxFromMessageId(message.getId());
			Assert.notNull(messageFolder);

			final Collection<Message> messages = messageFolder.getMessages();
			messages.remove(message);
			messageFolder.setMessages(messages);

			// Se coloca en el trashbox el mensaje
			final Collection<Message> trashboxMessages = trashbox.getMessages();
			trashboxMessages.add(message);
			trashbox.setMessages(trashboxMessages);

		}
	}

	public void delete(final Iterable<Message> messages) {
		Assert.notNull(messages);
		this.messageRepository.delete(messages);
	}

	public Message findOne(final int messageId) {
		return this.messageRepository.findOne(messageId);

	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();

	}

	// Other business methods

	public Message sendMessage() {
		Message result;
		Actor actor;
		Box outbox = null;
		Date sentDate;

		actor = this.actorService.findByPrincipal();
		outbox = this.boxService.getOutBoxFromActorId(actor.getId());
		sentDate = new Date();
		Assert.notNull(outbox);

		result = this.create();
		result.setSender(actor);
		result.setMoment(sentDate);
		outbox.getMessages().add(result);
		this.messageRepository.save(result);
		this.boxService.save(outbox);
		return result;
	}

	public void checkPrincipal(final Message message, final Actor actor) {

		final Collection<Message> messages = this.messageRepository.messagesFromActorId(actor.getId());
		Assert.isTrue(messages.contains(message));
	}

}
