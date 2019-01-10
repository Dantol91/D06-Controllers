
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Settings;

@Component
@Transactional
public class SettingsToStringConverter implements Converter<Settings, String> {

	@Override
	public String convert(final Settings settings) {
		String result;
		if (settings == null)
			result = null;
		else
			result = String.valueOf(settings.getId());

		return result;
	}

}
