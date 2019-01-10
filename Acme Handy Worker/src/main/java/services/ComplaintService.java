
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ComplaintRepository;
import domain.Complaint;
import domain.FixupTask;
import domain.Referee;

@Service
@Transactional
public class ComplaintService {

	//Managed Repository

	@Autowired
	private ComplaintRepository	complaintRepository;
	//Supporting Service
	@Autowired
	private TickerService		ticketableService;
	@Autowired
	private FixupTaskService	fixupTaskService;
	@Autowired
	private ServiceUtils		serviceUtils;


	// Simple CRUD methods

	public Complaint create() {
		Complaint res;
		res = new Complaint();
		res.setTicker(this.ticketableService.createTicker());
		res.setMoment(new Date(System.currentTimeMillis() - 1000));

		return res;
	}

	public Collection<Complaint> findAll() {
		return this.complaintRepository.findAll();
	}

	public Complaint findOne(final int complaintId) {
		return this.complaintRepository.findOne(complaintId);
	}

	public Complaint save(final Complaint complaint) {
		Complaint res = null;
		Assert.notNull(complaint);

		this.serviceUtils.checkIdSave(complaint);

		res = this.complaintRepository.save(complaint);
		return res;
	}

	//Other business methods

	public Collection<Complaint> findAllComplaintsByReferee(final Referee r) {
		return this.complaintRepository.SearchComplaintByReferee(r.getId());
	}

	public Collection<Complaint> findAllComplaintsWithoutReferee() {
		return this.complaintRepository.SearchComplaintWithoutReferee();
	}

	public Collection<Complaint> findByFixupTask(final FixupTask fixupTask) {
		Assert.notNull(fixupTask);
		Assert.isTrue(fixupTask.getId() > 0);
		Assert.notNull(this.fixupTaskService.findOne(fixupTask.getId()));
		return this.complaintRepository.findByFixupTaskId(fixupTask.getId());
	}

	public Collection<Complaint> findAll(final FixupTask dependency) {
		return this.findByFixupTask(dependency);
	}

	public Complaint create(final FixupTask dependency) {
		Assert.notNull(dependency);
		Assert.isTrue(dependency.getId() > 0);
		Assert.notNull(this.fixupTaskService.findOne(dependency.getId()));
		final Complaint res = this.create();
		res.setFixuptask(dependency);
		return res;
	}

	public void delete(final Complaint object) {
		throw new IllegalArgumentException("Unallowed method");
	}
}
