
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
import domain.ProfessionalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ProfessionalRecordServiceTest extends AbstractTest {

	@Autowired
	private ProfessionalRecordService	professionalRecordService;


	/*
	 * @Test
	 * public void testSaveProfessionalRecord() {
	 * final ProfessionalRecord professionalRecord, saved;
	 * final Collection<ProfessionalRecord> professionalRecords;
	 * final String companyName, role, attachmentLink, comment;
	 * final Date startDate, endDate;
	 * 
	 * super.authenticate("handyworker1");
	 * professionalRecord = this.professionalRecordService.create();
	 * 
	 * companyName = "Ayesa";
	 * role = "Salesforce Developer";
	 * startDate = new Date(1);
	 * endDate = new Date(4);
	 * attachmentLink = "http://wwww.ayesa.com";
	 * comment = "comment1";
	 * 
	 * professionalRecord.setCompanyName(companyName);
	 * professionalRecord.setStartDate(startDate);
	 * professionalRecord.setEndDate(endDate);
	 * professionalRecord.setRole(role);
	 * professionalRecord.setAttachmentLink(attachmentLink);
	 * professionalRecord.setComment(comment);
	 * 
	 * saved = this.professionalRecordService.save(professionalRecord);
	 * 
	 * professionalRecords = this.professionalRecordService.findAll();
	 * 
	 * Assert.isTrue(professionalRecords.contains(saved));
	 * 
	 * super.authenticate(null);
	 * 
	 * }
	 */

	@Test
	public void testDeleteProfessionalRecord() {
		ProfessionalRecord professionalRecord;
		Collection<ProfessionalRecord> professionalRecords;

		super.authenticate("handyworker1");
		professionalRecord = this.professionalRecordService.findOne(super.getEntityId("professionalRecord1"));
		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(professionalRecords.contains(professionalRecord));

		this.professionalRecordService.delete(professionalRecord);

		professionalRecords = this.professionalRecordService.findAll();

		Assert.isTrue(!(professionalRecords.contains(professionalRecord)));

		super.authenticate(null);

	}

}
