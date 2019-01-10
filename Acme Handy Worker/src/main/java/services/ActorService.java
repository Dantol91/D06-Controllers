
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.Administrator;
import domain.Customer;
import domain.Folder;
import domain.HandyWorker;
import domain.Message;
import domain.Referee;
import domain.SocialProfile;
import domain.Sponsor;

@Service
@Transactional
public class ActorService {

	//Repositories

	@Autowired
	private ActorRepository			actorRepository;

	//Services

	@Autowired
	private SettingsService			settingsService;

	@Autowired
	private AdministratorService	adminService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private SponsorService			sponsorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private RefereeService			refereeService;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private SocialProfileService	socialProfileService;

	@Autowired
	private ServiceUtils			serviceUtils;


	// CRUD methods

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);
		Actor result;
		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Collection<Actor> findAllExceptMe(final Actor a) {
		Collection<Actor> result;
		result = this.actorRepository.findAll();
		result.remove(a);
		Assert.notNull(result);

		return result;
	}

	// Other methods

	public Actor findOneByUserAccount(final UserAccount userAccount) {
		return this.actorRepository.findOneByUserAccount(userAccount.getId());
	}

	public Actor findPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		return this.actorRepository.findOneByUserAccount(userAccount.getId());
	}

	public Map<String, Double> fixupTasksStats() {
		final Double[] statistics = this.actorRepository.fixupTasksStats();
		final Map<String, Double> res = new HashMap<>();

		res.put("MIN", statistics[0]);
		res.put("MAX", statistics[1]);
		res.put("AVG", statistics[2]);
		res.put("STD", statistics[3]);

		return res;
	}

	//List of suspicious actors
	public Collection<Actor> suspiciousActors() {
		final Collection<Actor> res = new ArrayList<Actor>();
		for (final Actor a : this.findAll())
			if (a.getSuspicious())
				res.add(a);

		return res;
	}

	public Collection<Actor> listSuspiciousActors() {
		final Collection<Actor> res = new ArrayList<Actor>();
		for (final Actor a : this.findAll())
			if (a instanceof Administrator)
				if (this.isSuspicious(a))
					res.add(a);

		return res;
	}

	public Boolean unBanActor(final Actor a) {
		Boolean res = false;
		if (a.getUserAccount().getBanned())
			for (final Authority au : a.getUserAccount().getAuthorities()) {
				if (au.getAuthority().equals(Authority.ADMIN)) {

					final Collection<Administrator> admins = this.adminService.findAll();
					for (final Administrator admin : admins)
						if (admin.getUserAccount().equals(a.getUserAccount())) {
							this.adminService.unBanActor(admin);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.CUSTOMER)) {
					final Collection<Customer> customers = this.customerService.findAll();
					for (final Customer customer : customers)
						if (customer.getUserAccount().equals(a.getUserAccount())) {
							this.customerService.unBanActor(customer);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.HANDYWORKER)) {
					final Collection<HandyWorker> handyWorkers = this.handyWorkerService.findAll();
					for (final HandyWorker handyWorker : handyWorkers)
						if (handyWorker.getUserAccount().equals(a.getUserAccount())) {
							this.handyWorkerService.unbanActor(handyWorker);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.REFEREE)) {
					final Collection<Referee> referees = this.refereeService.findAll();
					for (final Referee referee : referees)
						if (referee.getUserAccount().equals(a.getUserAccount())) {
							this.refereeService.unbanActor(referee);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.SPONSOR)) {
					final Collection<Sponsor> sponsors = this.sponsorService.findAll();
					for (final Sponsor sponsor : sponsors)
						if (sponsor.getUserAccount().equals(a.getUserAccount())) {
							this.sponsorService.unbanActor(sponsor);
							res = true;
							break;
						}

				}
				break;
			}
		return res;
	}

	public Boolean banActor(final Actor a) {
		if (this.isSuspicious(a))
			a.setSuspicious(true);
		Boolean res = false;
		if (a.getSuspicious() && !(a.getUserAccount().getBanned()))
			for (final Authority au : a.getUserAccount().getAuthorities()) {
				if (au.getAuthority().equals(Authority.ADMIN)) {
					final Collection<Administrator> admins = this.adminService.findAll();
					for (final Administrator admin : admins)
						if (admin.getUserAccount().equals(a.getUserAccount())) {
							this.adminService.banActor(admin);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.CUSTOMER)) {
					final Collection<Customer> customers = this.customerService.findAll();
					for (final Customer customer : customers)
						if (customer.getUserAccount().equals(a.getUserAccount())) {
							this.customerService.banActor(customer);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.HANDYWORKER)) {
					final Collection<HandyWorker> handyWorkers = this.handyWorkerService.findAll();
					for (final HandyWorker handyWorker : handyWorkers)
						if (handyWorker.getUserAccount().equals(a.getUserAccount())) {
							this.handyWorkerService.banActor(handyWorker);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.REFEREE)) {
					final Collection<Referee> referees = this.refereeService.findAll();
					for (final Referee referee : referees)
						if (referee.getUserAccount().equals(a.getUserAccount())) {
							this.refereeService.banActor(referee);
							res = true;
							break;
						}
					break;
				} else if (au.getAuthority().equals(Authority.SPONSOR)) {
					final Collection<Sponsor> sponsors = this.sponsorService.findAll();
					for (final Sponsor sponsor : sponsors)
						if (sponsor.getUserAccount().equals(a.getUserAccount())) {
							this.sponsorService.banActor(sponsor);
							res = true;
							break;
						}

				}
				break;
			}

		return res;
	}

	public boolean containsSpam(final String s) {
		boolean res = false;
		for (final String spamWord : this.settingsService.findSettings().getSpamWords())
			if (s.contains(spamWord)) {
				res = true;
				break;
			}
		return res;
	}

	public boolean isSuspicious(final Actor a) {
		boolean res = false;
		Assert.notNull(a);
		this.serviceUtils.checkId(a.getId());
		final Actor actor = this.actorRepository.findOne(a.getId());
		Assert.notNull(actor);
		res = this.containsSpam(actor.getAddress()) || this.containsSpam(actor.getEmail()) || this.containsSpam(actor.getMiddleName()) || this.containsSpam(actor.getName()) || this.containsSpam(actor.getPhone()) || this.containsSpam(actor.getPhoto())
			|| this.containsSpam(actor.getSurname());
		if (!res)
			for (final Folder f : this.folderService.findAllByActor(actor)) {
				res = this.containsSpam(f.getName());
				if (res)
					break;
			}
		if (!res)
			for (final Message m : this.messageService.findSendedMessages(actor)) {
				res = this.containsSpam(m.getBody()) || this.containsSpam(m.getPriority()) || this.containsSpam(m.getSubject());
				if (!res)
					for (final String tag : m.getTags()) {
						res = this.containsSpam(tag);
						if (res)
							break;
					}
				else
					break;
			}
		if (!res)
			for (final SocialProfile sp : this.socialProfileService.findAllByActor(actor)) {
				res = this.containsSpam(sp.getNetworkName()) || this.containsSpam(sp.getNick()) || this.containsSpam(sp.getProfile());
				if (res)
					break;
			}
		if (!res)
			res = this.containsSpam(actor.getUserAccount().getUsername());
		if (!res)
			if (actor instanceof Customer)
				res = res || this.customerService.isSuspicious((Customer) actor);
			else if (actor instanceof HandyWorker)
				res = res || this.handyWorkerService.isSuspicious((HandyWorker) actor);
		return res;
	}

}
