
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository 

	@Autowired
	private ActorRepository	actorRepository;


	// Supporting services 

	// Constructors 

	public ActorService() {
		super();
	}

	// Simple CRUD methods 

	public Actor save(final Actor actor) {
		Assert.notNull(actor);
		return this.actorRepository.save(actor);
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);

		Actor result;

		result = this.actorRepository.findOne(actorId);

		return result;
	}

	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Other Business Methods 

	public Collection<Actor> getSuspiciousActors(final boolean suspicious) {
		return this.actorRepository.getSuspiciousActors(suspicious);
	}

	public Actor findByPrincipal() {
		Actor res;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		res = this.findByUserAccount(userAccount);
		Assert.notNull(res);
		return res;
	}

	public Actor findByUserAccount(final UserAccount userAccount) {
		Assert.notNull(userAccount);
		Actor result;

		result = this.actorRepository.findByUserAccountId(userAccount.getId());

		return result;
	}

	public Actor findByUserAccountId(final int userAccountId) {
		return this.actorRepository.findByUserAccountId(userAccountId);
	}

	public String getType(final UserAccount userAccount) {

		final List<Authority> authorities = new ArrayList<Authority>(userAccount.getAuthorities());

		return authorities.get(0).getAuthority();
	}

	// B.38.3 Un Admin puede banear un actor que sea sospechoso(desactivar
	// su cuenta de usuario)

	public void banActor(final Actor actor) {
		Assert.isTrue(actor.getSuspicious());
		Assert.isTrue(actor.getUserAccount().Banned());

		actor.getUserAccount().setBanned(true);
		this.save(actor);
	}

	// B.38.4 Un Admin puede quitar el baneo a un actor(reactivar su
	// cuenta)

	public void bannedActor(final Actor actor) {
		Assert.isTrue(!actor.getUserAccount().Banned());

		actor.getUserAccount().setBanned(false);
		this.save(actor);
	}

}
