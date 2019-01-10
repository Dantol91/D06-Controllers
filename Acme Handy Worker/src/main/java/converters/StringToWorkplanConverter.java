
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.WorkPlanRepository;
import domain.WorkPlan;

@Component
@Transactional
public class StringToWorkplanConverter implements Converter<String, WorkPlan> {

	@Autowired
	private WorkPlanRepository	repository;


	@Override
	public WorkPlan convert(final String s) {
		WorkPlan res;
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
