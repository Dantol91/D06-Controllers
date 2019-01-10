
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	//Managed Repository

	@Autowired
	private WarrantyRepository	warrantyRepository;

	// Supporting Service

	@Autowired
	private FixupTaskService	fixupTaskService;


	public Warranty findOne(final Integer id) {
		return this.warrantyRepository.findOne(id);
	}

	public List<Warranty> findAll(final Collection<Integer> ids) {
		return this.warrantyRepository.findAll(ids);
	}

	public List<Warranty> findAll() {
		return this.warrantyRepository.findAll();
	}

	public Warranty create() {
		final Warranty res = new Warranty();
		res.setDraft(true);
		return res;
	}

	public Warranty save(final Warranty object) {
		final Warranty warranty = object;
		Assert.isTrue(warranty.getDraft());
		if (warranty.getDraft() == true && object.getId() != 0)
			Assert.isTrue(this.fixupTaskService.findByWarranty(object).isEmpty());
		return this.warrantyRepository.save(object);
	}

	public Warranty saveDraft(final Warranty object) {
		final Warranty warranty = object;
		if (warranty.getDraft() == true && object.getId() != 0)
			Assert.isTrue(this.fixupTaskService.findByWarranty(object).isEmpty());
		return this.warrantyRepository.save(object);
	}

	public void delete(final Warranty object) {
		final Warranty warranty = object;
		Assert.isTrue(warranty.getDraft());
		if (this.fixupTaskService.findByWarranty(object).isEmpty())
			this.warrantyRepository.delete(object);
		else
			throw new IllegalArgumentException("No puedes borrar una garantía que tenga tareas");
	}

	public Collection<Warranty> findWarrantyNotDraft() {
		return this.warrantyRepository.findWarrantyNotDraft();
	}

}
