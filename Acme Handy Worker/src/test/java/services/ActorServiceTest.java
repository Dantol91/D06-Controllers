
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import security.Authority;
import security.UserAccount;
import utilities.AbstractTest;
import domain.Actor;
import domain.Administrator;
import domain.HandyWorker;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ActorServiceTest extends AbstractTest {

	// Service under test ---------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private UserAccountService		userAccountService;


	@Test
	public void updatePhone() {

		final Actor actor = (Actor) this.actorService.findAll().toArray()[0];
		System.out.println(actor.getPhone());
		actor.setPhone("+34662178210");
		System.out.println(actor.getPhone());

	}

	@Test
	public void testCreateActor() {

		final Actor actor = this.handyWorkerService.create();
		Assert.notNull(actor);

		System.out.println("Test Actor: " + actor);

	}

	@Test
	public void testSaveActor() {
		final UserAccount us = new UserAccount();
		final Authority actor = new Authority();

		actor.setAuthority("ADMIN");

		final Collection<Authority> authorities = new ArrayList<Authority>();

		authorities.add(actor);
		us.setAuthorities(authorities);
		final Actor saved;
		Collection<Actor> actors;

		final Administrator a = this.administratorService.create();

		a.setName("Name1");
		a.setSurname("Surname1");
		a.setPhone("6667852541");
		a.setAddress("calle 1");
		//	saved = this.actorService.save(a);
		actors = this.actorService.findAll();
		//	Assert.isTrue(actors.contains(saved));

		//	System.out.println("Test Actor: " + this.actorService.save(a));
	}

	@Test
	public void testFindOneActor() {
		final Actor saved;
		final HandyWorker h = this.handyWorkerService.create();
		h.setName("Name1");
		h.setSurname("Surname1");
		h.setScore(5.0);
		//	saved = this.actorService.save(h);
		//	final int actorId = saved.getId();
		//	final Actor a = this.actorService.findOne(actorId);
		//		Assert.isTrue(a.equals(saved));

		System.out.println("Test FindOneActor: " + this.handyWorkerService.create());
	}

}
