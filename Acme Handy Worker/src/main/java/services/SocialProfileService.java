
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed repository 

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	administratorService;


	// Constructors

	public SocialProfileService() {
		super();
	}

	// Simple CRUD methods 

	public SocialProfile create() {
		SocialProfile result;
		result = new SocialProfile();

		result.setNick("");
		result.setName("");
		result.setLink("");

		result.setActor(this.actorService.findByPrincipal());
		return result;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		socialProfile.setActor(this.actorService.findByPrincipal());

		SocialProfile result;

		result = this.socialProfileRepository.save(socialProfile);

		// Comprobamos si es spam
		this.administratorService.checkIsSpam(socialProfile.getNick());
		this.administratorService.checkIsSpam(socialProfile.getLink());
		this.administratorService.checkIsSpam(socialProfile.getName());

		return result;
	}

	public void delete(final SocialProfile socialProfile) {
		Assert.notNull(socialProfile);
		Assert.isTrue(socialProfile.getId() != 0);
		Assert.isTrue(this.socialProfileRepository.exists(socialProfile.getId()));

		this.socialProfileRepository.delete(socialProfile);
	}

	public void delete(final Iterable<SocialProfile> socialProfiles) {
		Assert.notNull(socialProfiles);
		this.socialProfileRepository.delete(socialProfiles);
	}

	public SocialProfile findOne(final int socialProfileId) {
		Assert.notNull(socialProfileId);
		Assert.isTrue(socialProfileId != 0);

		SocialProfile result;

		result = this.socialProfileRepository.findOne(socialProfileId);

		return result;
	}

	public SocialProfile findOneToEdit(final int socialProfileId) {
		Assert.notNull(socialProfileId);
		Assert.isTrue(socialProfileId != 0);

		SocialProfile result;

		result = this.socialProfileRepository.findOne(socialProfileId);

		this.checkPrincipal(result);

		return result;
	}

	public Collection<SocialProfile> findAll() {
		Collection<SocialProfile> result;

		result = this.socialProfileRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public void addToPrincipal(final SocialProfile socialProfile) {
		final Actor actor = this.actorService.findByPrincipal();
		actor.getSocialProfiles().add(socialProfile);

	}

	//Other business methods

	//	public String checkPrincipal(Actor prin) {
	//		String res = "";
	//
	//		for (Authority au : prin.getUserAccount().getAuthorities()) {
	//			if (au.getAuthority().equals("ADMIN")) {
	//				res = "redirect:/actor/admin/admin/edit.do";
	//			} else if (au.getAuthority().equals("EXPLORER")) {
	//				res = "redirect:/actor/admin/explorer/edit.do";
	//			} else if (au.getAuthority().equals("RANGER")) {
	//				res = "redirect:/actor/admin/ranger/edit.do";
	//			} else if (au.getAuthority().equals("SPONSOR")) {
	//				res = "redirect:/actor/admin/sponsor/edit.do";
	//			} else if (au.getAuthority().equals("MANAGER")) {
	//				res = "redirect:/actor/admin/manager/edit.do";
	//			} else if (au.getAuthority().equals("AUDITOR")) {
	//				res = "redirect:/actor/admin/auditor/edit.do";
	//			}
	//		}
	//
	//		return res;
	//	}

	public void checkPrincipal(final SocialProfile sc) {
		Actor a;

		a = this.actorService.findByPrincipal();

		Assert.isTrue(a.getSocialProfiles().contains(sc));
	}

}
