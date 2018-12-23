package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.EndorseRepository;
import domain.Endorse;

@Component
@Transactional
public class StringToEndorseConverter implements
		Converter<String, Endorse> {

	@Autowired
	EndorseRepository endorseRepository;

	@Override
	public Endorse convert(final String text) {
		Endorse result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.endorseRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
