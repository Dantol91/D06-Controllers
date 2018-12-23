package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.HandyWorkerRepository;
import domain.HandyWorker;

@Component
@Transactional
public class StringToHandyWorkerConverter implements Converter<String, HandyWorker> {

	@Autowired
	HandyWorkerRepository HandyWorkerRepository;


	@Override
	public HandyWorker convert(final String text) {
		HandyWorker result;
		int id;

		try {
			id = Integer.valueOf(text);
			result = this.HandyWorkerRepository.findOne(id);
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}
