
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import domain.HandyWorker;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	//Managed repository
	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	//Supporting services
	@Autowired
	private ActorService				actorService;
	@Autowired
	private ConfigurationService		configurationService;
	@Autowired
	private AdministratorService		administratorService;


	//Constructor
	public PersonalRecordService() {
		super();
	}

	//Simple CRUD methods

	public PersonalRecord create() {
		PersonalRecord personalRecord;
		personalRecord = new PersonalRecord();

		return personalRecord;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.notNull(personalRecord);

		PersonalRecord pr;

		pr = this.personalRecordRepository.save(personalRecord);

		// Comprobamos si es spam
		this.administratorService.checkIsSpam(personalRecord.getFullName());
		this.administratorService.checkIsSpam(personalRecord.getEmail());
		this.administratorService.checkIsSpam(personalRecord.getLinkedInProfile());
		this.administratorService.checkIsSpam(personalRecord.getPhoto());

		final String tlf = this.configurationService.checkPhoneNumber(personalRecord.getPhone());
		personalRecord.setPhone(tlf);

		return pr;

	}

	public Collection<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(final int personalRecordId) {
		PersonalRecord result;

		result = this.personalRecordRepository.findOne(personalRecordId);

		return result;
	}

	public PersonalRecord findOneToEdit(final int personalRecordId) {
		PersonalRecord result;

		result = this.personalRecordRepository.findOne(personalRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public void delete(final PersonalRecord pr) {
		this.personalRecordRepository.delete(pr);
	}

	//Other business methods

	public void checkPrincipal(final PersonalRecord mr) {
		HandyWorker h;

		h = (HandyWorker) this.actorService.findByPrincipal();

		Assert.isTrue(h.getCurriculum().getPersonalRecord().equals(mr));
	}

}
