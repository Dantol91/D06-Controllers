
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import repositories.GenericRepository;
import domain.Actor;
import domain.DomainEntity;

@Transactional
public class GenericService<R extends DomainEntity, T extends GenericRepository<R>> {

	// Repository

	@Autowired
	private T				repository;
	@Autowired
	private ServiceUtils	serviceUtils;


	public R findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<R> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<R> findAll() {
		return this.repository.findAll();
	}

	public R checkObjectSave(final R object) {
		this.serviceUtils.checkIdSave(object);
		final R res = this.checkObjectExists(object);
		return res;
	}

	public R checkObject(final R object) {
		this.serviceUtils.checkId(object);
		final R res = this.checkObjectExists(object);
		return res;
	}

	public void checkPermisionActor(final Actor actor, final String[] auths) {
		this.serviceUtils.checkActor(actor);
		this.serviceUtils.checkAnyAuthority(auths);
	}

	public void checkPermisionActors(final Actor[] actors, final String[] auths) {
		this.serviceUtils.checkAnyActor(actors);
		this.serviceUtils.checkAnyAuthority(auths);
	}

	public R checkObjectExists(final R object) {
		R res = object;
		if (object.getId() > 0)
			res = this.repository.findOne(object.getId());
		return res;
	}

}
