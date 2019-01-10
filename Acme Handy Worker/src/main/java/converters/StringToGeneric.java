
package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import repositories.GenericRepository;
import domain.DomainEntity;

@Transactional
public class StringToGeneric<S extends DomainEntity, T extends GenericRepository<S>> implements Converter<String, S> {

	@Autowired
	private T	repository;


	@Override
	public S convert(final String s) {
		S res = null;
		try {
			if (!StringUtils.isEmpty(s)) {
				final int id = Integer.valueOf(s);
				res = this.repository.findOne(id);
			}
		} catch (final Throwable t) {
			throw new IllegalArgumentException(t);
		}
		return res;
	}
}
