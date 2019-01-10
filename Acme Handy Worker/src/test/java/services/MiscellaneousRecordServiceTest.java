
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
import domain.MiscellaneousRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MiscellaneousRecordServiceTest extends AbstractTest {

	@Autowired
	private MiscellaneousRecordService	miscellaneousRecordService;


	@Test
	public void testCreateMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord;

		miscellaneousRecord = this.miscellaneousRecordService.create();

		Assert.notNull(miscellaneousRecord);
		Assert.isNull(miscellaneousRecord.getTitle());

	}

	@Test
	public void testSaveMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord, saved;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		String title, attachment, comments;

		super.authenticate("handyworker1");

		miscellaneousRecord = this.miscellaneousRecordService.create();

		title = "miscellaneous record 1";
		attachment = "http://www.google.com";
		comments = "comment";

		miscellaneousRecord.setTitle(title);

		saved = this.miscellaneousRecordService.save(miscellaneousRecord);

		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(miscellaneousRecords.contains(saved));

		super.authenticate(null);
	}

	@Test
	public void testDeleteMiscellaneousRecord() {
		MiscellaneousRecord miscellaneousRecord;
		Collection<MiscellaneousRecord> miscellaneousRecords;

		super.authenticate("handyworker1");

		miscellaneousRecord = this.miscellaneousRecordService.findOne(super.getEntityId("miscellaneousRecord1"));
		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(miscellaneousRecords.contains(miscellaneousRecord));

		this.miscellaneousRecordService.delete(miscellaneousRecord);

		miscellaneousRecords = this.miscellaneousRecordService.findAll();

		Assert.isTrue(!(miscellaneousRecords.contains(miscellaneousRecord)));

		super.authenticate(null);
	}

}
