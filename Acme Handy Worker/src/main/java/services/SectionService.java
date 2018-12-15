
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import domain.Section;

@Service
@Transactional
public class SectionService {

	// Managed repository

	@Autowired
	private SectionRepository	sectionRepository;


	//Supporting services

	// Constructor

	public SectionService() {
		super();
	}

	// Simple CRUD methods 

	public Section create() {
		final Section s;

		s = new Section();

		return s;
	}

	public Section save(final Section section) {
		Assert.notNull(section);

		return this.sectionRepository.save(section);
	}

	public Section findOne(final int sectionId) {
		Assert.isTrue(sectionId != 0);

		Section result;

		result = this.sectionRepository.findOne(sectionId);

		return result;
	}

	public Collection<Section> findAll() {
		Collection<Section> result;

		result = this.sectionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void delete(final Section section) {
		Assert.notNull(section);

		this.sectionRepository.delete(section);
	}

	// Other Business Methods 

}
