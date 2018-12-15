
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Actor;
import domain.Administrator;
import domain.Box;
import domain.Customer;
import domain.HandyWorker;
import domain.Referee;
import domain.SocialProfile;
import domain.Sponsor;

@Service
@Transactional
public class AdministratorService {

	// Managed repository

	@Autowired
	private AdministratorRepository	administratorRepository;

	// Supporting services

	@Autowired
	private BoxService				boxService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private SocialProfileService	socialProfileService;


	// Constructor
	public AdministratorService() {
		super();
	}

	// Simple CRUD methods

	public Administrator create() {

		Administrator a;
		final Collection<SocialProfile> socialProfiles;
		UserAccount ua;
		Authority auth;

		a = new Administrator();
		socialProfiles = new ArrayList<SocialProfile>();
		ua = this.userAccountService.create();
		auth = new Authority();

		auth.setAuthority("ADMIN");
		ua.addAuthority(auth);

		a.setUserAccount(ua);
		a.setSocialProfiles(socialProfiles);
		a.setSuspicious(false);

		return a;

	}

	public Administrator save(final Administrator administrator) {
		Assert.notNull(administrator);

		Administrator result;

		result = this.administratorRepository.save(administrator);
		this.boxService.createSystemBoxes(result);

		return result;
	}

	public void delete(final Administrator administrator) {
		Assert.notNull(administrator);
		Assert.isTrue(administrator.getId() != 0);
		final Collection<Box> boxes = administrator.getBoxes();

		final Collection<SocialProfile> socialProfiles = administrator.getSocialProfiles();
		this.administratorRepository.delete(administrator);
		this.boxService.delete(boxes);

		this.socialProfileService.delete(socialProfiles);
	}
	public Collection<Administrator> findAll() {
		Collection<Administrator> result;

		result = this.administratorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Administrator findOne(final int administratorId) {
		Administrator result;

		result = this.administratorRepository.findOne(administratorId);

		return result;
	}

	// Other business methods

	public Administrator findByPrincipal() {
		Administrator res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Administrator findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Administrator res;
		res = this.administratorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Administrator findByUserAccountId(final int userAccountId) {
		return this.administratorRepository.findByUserAccountId(userAccountId);
	}

	public boolean checkIsSpam(String text) {

		Collection<String> spamWords;
		Boolean isSpam = false;
		Actor a = this.actorService.findByPrincipal();
		final String type = this.actorService.getType(a.getUserAccount());

		if (type.equals("HANDYWORKER"))
			a = (HandyWorker) a;
		else if (type.equals("REFEREE"))
			a = (Referee) a;
		else if (type.equals("CUSTOMER"))
			a = (Customer) a;
		else if (type.equals("SPONSOR"))
			a = (Sponsor) a;

		if (text == null)
			return isSpam;
		else {
			text = text.toLowerCase();
			spamWords = this.configurationService.getSpamWords();
			for (final String spamword : spamWords)
				if (text.contains(spamword)) {
					isSpam = true;
					a.setSuspicious(true);
				}
		}
		return isSpam;
	}

}
