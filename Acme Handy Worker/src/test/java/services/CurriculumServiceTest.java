// Revisar

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
import domain.Curriculum;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CurriculumServiceTest extends AbstractTest {

	// Service under test 

	@Autowired
	private CurriculumService	curriculumService;


	// Tests 

	@Test
	public void testCreatecurriculum() {
		final Curriculum curriculum;
		curriculum = this.curriculumService.create();
		Assert.notNull(curriculum);
	}

	@Test
	public void testCreateCurriculum() {
		Curriculum curriculum;

		curriculum = this.curriculumService.create();

		Assert.notNull(curriculum);

		Assert.notNull(curriculum.getTicker());
		Assert.notNull(curriculum.getEducationRecords());
		Assert.notNull(curriculum.getProfessionalRecords());
		Assert.notNull(curriculum.getEndorserRecords());
		Assert.notNull(curriculum.getMiscellaneousRecords());

	}

	@Test
	public void testDeleteCurriculum() {
		Curriculum curriculum, deleted;
		int id;

		super.authenticate("handyworker1");

		id = super.getEntityId("curriculum1");
		System.out.println(id);
		curriculum = this.curriculumService.findOne(id);
		System.out.println(curriculum);

		this.curriculumService.delete(curriculum);

		deleted = this.curriculumService.findOne(id);
		System.out.println(deleted);
		Assert.isNull(deleted);

		super.authenticate(null);

	}

	@Test
	public void testDeletecurriculumSimple() {

		final Curriculum curriculum;

		curriculum = this.curriculumService.findOne(super.getEntityId("curriculum2"));

		this.curriculumService.delete(curriculum);

	}

	@Test
	public void testFindAllCurriculum() {
		final Collection<Curriculum> curricula;
		curricula = this.curriculumService.findAll();
		Assert.notEmpty(curricula);
		Assert.notNull(curricula);

	}

	@Test
	public void testFindOnecurriculum() {
		final Curriculum curriculum;

		curriculum = this.curriculumService.findOne(super.getEntityId("curriculum1"));
		Assert.notNull(curriculum);

	}
}
