
package services;

import java.util.Collection;

import javax.transaction.Transactional;

@Transactional
public interface ServiceObjectDependantI<S, T> {

	public S findOne(Integer id);
	public Collection<S> findAll(Collection<Integer> ids);
	public Collection<S> findAll();
	public Collection<S> findAll(T dependency);
	public S create(T dependency);
	public S save(S object);
	public void delete(S object);

}
