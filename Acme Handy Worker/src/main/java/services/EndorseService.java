
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorseRepository;
import domain.Endorse;
import domain.Endorsement;

@Service
@Transactional
public class EndorseService {

	// Managed repository 
	@Autowired
	private EndorseRepository		endorseRepository;

	// Supporting services 
	@Autowired
	private EndorsementService		endorsementService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors 
	public EndorseService() {
		super();
	}

	// Simple CRUD methods
	public Endorse findOne(final int EndorseId) {
		Endorse result;

		result = this.endorseRepository.findOne(EndorseId);

		return result;
	}

	// Other business methods

	// Se reciben los Endorsements de un Endorse
	public void computeScore(final Endorse Endorse) {
		Assert.notNull(Endorse);
		Assert.isTrue(Endorse.getId() != 0);

		final Double score;
		Integer p, n;
		//	final Collection<Endorsement> receivedEndorsements = this.endorsementService.getReceivesEndorsements();
		final List<Integer> ls = new ArrayList<Integer>();

		//	ls = this.positiveNegativeWordNumbers(receivedEndorsements);
		p = ls.get(0);
		n = ls.get(1);

	}

	private List<Integer> positiveNegativeWordNumbers(final Collection<Endorsement> receivedEndorsements) {
		Assert.isTrue(receivedEndorsements != null && receivedEndorsements.size() > 0);

		final List<Integer> results = new ArrayList<Integer>();
		String comments = "";
		String[] words = {};
		Integer positive = 0, negative = 0;
		final List<String> positive_ls = new ArrayList<>(this.configurationService.getPositiveWords());
		final List<String> negative_ls = new ArrayList<>(this.configurationService.getNegativeWords());

		for (final Endorsement e : receivedEndorsements) {
			comments = e.getComment();
			words = comments.split(" ");

			for (final String word : words)
				if (positive_ls.contains(word))
					positive++;
				else if (negative_ls.contains(word))
					negative++;

		}

		results.add(positive);
		results.add(negative);

		return results;
	}
	/*
	 * public Endorse findByPrincipal() {
	 * Endorse res;
	 * UserAccount userAccount;
	 * 
	 * userAccount = LoginService.getPrincipal();
	 * Assert.notNull(userAccount);
	 * res = this.findByUserAccount(userAccount);
	 * Assert.notNull(res);
	 * return res;
	 * }
	 * /*
	 * public Endorse findByUserAccount(final UserAccount userAccount) {
	 * Assert.notNull(userAccount);
	 * Endorse res;
	 * res = this.endorseRepository.findByUserAccountId(userAccount.getId());
	 * return res;
	 * }
	 * 
	 * public Endorse findByUserAccountId(final int userAccountId) {
	 * return this.endorseRepository.findByUserAccountId(userAccountId);
	 * }
	 */
}
