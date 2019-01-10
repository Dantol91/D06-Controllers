
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.FixupTask;

@Component
@Transactional
public class FixupTaskToStringConverter implements Converter<FixupTask, String> {

	@Override
	public String convert(final FixupTask fixupTask) {
		String result;
		if (fixupTask == null)
			result = null;
		else
			result = String.valueOf(fixupTask.getId());

		return result;
	}

}
