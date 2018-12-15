// Revisar

package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	// Service Under Test

	@Autowired
	private ConfigurationService	configurationService;


	// Tests

	/*
	 * @Test
	 * public void createAndSaveTest() {
	 * final Configuration config = this.configService.create();
	 * Assert.notNull(config);
	 * 
	 * final Collection<Configuration> configsBefore = this.configService.findAll();
	 * 
	 * final Collection<String> spamWords = new ArrayList<String>();
	 * final Collection<String> positiveWords = new ArrayList<String>();
	 * final Collection<String> negativeWords = new ArrayList<String>();
	 * final Collection<String> creditCardMakes = new ArrayList<String>();
	 * spamWords.add("palabra");
	 * positiveWords.add("agradable");
	 * negativeWords.add("insulto");
	 * 
	 * config.setBannerURL("http://www.banner.es");
	 * config.setCountryCode("34");
	 * config.setFinderCached(2);
	 * config.setFinderReturn(20);
	 * config.setSpamWords(spamWords);
	 * config.setPositiveWords(positiveWords);
	 * config.setNegativeWords(negativeWords);
	 * config.setCreditCardMakes(creditCardMakes);
	 * config.setVATTax(24);
	 * config.setWelcomeMessageEN("Welcome");
	 * config.setWelcomeMessageES("Welcome");
	 * 
	 * final Configuration configSaved = this.configService.save(config);
	 * 
	 * final Collection<Configuration> configsAfter = this.configService.findAll();
	 * 
	 * Assert.isTrue(!configsBefore.contains(configSaved));
	 * Assert.isTrue(configsAfter.contains(configSaved));
	 * 
	 * }
	 */

	/*
	 * @Test()
	 * public void testSave() {
	 * super.authenticate("administrator1");
	 * 
	 * final Collection<Configuration> config, saved;
	 * List<String> positiveWords;
	 * 
	 * config = this.configurationService.findAll();
	 * 
	 * ((Configuration) config).setFinderCached(2.0);
	 * ((Configuration) config).setFinderReturn(10);
	 * ((Configuration) config).setCountryCode("+10");
	 * 
	 * positiveWords = new ArrayList<>(((Configuration) config).getPositiveWords());
	 * positiveWords.add("Well");
	 * positiveWords.add("Obra maestra");
	 * 
	 * ((Configuration) config).setPositiveWords(positiveWords);
	 * 
	 * saved = this.configurationService.save(config);
	 * 
	 * Assert.notNull(saved);
	 * 
	 * super.unauthenticate();
	 * }
	 */

	@Test
	public void testFindAll() {
		Collection<Configuration> config;

		config = this.configurationService.findAll();

		Assert.notNull(config);

		System.out.println("ConfigurationCreate: " + this.configurationService.findAll());
	}

}
