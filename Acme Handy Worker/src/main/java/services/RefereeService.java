
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Box;
import domain.Complaint;
import domain.Referee;
import domain.SocialProfile;

@Service
@Transactional
public class RefereeService {

	// Managed repository

	@Autowired
	private RefereeRepository		refereeRepository;

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
	public RefereeService() {
		super();
	}

	// Simple CRUD methods

	public Referee create() {
		final Collection<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		final Referee r;
		UserAccount ua;
		Authority auth;

		r = new Referee();
		ua = this.userAccountService.create();
		auth = new Authority();

		auth.setAuthority("REFEREE");
		ua.addAuthority(auth);
		r.setUserAccount(ua);
		r.setSocialProfiles(socialProfiles);
		r.setSuspicious(false);

		return r;
	}

	public Collection<Referee> findAll() {
		return this.refereeRepository.findAll();
	}

	public Referee findOne(final int refereeId) {
		Referee result;
		result = this.refereeRepository.findOne(refereeId);
		return result;
	}

	public Referee save(Referee referee) {
		Assert.notNull(referee);
		if (referee.getId() == 0)
			Assert.isTrue(this.userAccountService.findByUsername(referee.getUserAccount().getUsername()) == null, "message.error.duplicatedUser");
		Boolean create = false;

		// Se comprueba si se está creando el user
		if (referee.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			referee.getUserAccount().setPassword(encoder.encodePassword(referee.getUserAccount().getPassword(), null));
		}

		if (referee.getPhone() != null) {
			final String tlf = this.configurationService.checkPhoneNumber(referee.getPhone());
			referee.setPhone(tlf);
		}
		if (referee.getId() == 0)
			referee.getUserAccount().setBanned(false);

		referee = this.refereeRepository.save(referee);
		if (create)
			this.boxService.createSystemBoxes(referee);

		return referee;
	}

	public void delete(final Referee referee) {
		Assert.notNull(referee);
		Assert.isTrue(referee.getId() != 0);
		final Collection<Box> boxes = referee.getBoxes();
		final Collection<SocialProfile> socialProfiles = referee.getSocialProfiles();
		this.refereeRepository.delete(referee);
		this.boxService.delete(boxes);
		this.socialProfileService.delete(socialProfiles);

	}

	// Other business methods 

	public Referee findByPrincipal() {
		Referee res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Referee findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Referee res;
		res = this.refereeRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public Referee findByUserAccountId(final int userAccountId) {
		return this.refereeRepository.findByUserAccountId(userAccountId);
	}

	public void selfAssignedComplaint(final Referee referee, final Complaint complaint) {

		referee.getComplaints().add(complaint);
	}

}
