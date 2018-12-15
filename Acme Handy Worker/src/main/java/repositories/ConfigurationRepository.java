
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Configuration;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Integer> {

	@Query("select s from Configuration c join c.spamWords s")
	Collection<String> getSpamWords();

	@Query("select c from Configuration c join c.positiveWords s")
	Collection<String> getPositiveWords();

	@Query("select c from Configuration c join c.negativeWords n")
	Collection<String> getNegativeWords();

	// Saca el VAT Tax de la clase Configuration

	@Query("select c.VATTax from Configuration c")
	double getTax();

	@Query("select bannerURL from Configuration c")
	String getBannerURL();

	@Query("select c from Configuration c")
	Configuration getConfiguration();
}
