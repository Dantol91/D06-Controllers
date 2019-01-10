
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import domain.Actor;

@Transactional
public interface ServiceActorDependantI<S> {

	public S findOne(Integer id);
	public Collection<S> findAll(Collection<Integer> ids);
	public Collection<S> findAll();
	public Collection<S> findAllByActor(Actor a);
	public S create(Actor a);
	public S save(S object);
	public void delete(S object);

}
