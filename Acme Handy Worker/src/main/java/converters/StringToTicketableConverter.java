
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TickerRepository;
import domain.Ticker;

@Component
@Transactional
public class StringToTicketableConverter implements Converter<String, Ticker> {

	@Autowired
	private TickerRepository	repository;


	@Override
	public Ticker convert(final String s) {
		Ticker res;
		int id;

		try {
			if (!StringUtils.isEmpty(s))
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
