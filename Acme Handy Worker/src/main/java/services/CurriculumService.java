
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Actor;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.PersonalRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Managed repository 
	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting services

	@Autowired
	private TickerService			ticketableService;
	@Autowired
	private HandyWorkerService		handyWorkerService;


	// Simple CRUD methods 

	public Curriculum create() {
		Curriculum res;

		res = new Curriculum();

		res.setTicker(this.ticketableService.createTicker());
		res.setPersonalRecord(new PersonalRecord());
		res.setEducationRecords(new ArrayList<EducationRecord>());
		res.setProfessionalRecords(new ArrayList<ProfessionalRecord>());
		res.setEndorserRecords(new ArrayList<EndorserRecord>());
		res.setMiscellaneousRecords(new ArrayList<MiscellaneousRecord>());

		return res;
	}

	public Curriculum findOne(final int curriculumId) {
		Curriculum res;

		res = this.curriculumRepository.findOne(curriculumId);

		return res;
	}

	public Collection<Curriculum> findAll() {
		Collection<Curriculum> res;

		res = this.curriculumRepository.findAll();

		return res;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		Curriculum res;

		Assert.notNull(curriculum.getHandyWorker());
		Assert.notNull(curriculum.getPersonalRecord());

		res = this.curriculumRepository.save(curriculum);

		return res;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);

		this.curriculumRepository.delete(curriculum);

	}

	public Curriculum findByHandyWorker(final HandyWorker h) {
		Assert.notNull(h);
		Assert.isTrue(h.getId() > 0);
		Assert.notNull(this.handyWorkerService.findOne(h.getId()));
		return this.curriculumRepository.findByHandyWorkerId(h.getId());
	}

	public Collection<Curriculum> findAllByActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.handyWorkerService.findOne(a.getId()));
		final Collection<Curriculum> res = new ArrayList<Curriculum>();
		res.add(this.findByHandyWorker(this.handyWorkerService.findOne(a.getId())));
		return res;
	}

	public Curriculum create(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.handyWorkerService.findOne(a.getId()));
		final Curriculum res = this.create();
		res.setHandyWorker(this.handyWorkerService.findOne(a.getId()));
		return res;
	}

}
