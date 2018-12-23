package controllers.administrator;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.CustomerService;
import services.HandyWorkerService;
import services.RegereeService;
import services.SponsorService;
import controllers.AbstractController;
import domain.Actor;
import domain.Administrator;
import domain.Referee;
import domain.HandyWorker;
import domain.Customer;
import domain.SocialProfile;
import domain.Sponsor;

@Controller
@RequestMapping("/actor")
public class ActorController extends AbstractController {

	// Services

	@Autowired
	private ActorService actorService;

	@Autowired
	private HandyWorkerService handyWorkerService;

	@Autowired
	private RefereeService refereeService;

	@Autowired
	private SponsorService sponsorService;

	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private CustomerService customerService;

	// Constructors

	public ActorController() {
		super();
	}

	// Listing 

	@RequestMapping(value = "admin/list", method = RequestMethod.GET)
	public ModelAndView listSuspicious() {
		ModelAndView result;
		Collection<Actor> suspiciousActors;

		suspiciousActors = actorService.getSuspiciousActors();

		result = new ModelAndView("actor/list");
		result.addObject("actors", suspiciousActors);

		return result;
	}

	// Edition
	
	// Admin

	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public ModelAndView editAdmin() {
		ModelAndView result;
		Administrator administrator;
		administrator = administratorService.findByPrincipal();
		Assert.notNull(administrator);
		result = createEditModelAndViewAdmin(administrator);
		result.addObject(administrator);
		return result;

	}

