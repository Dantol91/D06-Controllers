
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import security.Authority;
import domain.Actor;
import domain.Complaint;
import domain.Note;
import domain.Report;
import domain.Url;

@Service
@Transactional
public class ReportService {

	// Repository

	@Autowired
	private ReportRepository	repository;

	// Services

	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ServiceUtils		serviceUtils;
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private ApplicationService	applicationService;


	// CRUD methods

	public Report findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Report> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Report> findAll() {
		return this.repository.findAll();
	}

	public Collection<Report> findAll(final Complaint dependency) {
		this.serviceUtils.checkId(dependency);
		Assert.notNull(this.complaintService.findOne(dependency.getId()));
		return this.repository.findByComplaint(dependency.getId());
	}

	public Report findByComplaint(final Complaint dependency) {
		this.serviceUtils.checkId(dependency);
		Assert.notNull(this.complaintService.findOne(dependency.getId()));
		final List<Report> reports = new ArrayList<Report>(this.repository.findByComplaint(dependency.getId()));
		return reports.get(0);
	}

	public Report create(final Complaint dependency) {
		final Report res = new Report();
		res.setComplaint(dependency);
		res.setAttachments(new ArrayList<Url>());
		res.setDraft(true);
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		res.setNotes(new ArrayList<Note>());
		return res;
	}

	public Report save(final Report object) {
		final Report report = (Report) this.serviceUtils.checkObjectSave(object);
		if (report.getId() == 0) {
			this.serviceUtils.checkId(report.getComplaint());
			Assert.notNull(this.complaintService.findOne(report.getComplaint().getId()));
			report.setMoment(new Date(System.currentTimeMillis() - 1000));
		} else {
			Assert.isTrue(report.getDraft());
			report.setAttachments(object.getAttachments());
			report.setDescription(object.getDescription());
			report.setDraft(object.getDraft());
		}
		this.serviceUtils.checkActor(report.getComplaint().getReferee());
		this.serviceUtils.checkAuthority(Authority.REFEREE);
		final Report res = this.repository.save(report);
		return res;
	}

	public void delete(final Report object) {
		final Report report = (Report) this.serviceUtils.checkObject(object);
		Assert.isTrue(report.getDraft());
		this.serviceUtils.checkActor(report.getComplaint().getReferee());
		this.serviceUtils.checkAuthority(Authority.REFEREE);
		this.complaintService.save(report.getComplaint());
		this.repository.delete(report);
	}

	public Report profile(final Report r) {
		final Report report = (Report) this.serviceUtils.checkObject(r);
		this.serviceUtils.checkAnyActor(new Actor[] {
			r.getComplaint().getReferee(), r.getComplaint().getFixuptask().getCustomer(),

		});
		return report;
	}
	// Other methods

	public void flush() {
		this.repository.flush();
	}

	public Map<String, Double> refeeReportStats() {
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		final Double[] statistics = this.repository.refeeReportStats();
		final Map<String, Double> res = new HashMap<>();
		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;

	}

}
