package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.RefereeRepository;
import domain.Referee;

@Component
@Transactional
public class StringToRefereeConverter implements Converter<String, Referee>{
	
	@Autowired
	RefereeRepository RefereeRepository;


	@Override
	public Referee convert(final String text) {
		Referee result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.RefereeRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
