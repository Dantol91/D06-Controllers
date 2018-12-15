
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	// MANAGED REPOSITORY 

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	// SUPPORTING SERVICES

	@Autowired
	private ActorService				actorService;
	@Autowired
	private AdministratorService		administratorService;
	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private ConfigurationService		configurationService;


	// CONSTRUCTOR 

	public EndorserRecordService() {
		super();
	}

	// SIMPLE CRUD METHODS 

	public EndorserRecord create() {

		EndorserRecord er;
		er = new EndorserRecord();

		return er;

	}

	public Collection<EndorserRecord> findAll() {
		Collection<EndorserRecord> result;

		result = this.endorserRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public EndorserRecord findOne(final int endorserRecordId) {
		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(endorserRecordId);

		return result;
	}

	public EndorserRecord findOneToEdit(final int endorserRecordId) {
		EndorserRecord result;

		result = this.endorserRecordRepository.findOne(endorserRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);

		final HandyWorker h;
		Collection<EndorserRecord> c;
		EndorserRecord result;

		result = this.endorserRecordRepository.save(endorserRecord);
		h = (HandyWorker) this.actorService.findByPrincipal();

		if (endorserRecord.getId() == 0) {
			c = h.getCurriculum().getEndorserRecords();
			c.add(result);
			h.getCurriculum().setEndorserRecords(c);
			this.handyWorkerService.save(h);
		}

		// Comprobamos si es spam
		this.administratorService.checkIsSpam(endorserRecord.getComment());
		this.administratorService.checkIsSpam(endorserRecord.getEmail());
		this.administratorService.checkIsSpam(endorserRecord.getFullName());
		this.administratorService.checkIsSpam(endorserRecord.getLinkedInProfile());

		final String tlf = this.configurationService.checkPhoneNumber(endorserRecord.getPhone());
		endorserRecord.setPhone(tlf);

		return result;
	}

	public void delete(final EndorserRecord endorserRecord) {
		Assert.notNull(endorserRecord);
		Assert.isTrue(endorserRecord.getId() != 0);

		HandyWorker h;
		Collection<EndorserRecord> c;

		h = (HandyWorker) this.actorService.findByPrincipal();

		c = h.getCurriculum().getEndorserRecords();
		c.remove(endorserRecord);
		h.getCurriculum().setEndorserRecords(c);

		this.endorserRecordRepository.delete(endorserRecord);
	}

	// Other business methods

	public void checkPrincipal(final EndorserRecord er) {
		HandyWorker h;

		h = (HandyWorker) this.actorService.findByPrincipal();

		Assert.isTrue(h.getCurriculum().getEndorserRecords().contains(er));
	}

}
