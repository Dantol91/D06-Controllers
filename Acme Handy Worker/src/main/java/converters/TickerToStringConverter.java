
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Ticker;

@Component
@Transactional
public class TickerToStringConverter implements Converter<Ticker, String> {

	@Override
	public String convert(final Ticker t) {
		String result;
		if (t == null)
			result = null;
		else
			result = String.valueOf(t.getId());

		return result;
	}

}
