
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository

	@Autowired
	private ReportRepository	reportRepository;


	//Supporting services

	// Constructor

	public ReportService() {
		super();
	}

	// Simple CRUD methods 

	public Report create() {
		final Report r;

		r = new Report();

		return r;
	}

	public Report save(final Report report) {
		Assert.notNull(report);

		return this.reportRepository.save(report);
	}

	public Report findOne(final int reportId) {
		Assert.isTrue(reportId != 0);

		Report result;

		result = this.reportRepository.findOne(reportId);

		return result;
	}

	public Collection<Report> findAll() {
		Collection<Report> result;

		result = this.reportRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Report report) {
		Assert.notNull(report);

		this.reportRepository.delete(report);
	}

	// Other Business Methods 

}
