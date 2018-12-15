
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EndorsementRepository;
import domain.Endorsement;

@Service
@Transactional
public class EndorsementService {

	// Managed repository

	@Autowired
	private EndorsementRepository	endorsementRepository;

	//Supporting services

	@Autowired
	private EndorseService			endorseService;


	// Constructor

	public EndorsementService() {
		super();
	}

	// Simple CRUD methods 

	public Endorsement create() {
		final Endorsement e;

		e = new Endorsement();

		return e;
	}

	public Endorsement save(final Endorsement endorsement) {
		Assert.notNull(endorsement);

		return this.endorsementRepository.save(endorsement);
	}

	public Endorsement findOne(final int endorsementId) {
		Assert.isTrue(endorsementId != 0);

		Endorsement result;

		result = this.endorsementRepository.findOne(endorsementId);

		return result;
	}

	public Collection<Endorsement> findAll() {
		Collection<Endorsement> result;

		result = this.endorsementRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Endorsement endorsement) {
		Assert.notNull(endorsement);

		this.endorsementRepository.delete(endorsement);
	}

	// Other Business Methods 

	/*
	 * public Collection<Endorsement> getSentsEndorsements() {
	 * final Collection<Endorsement> results;
	 * final Endorse result;
	 * 
	 * // principal = this.endorseService.findByPrincipal();
	 * 
	 * // results = this.endorsementRepository.getSentsEndorsements(principal.getId());
	 * 
	 * return result;
	 * }
	 * 
	 * public Collection<Endorsement> getReceivesEndorsements() {
	 * Collection<Endorsement> results;
	 * Endorse principal;
	 * 
	 * principal = this.endorseService.findByPrincipal();
	 * 
	 * results = this.endorsementRepository.getReceivesEndorsements(principal.getId());
	 * 
	 * return results;
	 * }
	 */

}
