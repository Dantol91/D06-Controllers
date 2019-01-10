
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.Authority;
import domain.Curriculum;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	// Repository
	
	@Autowired
	private ProfessionalRecordRepository	repository;

	// Services
	
	@Autowired
	private CurriculumService				curriculumService;
	@Autowired
	private ServiceUtils					serviceUtils;

	// CRUD methods

	public ProfessionalRecord findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<ProfessionalRecord> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<ProfessionalRecord> findAll() {
		return this.repository.findAll();
	}
	
	public Collection<ProfessionalRecord> findAll(final Curriculum dependency) {
		this.serviceUtils.checkId(dependency);
		Assert.notNull(this.curriculumService.findOne(dependency.getId()));
		return this.repository.findByCurriculumId(dependency.getId());
	}

	public ProfessionalRecord create(final Curriculum dependency) {
		final ProfessionalRecord res = new ProfessionalRecord();
		res.setCurriculum(dependency);
		return res;
	}

	public ProfessionalRecord save(final ProfessionalRecord object) {
		final ProfessionalRecord professionalRecord = (ProfessionalRecord) this.serviceUtils.checkObjectSave(object);
		if (professionalRecord.getId() > 0) {
			professionalRecord.setAttachment(object.getAttachment());
			professionalRecord.setComments(object.getComments());
			professionalRecord.setCompany(object.getCompany());
			professionalRecord.setEnd(object.getEnd());
			professionalRecord.setRole(object.getRole());
			professionalRecord.setStart(object.getStart());
		}
		this.serviceUtils.checkActor(professionalRecord.getCurriculum().getHandyWorker());
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		final ProfessionalRecord res = this.repository.save(professionalRecord);
		return res;
	}

	public void delete(final ProfessionalRecord object) {
		final ProfessionalRecord professionalRecord = (ProfessionalRecord) this.serviceUtils.checkObject(object);
		this.serviceUtils.checkActor(professionalRecord.getCurriculum().getHandyWorker());
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		this.repository.delete(professionalRecord);
	}

	// Other methods
	
	public void flush() {
		this.repository.flush();
	}

}
