
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.FixupTaskRepository;
import domain.FixupTask;

@Component
@Transactional
public class StringToFixupTaskConverter implements Converter<String, FixupTask> {

	@Autowired
	private FixupTaskRepository	repository;


	@Override
	public FixupTask convert(final String s) {
		FixupTask res;
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
