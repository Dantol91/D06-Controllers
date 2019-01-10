
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import domain.Actor;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfileService {

	// Managed Repository

	@Autowired
	private SocialProfileRepository	repository;

	// Services

	@Autowired
	private ActorService			actorService;
	@Autowired
	private ServiceUtils			serviceUtils;


	// CRUD methods

	public SocialProfile findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<SocialProfile> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<SocialProfile> findAll() {
		return this.repository.findAll();
	}

	public Collection<SocialProfile> findAllByActor(final Actor a) {
		Assert.notNull(a);
		Assert.isTrue(a.getId() > 0);
		Assert.notNull(this.actorService.findOne(a.getId()));
		return this.repository.findByActor(a.getId());
	}

	public SocialProfile create(final Actor a) {
		final SocialProfile res = new SocialProfile();
		res.setActor(a);
		return res;
	}

	public SocialProfile save(final SocialProfile object) {
		final SocialProfile socialProfile = (SocialProfile) this.serviceUtils.checkObjectSave(object);
		if (socialProfile.getId() == 0)
			socialProfile.setActor(this.actorService.findPrincipal());
		else {
			socialProfile.setNetworkName(object.getNetworkName());
			socialProfile.setNick(object.getNick());
			socialProfile.setProfile(object.getProfile());
		}
		this.serviceUtils.checkActor(socialProfile.getActor());
		final SocialProfile res = this.repository.save(socialProfile);
		return res;
	}

	public void delete(final SocialProfile object) {
		final SocialProfile socialProfile = (SocialProfile) this.serviceUtils.checkObject(object);
		this.serviceUtils.checkActor(socialProfile.getActor());
		this.repository.delete(socialProfile);
	}

	// Other methods

	public void flush() {
		this.repository.flush();
	}

}
