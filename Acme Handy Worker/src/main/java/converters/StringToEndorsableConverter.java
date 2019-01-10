
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EndorsableRepository;
import domain.Endorsable;

@Component
@Transactional
public class StringToEndorsableConverter implements Converter<String, Endorsable> {

	@Autowired
	private EndorsableRepository	repository;


	@Override
	public Endorsable convert(final String s) {
		Endorsable res;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				res = null;
			else {
				id = Integer.valueOf(s);
				res = this.repository.findOne(id);
			}
		} catch (final Throwable t) {
			throw new IllegalArgumentException(t);
		}
		return res;
	}

}
