
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import security.Authority;
import domain.Actor;
import domain.Application;
import domain.Customer;
import domain.Endorsable;
import domain.Endorsement;
import domain.FixupTask;
import domain.HandyWorker;

@Service
@Transactional
public class EndorsementService {

	// Managed repository 
	@Autowired
	private EndorsementRepository	endorsementRepository;

	// Supporting services 
	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private EndorsableService		endorsableService;

	@Autowired
	private FixupTaskService		fixUpTaskService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private ApplicationService		applicationService;


	// Constructors 

	public EndorsementService() {
		super();

	}

	// Simple CRUD methods 

	public Endorsement create() {
		Endorsement res;
		res = new Endorsement();
		res.setMoment(new Date(System.currentTimeMillis() - 1000));
		final Endorsable sender = this.endorsableService.findPrincipal();
		Assert.notNull(sender);
		res.setSender(sender);
		return res;
	}
	public Endorsement findOne(final int endorsementId) {
		this.serviceUtils.checkId(endorsementId);
		Endorsement res;
		res = this.endorsementRepository.findOne(endorsementId);
		return res;
	}

	public Collection<Endorsement> findAll() {
		Collection<Endorsement> res;
		res = this.endorsementRepository.findAll();
		Assert.notNull(res);
		Assert.notEmpty(res);
		return res;
	}

	public Endorsement save(final Endorsement endorsement) {
		Assert.notNull(endorsement);

		this.serviceUtils.checkIdSave(endorsement);

		Endorsement endorsementBD;
		endorsementBD = this.endorsementRepository.findOne(endorsement.getId());

		if (endorsement.getId() == 0) {
			final Boolean res = (this.serviceUtils.checkAuthorityBoolean(Authority.CUSTOMER) || this.serviceUtils.checkAuthorityBoolean(Authority.HANDYWORKER));
			Assert.isTrue(res);
		} else {
			endorsement.setComments(endorsementBD.getComments());
			endorsement.setMoment(endorsementBD.getMoment());
			endorsement.setSender(endorsementBD.getSender());
			final Boolean res = (this.serviceUtils.checkAuthorityBoolean(Authority.CUSTOMER) || this.serviceUtils.checkAuthorityBoolean(Authority.HANDYWORKER));
			Assert.isTrue(res);
		}
		Endorsement res;
		res = this.endorsementRepository.save(endorsement);
		return res;
	}
	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);
		this.endorsementRepository.delete(endorsement);
	}

	public Collection<Endorsement> findBySender(final Endorsable sender) {
		Assert.notNull(sender);
		Assert.isTrue(sender.getId() > 0);
		Assert.notNull(this.endorsableService.findOne(sender.getId()));
		return this.endorsementRepository.getBySenderId(sender.getId());
	}

	public Collection<Endorsement> findByReceiver(final Endorsable receiver) {
		Assert.notNull(receiver);
		Assert.isTrue(receiver.getId() > 0);
		Assert.notNull(this.endorsableService.findOne(receiver.getId()));
		return this.endorsementRepository.getByReceiverId(receiver.getId());
	}

	public Collection<Endorsement> findAllByActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.endorsableService.findOne(a.getId()));
		final Collection<Endorsement> res = new ArrayList<Endorsement>();
		res.addAll(this.findByReceiver((Endorsable) a));
		res.addAll(this.findBySender((Endorsable) a));
		return res;
	}

	public Endorsement create(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.endorsableService.findOne(a.getId()));
		final Endorsement res = this.create();
		Assert.isTrue(a instanceof HandyWorker || a instanceof Customer);
		res.setSender((Endorsable) a);
		return res;
	}

	public Collection<HandyWorker> GiveHandyWorkersByCustomer(final Customer c) {
		final Collection<FixupTask> fixUpTasks = this.fixUpTaskService.findByCustomer(c);
		final Collection<HandyWorker> handyWorkers = new ArrayList<>();
		for (final FixupTask f : fixUpTasks)
			for (final Application app : f.getApplications())
				if (app.getStatus().equals("ACCEPTED"))
					handyWorkers.add(app.getHandyWorker());

		return handyWorkers;

	}

	public Collection<Customer> GiveCustomersByHandyWorker(final HandyWorker h) {
		final Collection<Customer> customers = new ArrayList<>();
		final Collection<Application> applications = this.applicationService.findApplicationsByHandyWorker(h);
		for (final Application a : applications)
			if (a.getStatus().equals("ACCEPTED") && a.getHandyWorker().equals(h))
				customers.add(a.getFixupTask().getCustomer());

		return customers;

	}
}
