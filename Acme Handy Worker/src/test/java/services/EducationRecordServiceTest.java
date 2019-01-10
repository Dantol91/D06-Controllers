
package services;

import java.sql.Date;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.EducationRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class EducationRecordServiceTest extends AbstractTest {

	@Autowired
	private EducationRecordService	educationRecordService;


	@Test
	public void testCreateEducationRecord() {
		EducationRecord educationRecord;

		educationRecord = this.educationRecordService.create();

		Assert.notNull(educationRecord);
		Assert.isNull(educationRecord.getDiplomaTitle());
		Assert.isNull(educationRecord.getInstitution());

	}

	@Test
	public void testSaveEducationRecord() {
		final EducationRecord educationRecord, saved;
		final Collection<EducationRecord> educationRecords;
		final String diplomaTitle, institution, attachmentLink, comment;
		final Date startDate, endDate;

		super.authenticate("handyworker1");
		educationRecord = this.educationRecordService.create();

		diplomaTitle = "Diploma B1";
		institution = "US";
		startDate = new Date(0);
		endDate = null;
		attachmentLink = "http://wwww.us.es";
		comment = "comment1";

		educationRecord.setDiplomaTitle(diplomaTitle);
		educationRecord.setInstitution(institution);

		saved = this.educationRecordService.save(educationRecord);

		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(educationRecords.contains(saved));

		super.authenticate(null);

	}

	@Test
	public void testDeleteEducationRecord() {
		EducationRecord educationRecord;
		Collection<EducationRecord> educationRecords;

		super.authenticate("handyworker1");
		educationRecord = this.educationRecordService.findOne(super.getEntityId("educationRecord1"));
		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(educationRecords.contains(educationRecord));

		this.educationRecordService.delete(educationRecord);

		educationRecords = this.educationRecordService.findAll();

		Assert.isTrue(!(educationRecords.contains(educationRecord)));

		super.authenticate(null);

	}

}
