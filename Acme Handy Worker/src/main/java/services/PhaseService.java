
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	// Managed repository 

	@Autowired
	private PhaseRepository	phaseRepository;


	// Supporting services 

	// Constructors 

	public PhaseService() {
		super();
	}

	// Simple CRUD methods 

	public Phase create() {
		final Phase p;

		p = new Phase();

		return p;
	}

	public Phase save(final Phase phase) {
		Assert.notNull(phase);
		return this.phaseRepository.save(phase);
	}

	public Phase findOne(final int phaseId) {
		Assert.isTrue(phaseId != 0);

		Phase result;

		result = this.phaseRepository.findOne(phaseId);

		return result;
	}

	public Collection<Phase> findAll() {
		Collection<Phase> result;

		result = this.phaseRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Phase phase) {
		Assert.notNull(phase);

		this.phaseRepository.delete(phase);
	}

	// Other Business Methods 

}
