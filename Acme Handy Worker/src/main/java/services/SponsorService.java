
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Box;
import domain.SocialProfile;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	// Managed repository

	@Autowired
	private SponsorRepository		sponsorRepository;

	// Supporting services

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private BoxService				boxService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private SocialProfileService	socialProfileService;


	// Constructor
	public SponsorService() {
		super();
	}

	// Simple CRUD methods

	public Sponsor create() {
		final Collection<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		Sponsor s;
		UserAccount ua;
		Authority auth;

		s = new Sponsor();
		ua = this.userAccountService.create();
		auth = new Authority();

		auth.setAuthority("SPONSOR");
		ua.addAuthority(auth);
		s.setUserAccount(ua);
		s.setSocialProfiles(socialProfiles);
		s.setSuspicious(false);

		return s;
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int sponsorId) {
		Sponsor result;
		result = this.sponsorRepository.findOne(sponsorId);
		return result;
	}

	public Sponsor save(Sponsor sponsor) {
		Assert.notNull(sponsor);
		if (sponsor.getId() == 0)
			Assert.isTrue(this.userAccountService.findByUsername(sponsor.getUserAccount().getUsername()) == null, "message.error.duplicatedUser");
		Boolean create = false;

		// Comprobamos si se está creando el user
		if (sponsor.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			sponsor.getUserAccount().setPassword(encoder.encodePassword(sponsor.getUserAccount().getPassword(), null));
		}

		if (sponsor.getPhone() != null) {
			final String tlf = this.configurationService.checkPhoneNumber(sponsor.getPhone());
			sponsor.setPhone(tlf);
		}
		if (sponsor.getId() == 0)
			sponsor.getUserAccount().setBanned(false);

		sponsor = this.sponsorRepository.save(sponsor);
		if (create)
			this.boxService.createSystemBoxes(sponsor);

		return sponsor;
	}

	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() != 0);
		final Collection<Box> boxes = sponsor.getBoxes();
		// Collection<Message> receivedMessages = sponsor.getReceivedMessages();
		// Collection<Message> sentMessages = sponsor.getSentMessages();
		final Collection<SocialProfile> socialProfiles = sponsor.getSocialProfiles();
		this.sponsorRepository.delete(sponsor);
		this.boxService.delete(boxes);
		// messageService.delete(receivedMessages);
		// messageService.delete(sentMessages);
		this.socialProfileService.delete(socialProfiles);

	}

	// Other business methods 

	public Sponsor findByPrincipal() {
		Sponsor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Sponsor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Sponsor res;
		res = this.sponsorRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Sponsor findByUserAccountId(final int userAccountId) {
		return this.sponsorRepository.findByUserAccountId(userAccountId);
	}

}
