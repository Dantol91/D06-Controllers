
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.WorkPlan;

@Component
@Transactional
public class WorkplanToStringConverter implements Converter<WorkPlan, String> {

	@Override
	public String convert(final WorkPlan t) {
		String result;
		if (t == null)
			result = null;
		else
			result = String.valueOf(t.getId());

		return result;
	}

}
