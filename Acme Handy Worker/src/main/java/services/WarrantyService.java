
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	// Managed repository 

	@Autowired
	private WarrantyRepository	warrantyRepository;


	// Supporting services 

	// Constructors 

	public WarrantyService() {
		super();
	}

	// Simple CRUD methods 

	public Warranty create() {
		final Warranty w;

		w = new Warranty();

		return w;
	}

	public Warranty save(final Warranty warranty) {
		Assert.notNull(warranty);
		return this.warrantyRepository.save(warranty);
	}

	public Warranty findOne(final int warrantyId) {
		Assert.isTrue(warrantyId != 0);

		Warranty result;

		result = this.warrantyRepository.findOne(warrantyId);

		return result;
	}

	public Collection<Warranty> findAll() {
		Collection<Warranty> result;

		result = this.warrantyRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Warranty warranty) {
		Assert.notNull(warranty);

		this.warrantyRepository.delete(warranty);
	}

	// Other Business Methods 

}
