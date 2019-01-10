
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SectionRepository;
import security.Authority;
import domain.Section;
import domain.Tutorial;
import domain.Url;

@Service
@Transactional
public class SectionService {

	// Repository

	@Autowired
	private SectionRepository	repository;

	// Services

	@Autowired
	private ServiceUtils		serviceUtils;
	@Autowired
	private TutorialService		tutorialService;


	// CRUD methods

	public Section findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Section> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Section> findAll() {
		return this.repository.findAll();
	}

	public Collection<Section> findByTutorial(final Tutorial dependency) {
		this.serviceUtils.checkId(dependency.getId());
		Assert.notNull(this.tutorialService.findOne(dependency.getId()));
		return this.repository.findByTutorial(dependency.getId());
	}

	public Section create(final Tutorial dependency) {
		final Section res = new Section();
		res.setPictures(new ArrayList<Url>());
		res.setNumberOrder(dependency.getSections().size() + 1);
		res.setTutorial(dependency);
		return res;
	}

	public Section save(final Section object) {
		final Section section = (Section) this.serviceUtils.checkObjectSave(object);
		Section movedSection = null;
		Collection<Section> sectionsMoving = null;
		if (section.getId() == 0) {
			this.serviceUtils.checkId(section.getTutorial());
			Assert.notNull(section.getTutorial());
			sectionsMoving = this.findByNumberOrder(object.getNumberOrder(), section.getTutorial());
			for (final Section s : sectionsMoving)
				if (s.getId() > 0) {
					movedSection = s;
					movedSection.setNumberOrder(section.getTutorial().getSections().size() + 1);
				}
		} else {
			section.setPictures(object.getPictures());
			section.setText(object.getText());
			section.setTitle(object.getTitle());
			final Integer sectionNumberOrder = section.getNumberOrder();
			sectionsMoving = this.findByNumberOrder(object.getNumberOrder(), section.getTutorial());
			for (final Section s : sectionsMoving)
				if (!s.equals(object)) {
					section.setNumberOrder(s.getNumberOrder());
					movedSection = s;
					movedSection.setNumberOrder(sectionNumberOrder);
				}
		}
		if (object.getId() == 0)
			Assert.isTrue(object.getNumberOrder() <= section.getTutorial().getSections().size() + 1);
		else
			Assert.isTrue(object.getNumberOrder() <= section.getTutorial().getSections().size());
		Assert.isTrue(object.getNumberOrder() > 0);
		if (movedSection != null)
			this.repository.save(movedSection);
		this.serviceUtils.checkActor(section.getTutorial().getHandyWorker());
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		final Section res = this.repository.save(section);
		return res;
	}
	public void delete(final Section object) {
		final Section section = (Section) this.serviceUtils.checkObject(object);
		this.serviceUtils.checkActor(section.getTutorial().getHandyWorker());
		this.serviceUtils.checkAuthority(Authority.HANDYWORKER);
		final Collection<Section> nextSections = this.findNextSections(section.getNumberOrder(), section.getTutorial());
		for (final Section s : nextSections)
			if (!s.equals(object)) {
				s.setNumberOrder(s.getNumberOrder() - 1);
				this.repository.save(s);
			}
		this.repository.delete(section);
	}
	// Other methods

	public void move(final Section section1, final Section section2) {
		final Section s1 = (Section) this.serviceUtils.checkObject(section1);
		final Section s2 = (Section) this.serviceUtils.checkObject(section2);
		final Integer pos1 = s1.getNumberOrder();
		final Integer pos2 = s2.getNumberOrder();
		s1.setNumberOrder(pos2);
		s2.setNumberOrder(pos1);
		this.save(s1);
		this.save(s2);
	}

	public Collection<Section> findByNumberOrder(final Integer numberOrder, final Tutorial tutorial) {
		Assert.notNull(numberOrder);
		Assert.isTrue(numberOrder > 0);
		this.serviceUtils.checkObject(tutorial);
		return this.repository.findByNumberOrder(numberOrder, tutorial.getId());
	}

	public Collection<Section> findNextSections(final Integer numberOrder, final Tutorial tutorial) {
		Assert.notNull(numberOrder);
		Assert.isTrue(numberOrder > 0);
		this.serviceUtils.checkObject(tutorial);
		return this.repository.findNextSections(numberOrder, tutorial.getId());
	}

	public void flush() {
		this.repository.flush();
	}

}
