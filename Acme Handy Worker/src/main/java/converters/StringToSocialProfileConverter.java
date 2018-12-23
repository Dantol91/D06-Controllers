package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialProfile;
import repositories.SocialProfileRepository;

@Component
@Transactional
public class StringToSocialProfileConverter implements Converter<String, SocialProfile> {

	@Autowired
	SocialProfileRepository SocialProfileRepository;

	@Override
	public SocialProfile convert(final String text) {
		SocialProfile result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.SocialProfileRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
