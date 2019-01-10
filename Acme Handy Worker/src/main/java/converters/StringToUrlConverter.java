
package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import domain.Url;

@Component
@Transactional
public class StringToUrlConverter implements Converter<String, Url> {

	@Override
	public Url convert(final String s) {
		Url res = null;
		try {
			if (!StringUtils.isEmpty(s)) {
				res = new Url();
				res.setUrl(s);
			}
		} catch (final Throwable t) {
			throw new IllegalArgumentException(t);
		}
		return res;
	}
}
