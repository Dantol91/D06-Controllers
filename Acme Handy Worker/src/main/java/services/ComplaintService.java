
package services;

import java.sql.Date;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;

@Service
@Transactional
public class ComplaintService {

	// Managed repository 

	@Autowired
	private ComplaintRepository	complaintRepository;

	// Supporting services 

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructors 

	public ComplaintService() {
		super();
	}
	// Simple CRUD methods 

	public Complaint create() {

		Complaint res;
		res = new Complaint();
		res.setTicker(this.fixUpTaskService.getTicker());
		res.setMoment(new Date(System.currentTimeMillis() - 1000));

		return res;

	}

	public Complaint save(final Complaint complaint) {
		Assert.notNull(complaint);
		return this.complaintRepository.save(complaint);

	}

	public Complaint findOne(final int complaintId) {
		Assert.isTrue(complaintId != 0);

		Complaint result;

		result = this.complaintRepository.findOne(complaintId);

		return result;
	}

	public Collection<Complaint> findAll() {
		Collection<Complaint> result;

		result = this.complaintRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Complaint complaint) {
		Assert.notNull(complaint);

		this.complaintRepository.delete(complaint);
	}

	// Other Business Methods 

	public Double[] computeMinMaxAvgStddevComplaintsPerFixUpTasks() {

		return this.complaintRepository.computeMinMaxAvgStddevComplaintsPerFixUpTasks();
	}

	/*
	 * public Collection<Complaint> findComplaintsByReferee(final Referee r) {
	 * return this.complaintRepository.findComplaintsByReferee(r.getId());
	 * }
	 * 
	 * public Collection<Complaint> findComplaintsWithoutReferee() {
	 * return this.complaintRepository.findComplaintsWithoutReferee();
	 * }
	 */

}
