
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.TutorialRepository;
import domain.HandyWorker;
import domain.Section;
import domain.Sponsorship;
import domain.Tutorial;

@Service
@Transactional
public class TutorialService {

	// Managed repository ---------------------------------------------
	@Autowired
	private TutorialRepository	tutorialRepository;

	// Supporting services -------------------------------------------

	private HandyWorkerService	handyWorkerService;


	//Constructor ----------------------------------------------------
	public TutorialService() {
		super();
	}
	//Simple CRUD methods -------------------------------------------

	public Tutorial create() {
		final Tutorial result;
		final Date date;
		final HandyWorker handyWorker;
		final Collection<Sponsorship> sponsorships;
		final Collection<Section> sections;

		handyWorker = new HandyWorker();

		result = new Tutorial();
		date = new Date(System.currentTimeMillis() - 1);

		sponsorships = new ArrayList<Sponsorship>();
		sections = new ArrayList<Section>();

		result.setMoment(date);
		result.setSections(sections);
		result.setSponsorships(sponsorships);

		return result;
	}
	public Tutorial save(final Tutorial tutorial) {
		Assert.notNull(tutorial);

		Date dateNow;
		Tutorial result;

		dateNow = new Date();
		Assert.isTrue(tutorial.getMoment().before(dateNow));

		result = this.tutorialRepository.save(tutorial);

		return result;
	}
	public Tutorial findOne(final int idTutorial) {
		Tutorial result;

		Assert.isTrue(idTutorial != 0);

		result = this.tutorialRepository.findOne(idTutorial);

		return result;
	}
	public Collection<Tutorial> findAll() {
		Collection<Tutorial> result;

		result = this.tutorialRepository.findAll();

		Assert.notNull(result);

		return result;
	}

	public void delete(final Tutorial tutorial) {
		Assert.isTrue(tutorial.getId() != 0);

		Assert.notNull(tutorial);

		Assert.isTrue(this.tutorialRepository.exists(tutorial.getId()));

		this.tutorialRepository.delete(tutorial);
	}

	//Other business methods

	public Tutorial getTutorialBySectionId(final Section section) {
		Tutorial result;

		result = this.tutorialRepository.getTutorialBySectionId(section.getId());

		Assert.isTrue((result.getSections().contains(section)));

		return result;
	}
	public void deleteSection(final Tutorial tutorial, final Section section) {
		Assert.isTrue((tutorial.getSections().contains(section)));

		tutorial.getSections().remove(section);

		Assert.isTrue(!(tutorial.getSections().contains(section)));
	}
	public void getSectionTutorial(final Tutorial tutorial, final Section section) {
		Collection<Section> sections;

		sections = tutorial.getSections();
		sections.add(section);

		tutorial.setSections(sections);
		Assert.isTrue(tutorial.getSections().contains(section));
	}
	public void deleteSectionTutorial(final Tutorial tutorial, final Section section) {
		Collection<Section> sections;

		sections = tutorial.getSections();
		sections.remove(section);

		tutorial.setSections(sections);
		Assert.isTrue(!(tutorial.getSections().contains(section)));
	}
}
