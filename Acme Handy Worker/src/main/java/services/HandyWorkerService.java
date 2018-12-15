
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.HandyWorkerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Finder;
import domain.HandyWorker;
import domain.Phase;
import domain.SocialProfile;

@Service
@Transactional
public class HandyWorkerService {

	// Managed repository
	@Autowired
	private HandyWorkerRepository	handyWorkerRepository;

	// Supporting services
	@Autowired
	private UserAccountService		userAccountService;
	@Autowired
	private BoxService				boxService;
	@Autowired
	private ConfigurationService	configurationService;
	@Autowired
	private FinderService			finderService;


	// Constructor

	public HandyWorkerService() {
		super();
	}

	// Simple CRUD methods

	public HandyWorker create() {

		final Collection<SocialProfile> socialProfiles = new ArrayList<SocialProfile>();
		HandyWorker h;
		UserAccount ua;
		Authority auth;

		h = new HandyWorker();
		ua = this.userAccountService.create();

		auth = new Authority();

		auth.setAuthority("HANDYWORKER");
		ua.addAuthority(auth);
		h.setUserAccount(ua);
		h.setSocialProfiles(socialProfiles);
		h.setSuspicious(false);

		return h;
	}

	public HandyWorker save(HandyWorker handyWorker) {
		Assert.notNull(handyWorker);

		if (handyWorker.getId() == 0)
			Assert.isTrue(this.userAccountService.findByUsername(handyWorker.getUserAccount().getUsername()) == null, "message.error.duplicatedUser");

		Boolean create = false;
		Finder f;

		// Comprobamos si se está creando el user
		if (handyWorker.getId() == 0) {
			create = true;
			Md5PasswordEncoder encoder;

			encoder = new Md5PasswordEncoder();
			handyWorker.getUserAccount().setPassword(encoder.encodePassword(handyWorker.getUserAccount().getPassword(), null));
		}

		if (handyWorker.getPhone() != null) {
			final String tlf = this.configurationService.checkPhoneNumber(handyWorker.getPhone());
			handyWorker.setPhone(tlf);
		}

		if (handyWorker.getId() == 0)

			handyWorker = this.handyWorkerRepository.save(handyWorker);
		if (create) {
			this.boxService.createSystemBoxes(handyWorker);
			f = this.finderService.create();

			this.finderService.save(f);
		}

		return handyWorker;
	}

	public Collection<HandyWorker> findAll() {
		return this.handyWorkerRepository.findAll();

	}

	public HandyWorker findOne(final int handyWorkerId) {
		Assert.notNull(handyWorkerId);

		final HandyWorker h;

		h = this.handyWorkerRepository.findOne(handyWorkerId);

		return h;
	}

	// Other business methods

	public HandyWorker findByApplicationId(final int applicationId) {

		return this.handyWorkerRepository.findByApplicationId(applicationId);
	}

	public HandyWorker findByPrincipal() {
		HandyWorker res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public HandyWorker findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		HandyWorker res;
		res = this.handyWorkerRepository.findByUserAccountId(userAccount.getId());
		return res;
	}

	public HandyWorker findByUserAccountId(final int userAccountId) {
		return this.handyWorkerRepository.findByUserAccountId(userAccountId);
	}

	public HandyWorker getHandyWorkerByCurriculumId(final int curriculumId) {
		Assert.notNull(curriculumId);

		return this.handyWorkerRepository.getHandyWorkerByCurriculumId(curriculumId);
	}

	public HandyWorker getTopThreeHandyWorkersComplaints() {

		return this.handyWorkerRepository.getTopThreeHandyWorkersComplaints();

	}

	//The listing of handy workers who have got accepted at least 10% more ap-plications than the average,
	//ordered by number of applications.
	public Collection<HandyWorker> getHandyWorkerApplications() {

		return this.handyWorkerRepository.getHandyWorkerApplications();

	}

	public Collection<HandyWorker> getEndorseHandyWorkers(final int customerId) {
		Collection<HandyWorker> handyWorkers;

		handyWorkers = this.handyWorkerRepository.getEndorseHandyWorkers(customerId);

		return handyWorkers;
	}

	public int findPhaseCreator(final Phase phase) {
		int result;

		result = this.handyWorkerRepository.getPhaseByHandyWorkerId(phase);

		return result;
	}

}
