
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.SettingsRepository;
import security.Authority;
import domain.Endorsable;
import domain.Endorsement;
import domain.Settings;

@Service
@Transactional
public class SettingsService {

	// Managed Repository

	@Autowired
	private SettingsRepository	repository;

	// Services

	@Autowired
	private ServiceUtils		serviceUtils;

	@Autowired
	private EndorsableService	endorsableService;

	@Autowired
	private EndorsementService	endorsementService;


	// CRUD methods

	public Settings findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Settings> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Settings> findAll() {
		return this.repository.findAll();
	}

	public Settings save(final Settings object) {
		this.serviceUtils.checkObject(object);
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		final Settings res = this.repository.save(object);
		return res;
	}

	// Other methods

	public Settings findSettings() {
		return this.repository.findSettings();
	}

	public void flush() {
		this.repository.flush();
	}

	public Collection<String> addPositiveWords(final String s) {
		List<String> res = new ArrayList<>();
		final Settings settings = this.findSettings();
		res = settings.getPositiveWords();
		if (res.contains(s))
			throw new IllegalArgumentException("That word is already included");
		res.add(s);
		settings.setPositiveWords(res);
		this.save(settings);
		return res;

	}

	public Collection<String> deletePositiveWords(final String s) {
		List<String> res = new ArrayList<>();
		final Settings settings = this.findSettings();
		res = settings.getPositiveWords();
		if (!(res.contains(s)))
			throw new IllegalArgumentException("That word is not in the positive words list");
		res.remove(s);
		settings.setPositiveWords(res);
		this.save(settings);
		return res;

	}

	public Collection<String> addNegativeWords(final String s) {
		List<String> res = new ArrayList<>();
		final Settings settings = this.findSettings();
		res = settings.getNegativeWords();
		if (res.contains(s))
			throw new IllegalArgumentException("That word is already included");
		res.add(s);
		settings.setNegativeWords(res);
		this.save(settings);
		return res;

	}

	public Collection<String> deleteNegativeWords(final String s) {
		List<String> res = new ArrayList<>();
		final Settings settings = this.findSettings();
		res = settings.getNegativeWords();
		if (!(res.contains(s)))
			throw new IllegalArgumentException("That word is not in the negative words list");
		res.remove(s);
		settings.setNegativeWords(res);
		this.save(settings);
		return res;

	}

	public Collection<String> updatePositiveWord(final String oldWord, final String newWord) {
		this.deletePositiveWords(oldWord);
		this.addPositiveWords(newWord);
		return this.findSettings().getPositiveWords();
	}

	public Collection<String> updateNegativeWord(final String oldWord, final String newWord) {
		this.deleteNegativeWords(oldWord);
		this.addNegativeWords(newWord);
		return this.findSettings().getNegativeWords();
	}

	public void generateAllScore() {
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		final Collection<Endorsable> res = this.endorsableService.findAll();
		for (final Endorsable e : res) {
			final Double d = this.generateScore(e);
			e.setScore(d);
			this.endorsableService.save(e);
		}

	}

	public Double generateScore(final Endorsable a) {
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		Double res = 0.0;
		Double cont = 0.0;
		final Collection<Endorsement> endorsements = new ArrayList<Endorsement>();
		final Collection<String> buenas = this.findSettings().getPositiveWords();
		final Collection<String> malas = this.findSettings().getNegativeWords();
		endorsements.addAll(this.endorsementService.findByReceiver(a));
		endorsements.addAll(this.endorsementService.findBySender(a));
		for (final Endorsement e : endorsements) {
			final String[] palabras = e.getComments().split(" ");
			for (final String word : palabras) {

				if (buenas.contains(word))
					res++;
				if (malas.contains(word))
					res--;
				cont++;
			}

		}

		return res / cont;
	}
}
