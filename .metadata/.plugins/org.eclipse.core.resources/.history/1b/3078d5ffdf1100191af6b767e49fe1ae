
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CurriculumRepository;
import domain.Curriculum;
import domain.EducationRecord;
import domain.EndorserRecord;
import domain.HandyWorker;
import domain.MiscellaneousRecord;
import domain.ProfessionalRecord;

@Service
@Transactional
public class CurriculumService {

	// Managed Repository 

	@Autowired
	private CurriculumRepository	curriculumRepository;

	// Supporting Services 

	@Autowired
	private FixUpTaskService		fixUpTaskService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private PersonalRecordService	personalRecordService;


	// Constructor 

	public CurriculumService() {
		super();
	}

	// Simple CRUD Methods 

	public Curriculum create() {
		Curriculum c;
		final String ticker;
		Collection<EndorserRecord> endorserRecords;
		Collection<ProfessionalRecord> professionalRecords;
		Collection<MiscellaneousRecord> miscellaneousRecords;
		Collection<EducationRecord> educationRecords;

		ticker = this.fixUpTaskService.getTicker();
		endorserRecords = new ArrayList<>();
		professionalRecords = new ArrayList<>();
		miscellaneousRecords = new ArrayList<>();
		educationRecords = new ArrayList<>();

		c = new Curriculum();
		c.setTicker(ticker);
		c.setEndorserRecords(endorserRecords);
		c.setProfessionalRecords(professionalRecords);
		c.setMiscellaneousRecords(miscellaneousRecords);
		c.setEducationRecords(educationRecords);

		return c;
	}

	public Curriculum save(final Curriculum curriculum) {
		Assert.notNull(curriculum);

		HandyWorker h;

		h = (HandyWorker) this.actorService.findByPrincipal();

		if (curriculum.getId() != 0)
			this.checkPrincipal(curriculum.getId());

		final Curriculum c = this.curriculumRepository.save(curriculum);

		h.setCurriculum(c);
		this.handyWorkerService.save(h);

		return c;
	}

	public void delete(final Curriculum curriculum) {
		Assert.notNull(curriculum);
		//this.checkPrincipal(curriculum.getId());
		this.personalRecordService.delete(curriculum.getPersonalRecord());

		final HandyWorker h;
		h = (HandyWorker) this.actorService.findByPrincipal();
		h.setCurriculum(null);
		this.handyWorkerService.save(h);
		this.curriculumRepository.delete(curriculum);
	}

	public Collection<Curriculum> findAll() {
		return this.curriculumRepository.findAll();
	}

	public Curriculum findOne(final int curriculumId) {
		Assert.notNull(curriculumId);

		Curriculum c;

		c = this.curriculumRepository.findOne(curriculumId);

		return c;
	}

	public Curriculum findOneToEdit(final int curriculumId) {
		Assert.notNull(curriculumId);

		Curriculum c;

		c = this.curriculumRepository.findOne(curriculumId);

		this.checkPrincipal(c.getId());

		return c;
	}

	// Other business methods

	public Curriculum getCurriculumByRangerId(final int handyWorkerId) {
		Assert.notNull(handyWorkerId);

		return this.curriculumRepository.getCurriculumByHandyWorkerId(handyWorkerId);
	}

	public void checkPrincipal(final int curriculumId) {
		HandyWorker a;
		HandyWorker h;

		a = (HandyWorker) this.actorService.findByPrincipal();
		h = this.handyWorkerService.getHandyWorkerByCurriculumId(curriculumId);
		Assert.isTrue(a.equals(h));
	}

}
