
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	// Managed repository
	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	// Supporting services

	@Autowired
	private ActorService					actorService;

	@Autowired
	private AdministratorService			administratorService;

	@Autowired
	private HandyWorkerService				handyWorkerService;


	// Constructor
	public MiscellaneousRecordService() {
		super();
	}

	// Simple CRUD methods

	public MiscellaneousRecord create() {

		MiscellaneousRecord er;
		er = new MiscellaneousRecord();

		return er;

	}

	public Collection<MiscellaneousRecord> findAll() {
		Collection<MiscellaneousRecord> result;

		result = this.miscellaneousRecordRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public MiscellaneousRecord findOne(final int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		return result;
	}

	public MiscellaneousRecord findOneToEdit(final int miscellaneousRecordId) {
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.findOne(miscellaneousRecordId);

		this.checkPrincipal(result);

		return result;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);

		final HandyWorker h;
		Collection<MiscellaneousRecord> c;
		MiscellaneousRecord result;

		result = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		h = (HandyWorker) this.actorService.findByPrincipal();

		if (miscellaneousRecord.getId() == 0) {
			c = h.getCurriculum().getMiscellaneousRecords();
			c.add(result);
			h.getCurriculum().setMiscellaneousRecords(c);
			this.handyWorkerService.save(h);
		}

		// Comprobamos si es spam
		this.administratorService.checkIsSpam(miscellaneousRecord.getAttachmentLink());
		this.administratorService.checkIsSpam(miscellaneousRecord.getComment());
		this.administratorService.checkIsSpam(miscellaneousRecord.getTitle());

		return result;
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		Assert.notNull(miscellaneousRecord);
		Assert.isTrue(miscellaneousRecord.getId() != 0);

		HandyWorker h;
		Collection<MiscellaneousRecord> c;

		h = (HandyWorker) this.actorService.findByPrincipal();

		c = h.getCurriculum().getMiscellaneousRecords();
		c.remove(miscellaneousRecord);
		h.getCurriculum().setMiscellaneousRecords(c);

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

	// Other business methods

	public void checkPrincipal(final MiscellaneousRecord mr) {
		HandyWorker h;

		h = (HandyWorker) this.actorService.findByPrincipal();

		Assert.isTrue(h.getCurriculum().getMiscellaneousRecords().contains(mr));
	}
}
