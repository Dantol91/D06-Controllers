
package services;

import java.util.Collection;

import javax.transaction.Transactional;

@Transactional
public interface ServiceI<S> {

	public S findOne(Integer id);
	public Collection<S> findAll(Collection<Integer> ids);
	public Collection<S> findAll();
	public S create();
	public S save(S object);
	public void delete(S object);

}
