
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationRecordRepository;
import domain.Curriculum;
import domain.EducationRecord;

@Service
@Transactional
public class EducationRecordService {

	// Managed Repository

	@Autowired
	private EducationRecordRepository	educationRecordRepository;

	//Supporting Services

	@Autowired
	private CurriculumService			curriculumService;


	// Constructors 

	public EducationRecordService() {
		super();
	}

	//Simple CRUD methods
	public EducationRecord create() {
		EducationRecord res;
		res = new EducationRecord();
		return res;
	}

	public Collection<EducationRecord> findAll() {
		Collection<EducationRecord> res;
		res = this.educationRecordRepository.findAll();
		return res;
	}
	public EducationRecord findOne(final int educationRecordId) {
		EducationRecord result;
		result = this.educationRecordRepository.findOne(educationRecordId);
		return result;
	}

	public EducationRecord save(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		EducationRecord res;
		res = this.educationRecordRepository.save(educationRecord);
		return res;
	}

	public void delete(final EducationRecord educationRecord) {
		Assert.notNull(educationRecord);
		this.educationRecordRepository.delete(educationRecord);
	}

	public void delete(final Collection<EducationRecord> educationsRecords) {

		for (final EducationRecord i : educationsRecords) {
			Assert.notNull(i);
			this.educationRecordRepository.delete(i);
		}
	}

	public Collection<EducationRecord> findAll(final Curriculum dependency) {
		Assert.notNull(dependency);
		Assert.isTrue(dependency.getId() > 0);
		Assert.notNull(this.curriculumService.findOne(dependency.getId()));
		return dependency.getEducationRecords();
	}

	public EducationRecord create(final Curriculum dependency) {
		Assert.notNull(dependency);
		Assert.isTrue(dependency.getId() > 0);
		Assert.notNull(this.curriculumService.findOne(dependency.getId()));
		final EducationRecord res = this.create();
		res.setCurriculum(dependency);
		return res;
	}

}
