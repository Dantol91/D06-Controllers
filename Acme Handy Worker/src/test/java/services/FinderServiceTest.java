
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Finder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class FinderServiceTest extends AbstractTest {

	//Service under test
	@Autowired
	private FinderService	finderService;


	@Test
	public void testCreateFinder() {
		final Finder finder;
		finder = this.finderService.create();
		Assert.notNull(finder);
	}

	@Test
	public void testSaveFinder() {
		final Finder finder;
		final Finder finderSaved;
		final Date dateFinder;
		final Date dateFinderSaved;

		finder = this.finderService.findOne(super.getEntityId("finder1"));
		//	dateFinder = finder.getEndDate();

		finder.setKeyword("fixUpTask");
		finderSaved = this.finderService.save(finder);
		//	dateFinderSaved = finderSaved.getEndDate();

		//Assert.isTrue(dateFinder != dateFinderSaved);
		Assert.notNull(finderSaved);
	}
	@Test
	public void testDeleteFinder() {

		final Finder finder;
		finder = this.finderService.findOne(super.getEntityId("finder1"));
		this.finderService.delete(finder);

	}

	@Test
	public void testFindAllFinder() {
		Collection<Finder> finders;
		finders = this.finderService.findAll();
		Assert.notEmpty(finders);
		Assert.notNull(finders);

	}

	@Test
	public void testFindOneFinder() {
		Finder finder;

		finder = this.finderService.findOne(super.getEntityId("finder1"));
		Assert.notNull(finder);

	}

}
