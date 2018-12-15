
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
import domain.Complaint;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ComplaintServiceTest extends AbstractTest {

	// Service under test

	@Autowired
	private ComplaintService	complaintService;


	// Tests

	@Test
	public void testCreateComplaint() {
		final Complaint complaint;
		complaint = this.complaintService.create();
		Assert.notNull(complaint);
	}

	@Test
	public void testDeleteComplaint() {

		final Complaint complaint;
		complaint = this.complaintService.findOne(super.getEntityId("complaint2"));
		this.complaintService.delete(complaint);

	}

	@Test
	public void testFindAllComplaint() {
		final Collection<Complaint> complaints;
		complaints = this.complaintService.findAll();
		Assert.notEmpty(complaints);
		Assert.notNull(complaints);

	}

	@Test
	public void testFindOneComplaint() {
		final Complaint complaint;

		complaint = this.complaintService.findOne(super.getEntityId("complaint1"));
		Assert.notNull(complaint);

	}

}
