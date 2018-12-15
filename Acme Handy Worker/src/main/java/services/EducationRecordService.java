
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.EducationRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationRecordService {

	// MANAGED REPOSITORY 

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	// SUPPORTING SERVICES 

	@Autowired
	private ActorService				actorService;
	@Autowired
	private AdministratorService		administratorService;
	@Autowired
	private HandyWorkerService			handyWorkerService;


	// CONSTRUCTOR 

	public EducationRecordService() {
		super();
	}

	// SIMPLE CRUD METHODS 

	public EducationRecord create() {

		EducationRecord er;
		er = new EducationRecord();

		return er;

	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> result;

		result = this.educationRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EducationRecord findOne(final int educationRecordId) {
		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);

		return result;
	}

	public EducationRecord findOneToEdit(final int educationRecordId) {
		EducationRecord result;

		result = this.educationRecordRepository.findOne(educationRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		if (educationRecord.getEndDate() != null)
			Assert.isTrue(educationRecord.getStartDate().before(educationRecord.getEndDate()), "message.error.startDateEndDate");

		final HandyWorker h;
		Collection<EducationRecord> c;
		EducationRecord result;

		result = this.educationRecordRepository.save(educationRecord);
		h = (HandyWorker) this.actorService.findByPrincipal();

		c = h.getCurriculum().getEducationRecords();
		if (!c.contains(result)) {
			c.add(result);
			h.getCurriculum().setEducationRecords(c);
			this.handyWorkerService.save(h);
		}
		// Comprobamos si es spam
		this.administratorService.checkIsSpam(educationRecord.getAttachmentLink());
		this.administratorService.checkIsSpam(educationRecord.getComment());
		this.administratorService.checkIsSpam(educationRecord.getDiplomaTitle());

		return result;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		Assert.isTrue(educationRecord.getId() != 0);

		HandyWorker h;
		Collection<EducationRecord> c;

		h = (HandyWorker) this.actorService.findByPrincipal();

		c = h.getCurriculum().getEducationRecords();
		c.remove(educationRecord);
		h.getCurriculum().setEducationRecords(c);

		this.educationRecordRepository.delete(educationRecord);

	}

	// Other business methods

	public void checkPrincipal(final EducationRecord er) {
		HandyWorker h;

		h = (HandyWorker) this.actorService.findByPrincipal();

		Assert.isTrue(h.getCurriculum().getEducationRecords().contains(er));
	}

}
