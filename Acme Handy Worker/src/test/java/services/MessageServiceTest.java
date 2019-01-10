
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MessageServiceTest extends AbstractTest {

	@Autowired
	private MessageService	messageService;


	@Test
	public void testCreateMessage() {
		super.authenticate("HandyWorker1");
		final Message message;
		//Assert.notNull(message);
		super.unauthenticate();

	}

	@Test
	public void testFindOneMessage() {
		Message message;
		message = this.messageService.findOne(super.getEntityId("message2"));
		Assert.notNull(message);

		System.out.println("Message: " + message.getId());
	}

	@Test
	public void testFindAllMessage() {
		Collection<Message> messages;
		messages = this.messageService.findAll();
		Assert.notEmpty(messages);
		Assert.notNull(messages);

		System.out.println("Message: " + messages);
	}

	@Test
	public void testSearchMessage() {
		final Collection<Message> messages = this.messageService.findAll();

		final Message message = this.messageService.findOne(1692);

		System.out.println("Message" + message.getSender());
		Assert.isTrue(messages.contains(message));

	}

	@Test
	public void testDeleteMessageFromTrashBox() {
		this.authenticate("customer1");
		List<Message> messages;
		messages = (List<Message>) this.messageService.findAll();
		final Message message = messages.get(0);

		System.out.println("Message: " + message);
		this.authenticate(null);

	}

	/*
	 * @Test
	 * public void testSave() {
	 * super.authenticate("customer2");
	 * Message message = null;
	 * //final Message messageSaved;
	 * 
	 * //message = this.messageService.findOne(super.getEntityId("message1"));
	 * 
	 * message = this.messageService.save(message);
	 * 
	 * //Assert.notNull(messageSaved);
	 * super.unauthenticate();
	 * 
	 * System.out.println("Message: " + message);
	 * }
	 */

	/*
	 * @Test
	 * public void testDelete() {
	 * final Message message;
	 * 
	 * message = this.messageService.findOne(super.getEntityId("message2"));
	 * //Assert.notNull(message);
	 * this.messageService.delete(message);
	 * 
	 * System.out.println("Message: " + message);
	 * 
	 * }
	 */
}
