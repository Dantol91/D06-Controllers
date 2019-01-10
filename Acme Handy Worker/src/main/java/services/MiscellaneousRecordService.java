
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.Curriculum;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	//Managed Repository

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	@Autowired
	private CurriculumService				curriculumService;


	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods
	public MiscellaneousRecord create() {
		final MiscellaneousRecord mr = new MiscellaneousRecord();
		return mr;
	}

	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public Collection<MiscellaneousRecord> findAll(final int miscellaneousRecordId) {
		final Collection<MiscellaneousRecord> mr;

		mr = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(mr);

		return mr;
	}

	public Collection<MiscellaneousRecord> findAll(final Curriculum dependency) {
		Assert.notNull(dependency);
		Assert.isTrue(dependency.getId() > 0);
		Assert.notNull(this.curriculumService.findOne(dependency.getId()));
		return dependency.getMiscellaneousRecords();
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		Assert.notNull(this);
		return this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);
	}

	public MiscellaneousRecord save(final MiscellaneousRecord mr) {
		Assert.notNull(mr);
		return this.miscellaneousRecordRepository.save(mr);
	}

	public void delete(final MiscellaneousRecord mr) {
		Assert.notNull(mr);
		Assert.isTrue(mr.getId() != 0);
		this.miscellaneousRecordRepository.delete(mr);
	}

}
