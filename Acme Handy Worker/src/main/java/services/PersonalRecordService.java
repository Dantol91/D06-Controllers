
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.Curriculum;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	//Managed Repository

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	// Supporting Service

	@Autowired
	private CurriculumService			curriculumService;


	public PersonalRecordService() {
		super();
	}
	// Simple CRUD methods

	public PersonalRecord create() {
		PersonalRecord pr;
		Curriculum c;
		c = this.curriculumService.create();
		pr = new PersonalRecord();
		pr.setCurriculum(c);
		return pr;
	}

	public Collection<PersonalRecord> findAll() {
		Collection<PersonalRecord> pr;
		pr = this.personalRecordRepository.findAll();
		return pr;
	}

	public PersonalRecord findOne(final int personalRecordId) {
		PersonalRecord res;
		res = this.personalRecordRepository.findOne(personalRecordId);
		return res;

	}

	public PersonalRecord save(final PersonalRecord p) {
		Assert.notNull(p);
		PersonalRecord res;
		System.out.println("a");
		System.out.println(p.getFullName());
		System.out.println(p.getEmail());
		System.out.println(p.getLinkedinProfile());
		System.out.println(p.getPhone());
		System.out.println(p.getPhoto());
		System.out.println(p.getCurriculum());
		res = this.personalRecordRepository.save(p);
		System.out.println(p.getFullName());
		System.out.println("b");
		return res;
	}

	public void delete(final PersonalRecord p) {
		Assert.notNull(p);

		this.personalRecordRepository.delete(p);
	}

	public void delete(final Collection<PersonalRecord> pr) {

		for (final PersonalRecord i : pr) {
			Assert.notNull(i);
			this.personalRecordRepository.delete(i);
		}
	}

	public PersonalRecord findOne(final Curriculum dependency) {
		Assert.notNull(dependency);
		Assert.isTrue(dependency.getId() > 0);
		Assert.notNull(this.curriculumService.findOne(dependency.getId()));
		return dependency.getPersonalRecord();
	}

	public PersonalRecord create(final Curriculum dependency) {
		Assert.notNull(dependency);
		Assert.isTrue(dependency.getId() > 0);
		Assert.notNull(this.curriculumService.findOne(dependency.getId()));
		final PersonalRecord res = this.create();
		res.setCurriculum(dependency);
		return res;
	}
}
