
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// MANAGED REPOSITORY 

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	// SUPPORTING SERVICES 

	@Autowired
	private ActorService					actorService;
	@Autowired
	private HandyWorkerService				handyWorkerService;
	@Autowired
	private AdministratorService			administratorService;


	// CONSTRUCTOR 

	public ProfessionalRecordService() {
		super();
	}

	// SIMPLE CRUD METHODS 

	public ProfessionalRecord create() {

		ProfessionalRecord pr;

		pr = new ProfessionalRecord();

		return pr;

	}

	public Collection<ProfessionalRecord> findAll() {
		Collection<ProfessionalRecord> result;

		result = this.professionalRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public ProfessionalRecord findOne(final int EducationRecordId) {
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(EducationRecordId);

		return result;
	}

	public ProfessionalRecord findOneToEdit(final int EducationRecordId) {
		ProfessionalRecord result;

		result = this.professionalRecordRepository.findOne(EducationRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public ProfessionalRecord save(final ProfessionalRecord pr) {
		Assert.notNull(pr);
		if (pr.getEndDate() != null)
			Assert.isTrue(pr.getStartDate().before(pr.getEndDate()), "message.error.startDateEndDate");

		final HandyWorker h;
		Collection<ProfessionalRecord> c;
		ProfessionalRecord result;

		result = this.professionalRecordRepository.save(pr);
		h = (HandyWorker) this.actorService.findByPrincipal();

		if (pr.getId() == 0) {
			c = h.getCurriculum().getProfessionalRecords();
			c.add(pr);
			h.getCurriculum().setProfessionalRecords(c);
			this.handyWorkerService.save(h);
		}

		// Comprobamos si es spam
		this.administratorService.checkIsSpam(pr.getAttachmentLink());
		this.administratorService.checkIsSpam(pr.getComment());
		this.administratorService.checkIsSpam(pr.getCompanyName());
		this.administratorService.checkIsSpam(pr.getRole());

		return result;
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		Assert.notNull(professionalRecord);
		Assert.isTrue(professionalRecord.getId() != 0);

		HandyWorker h;
		Collection<ProfessionalRecord> c;

		h = (HandyWorker) this.actorService.findByPrincipal();

		c = h.getCurriculum().getProfessionalRecords();
		c.remove(professionalRecord);
		h.getCurriculum().setProfessionalRecords(c);

		this.professionalRecordRepository.delete(professionalRecord);
	}

	// Other business methods

	public void checkPrincipal(final ProfessionalRecord mr) {
		HandyWorker h;

		h = (HandyWorker) this.actorService.findByPrincipal();

		Assert.isTrue(h.getCurriculum().getProfessionalRecords().contains(mr));
	}

}
