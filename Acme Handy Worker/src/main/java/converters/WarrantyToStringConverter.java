
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Warranty;

@Component
@Transactional
public class WarrantyToStringConverter implements Converter<Warranty, String> {

	@Override
	public String convert(final Warranty t) {
		String result;
		if (t == null)
			result = null;
		else
			result = String.valueOf(t.getId());

		return result;
	}

}
