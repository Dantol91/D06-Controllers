package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Endorse;


@Component
@Transactional
public class EndorseToStringConverter implements Converter<Endorse, String> {

	@Override
	public String convert(final Endorse endorse) {
		String result;

		if (endorse == null)
			result = null;
		else
			result = String.valueOf(endorse.getId());

		return result;
	}
}
