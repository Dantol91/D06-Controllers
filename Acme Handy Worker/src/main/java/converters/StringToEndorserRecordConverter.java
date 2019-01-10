
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.EndorserRecordRepository;
import domain.EndorserRecord;

@Component
@Transactional
public class StringToEndorserRecordConverter implements Converter<String, EndorserRecord> {

	@Autowired
	private EndorserRecordRepository	repository;


	@Override
	public EndorserRecord convert(final String s) {
		EndorserRecord res;
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
