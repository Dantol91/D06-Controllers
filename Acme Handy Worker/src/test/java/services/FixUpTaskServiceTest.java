
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
import domain.FixUpTask;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FixUpTaskServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private FixUpTaskService	fixUpTaskService;


	//Tests

	@Test
	public void testCreateFixUpTask() {
		final FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.create();
		Assert.notNull(fixUpTask);
	}

	/*
	 * @Test
	 * public void testSaveFixUpTask() {
	 * final Finder finder;
	 * final Finder finderSaved;
	 * Date dateFinder;
	 * Date dateFinderSaved;
	 * 
	 * finder = this.fixUpTaskService.findOne(super.getEntityId("finder1"));
	 * dateFinder = finder.getEndDate();
	 * 
	 * finder.setKeyword("fixUpTask");
	 * finderSaved = this.fixUpTaskService.save(finder);
	 * dateFinderSaved = finderSaved.getEndDate();
	 * 
	 * Assert.isTrue(dateFinder != dateFinderSaved);
	 * Assert.notNull(finderSaved);
	 * }
	 */

	@Test
	public void testDeleteFixUpTask() {

		final FixUpTask fixUpTask;
		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask2"));
		this.fixUpTaskService.delete(fixUpTask);

	}

	@Test
	public void testFindAllFixUpTask() {
		final Collection<FixUpTask> fixUpTasks;
		fixUpTasks = this.fixUpTaskService.findAll();
		Assert.notEmpty(fixUpTasks);
		Assert.notNull(fixUpTasks);

	}

	@Test
	public void testFindOneFixUpTask() {
		FixUpTask fixUpTask;

		fixUpTask = this.fixUpTaskService.findOne(super.getEntityId("fixUpTask1"));
		Assert.notNull(fixUpTask);

	}

}
