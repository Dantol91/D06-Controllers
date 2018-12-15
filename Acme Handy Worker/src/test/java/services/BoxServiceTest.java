
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Box;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class BoxServiceTest extends AbstractTest {

	// Service under test
	@Autowired
	private BoxService	boxService;


	@Test
	public void testCreateBox() {
		super.authenticate("HandyWorker1");
		final Box box;
		box = this.boxService.create();
		Assert.notNull(box);
		super.unauthenticate();

		System.out.println("BoxCreate: " + this.boxService.create());

	}

	@Test
	public void testSaveBox() {
		this.authenticate("handyWorker1");
		final Box box, saved;

		final Collection<Box> boxes;

		box = this.boxService.create();
		box.setName("box1");

		saved = this.boxService.save(box);
		boxes = this.boxService.findAll();
		Assert.isTrue(boxes.contains(saved));
		this.authenticate(null);

	}

	@Test
	public void testDeleteBox() {
		this.authenticate("customer2");
		final Box box;
		Box saved;
		final Collection<Box> boxes;
		box = this.boxService.create();
		box.setName("b1");
		saved = this.boxService.save(box);
		this.boxService.delete(saved);
		boxes = this.boxService.findAll();
		Assert.isTrue(!boxes.contains(saved));

	}

	@Test
	public void testFindAllBox() {
		final Collection<Box> boxes;
		boxes = this.boxService.findAll();
		Assert.notEmpty(boxes);
		Assert.notNull(boxes);

		System.out.println("BoxFindAll: " + this.boxService.findAll());

	}

	@Test
	public void testFindOneBox() {
		Box box;
		box = this.boxService.findOne(super.getEntityId("box1.1"));
		Assert.notNull(box);

		System.out.println("BoxFindOne: " + this.boxService.findOne(super.getEntityId("box1.1")));

	}

}
