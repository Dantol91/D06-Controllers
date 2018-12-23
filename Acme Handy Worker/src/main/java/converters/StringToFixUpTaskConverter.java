package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.FixUpTaskRepository;
import domain.FixUpTask;

@Component
@Transactional
public class StringToFixUpTaskConverter implements Converter<String, FixUpTask> {

	@Autowired
	FixUpTaskRepository	FixUpTaskRepository;


	@Override
	public FixUpTask convert(final String text) {
		FixUpTask result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.FixUpTaskRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