	@RequestMapping(value = "/admin/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveAdmin(@Valid Administrator actor,
			BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewAdmin(actor);
		} else {
			try {

				this.administratorService.save(actor);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewAdmin(actor,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	
	// Admin

	protected ModelAndView createEditModelAndViewAdmin(Administrator actor) {
		ModelAndView result;

		result = createEditModelAndViewAdmin(actor, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewAdmin(Administrator admin,
			String message) {
		ModelAndView result;

		Collection<SocialProfile> socialProfiles;
		socialProfiles = admin.getSocialProfiles();

		result = new ModelAndView("administrator/edit");
		result.addObject("admin", admin);
		result.addObject("socialProfiles", socialProfiles);

		return result;

	}

	// Edition
	
	// HandyWorker

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.GET)
	public ModelAndView editHandyWorker() {
		ModelAndView result;
		HandyWorker handyWorker;
		handyWorker = explorerService.findByPrincipal();
		Assert.notNull(handyWorker);
		result = createEditModelAndViewHandyWorker(handyWorker);
		result.addObject(handyWorker);
		return result;

	}

	@RequestMapping(value = "/handyWorker/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveHandyWorker(@Valid HandyWorker handyWorker,
			BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewHandyWorker(handyWorker);
		} else {
			try {

				this.explorerService.save(handyWorker);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewHandyWorker(handyWorker,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	
	// HandyWorker
	
	protected ModelAndView createEditModelAndViewHandyWorker(HandyWorker handyWorker) {
		ModelAndView result;

		result = createEditModelAndViewHandyWorker(handyWorker, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewHandyWorker(HandyWorker handyWorker,
			String message) {
		ModelAndView result;

		Collection<SocialProfile> socialProfiles;
		socialProfiles = handyWorker.getSocialProfiles();

		result = new ModelAndView("handyWorker/edit");
		result.addObject("handyWorker", handyWorker);
		result.addObject("socialProfiles", socialProfiles);

		return result;

	}

	// Edition
	
	// Referee

	@RequestMapping(value = "/referee/edit", method = RequestMethod.GET)
	public ModelAndView editReferee() {
		ModelAndView result;
		Referee referee;
		referee = rangerService.findByPrincipal();
		Assert.notNull(referee);
		result = createEditModelAndViewRanger(referee);
		result.addObject(referee);
		return result;

	}

	@RequestMapping(value = "/referee/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveReferee(@Valid Referee referee, BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewReferee(referee);
		} else {
			try {

				this.rangerService.save(referee);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewRanger(referee,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	
	// Referee

	protected ModelAndView createEditModelAndViewReferee(Referee referee) {
		ModelAndView result;

		result = createEditModelAndViewRanger(referee, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewRanger(Referee referee,
			String message) {
		ModelAndView result;

		Collection<SocialProfile> socialProfiles;
		socialProfiles = referee.getSocialProfiles();

		result = new ModelAndView("referee/edit");
		result.addObject("referee", referee);
		result.addObject("socialProfiles", socialProfiles);

		return result;

	}

	// Edition
	
	// Sponsor

	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Sponsor sponsor;
		sponsor = sponsorService.findByPrincipal();
		Assert.notNull(sponsor);
		result = createEditModelAndView(sponsor);
		result.addObject(sponsor);
		return result;

	}

	@RequestMapping(value = "/sponsor/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid Sponsor sponsor, BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndView(sponsor);
		} else {
			try {

				this.sponsorService.save(sponsor);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndView(sponsor,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	
	// Sponsor

	protected ModelAndView createEditModelAndView(Sponsor sponsor) {
		ModelAndView result;

		result = createEditModelAndView(sponsor, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Sponsor sponsor,
			String message) {
		ModelAndView result;

		Collection<SocialIdentity> socialProfiles;
		socialProfiles = sponsor.getSocialProfiles();

		result = new ModelAndView("sponsor/edit");
		result.addObject("sponsor", sponsor);
		result.addObject("socialProfiles", socialProfiles);

		return result;

	}

	// Edition
	
	// Customer

	@RequestMapping(value = "/customer/edit", method = RequestMethod.GET)
	public ModelAndView editCustomer() {
		ModelAndView result;
		Customer customer;
		customer = auditorService.findByPrincipal();
		Assert.notNull(customer);
		result = createEditModelAndViewAuditor(customer);
		result.addObject(customer);
		return result;

	}

	@RequestMapping(value = "/customer/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveCustomer(@Valid Customer customer,
			BindingResult binding) {
		ModelAndView result = null;

		if (binding.hasErrors()) {
			result = this.createEditModelAndViewCustomer(customer);
		} else {
			try {

				this.auditorService.save(auditor);
				result = new ModelAndView("redirect:/");

			} catch (Throwable oops) {
				result = this.createEditModelAndViewAuditor(auditor,
						"actor.commit.error");
			}
		}

		return result;
	}

	// Ancillary methods
	
	// Customer

	protected ModelAndView createEditModelAndViewCustomer(Customer customer) {
		ModelAndView result;

		result = createEditModelAndViewCustomer(customer, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewCustomer(Customer customer,
			String message) {
		ModelAndView result;

		Collection<SocialProfile> socialProfiles;
		socialProfiles = customer.getSocialProfiles();

		result = new ModelAndView("customer/edit");
		result.addObject("customer", customer);
		result.addObject("socialProfiles", socialProfiles);

		return result;

	}

	// Ban/Unban 

	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam(required = true) int actorId) {
		ModelAndView result;
		Collection<Actor> suspiciousActors;
		Actor a;

		a = actorService.findOne(actorId);

		actorService.banActor(a);
		suspiciousActors = actorService.getSuspiciousActors();

		result = new ModelAndView("actor/list");
		result.addObject("actors", suspiciousActors);

		return result;
	}

	@RequestMapping(value = "/unban", method = RequestMethod.GET)
	public ModelAndView unban(@RequestParam(required = true) int actorId) {
		ModelAndView result;
		Collection<Actor> suspiciousActors;
		Actor a;

		a = actorService.findOne(actorId);

		actorService.unbanActor(a);
		suspiciousActors = actorService.getSuspiciousActors();

		result = new ModelAndView("actor/list");
		result.addObject("actors", suspiciousActors);

		return result;
	}

}
