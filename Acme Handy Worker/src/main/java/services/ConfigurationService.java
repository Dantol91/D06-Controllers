
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository

	@Autowired
	private ConfigurationRepository	configurationRepository;


	// Supporting services

	// Constructor

	public ConfigurationService() {
		super();
	}

	// Simple CRUD Methods 

	public Configuration create() {
		Configuration c;

		c = new Configuration();

		return c;
	}

	public Configuration save(final Configuration config) {
		Assert.notNull(config);

		Configuration c;

		c = this.configurationRepository.save(config);

		return c;
	}

	public void delete(final Configuration config) {
		Assert.notNull(config);

		this.configurationRepository.delete(config);
	}

	public Configuration findOne(final int configurationId) {
		Configuration result;

		result = this.configurationRepository.findOne(configurationId);

		return result;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> result;

		result = this.configurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// Other Bussines Methods

	public Collection<String> getSpamWords() {
		return this.configurationRepository.getSpamWords();
	}

	public Collection<String> getPositiveWords() {
		return this.configurationRepository.getPositiveWords();
	}

	public Collection<String> getNegativeWords() {
		return this.configurationRepository.getNegativeWords();
	}

	public double getTax() {
		return this.configurationRepository.getTax();
	}

	public String getBannerURL() {
		return this.configurationRepository.getBannerURL();
	}

	public Configuration getConfiguration() {
		return this.configurationRepository.getConfiguration();
	}

	public String checkPhoneNumber(String tlf) {

		if (!tlf.startsWith("+") && tlf.length() > 4)
			tlf = this.getConfiguration().getCountryCode() + " " + tlf;

		return tlf;

	}
}
