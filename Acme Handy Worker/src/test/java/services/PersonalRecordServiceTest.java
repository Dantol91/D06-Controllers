
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
import domain.PersonalRecord;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class PersonalRecordServiceTest extends AbstractTest {

	@Autowired
	private PersonalRecordService	personalRecordService;


	@Test
	public void testCreatePersonalRecord() {
		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();

		Assert.notNull(personalRecord);
		Assert.isNull(personalRecord.getFullName());
		Assert.isNull(personalRecord.getPhoto());
		Assert.isNull(personalRecord.getEmail());
		Assert.isNull(personalRecord.getPhone());

	}

	@Test
	public void testSavePersonalRecord() {
		PersonalRecord personalRecord, saved;
		Collection<PersonalRecord> personalRecords;
		String fullName;
		final String photo;
		String email;
		final String phone;
		String linkedInProfile;

		super.authenticate("handyworker1");
		personalRecord = this.personalRecordService.create();

		fullName = "Manuel";
		photo = "http://www.ph1.com";
		email = "mrodo@gmail.com";
		phone = "668789875";
		linkedInProfile = "http://www.linkedin.com";

		personalRecord.setFullName(fullName);
		personalRecord.setPhoto(photo);
		personalRecord.setEmail(email);
		personalRecord.setPhone(phone);

		saved = this.personalRecordService.save(personalRecord);

		personalRecords = this.personalRecordService.findAll();

		Assert.isTrue(personalRecords.contains(saved));

		super.authenticate(null);
	}

}
