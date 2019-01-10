
package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Settings;

@Repository
public interface SettingsRepository extends GenericRepository<Settings> {

	@Query("select s from Settings s")
	Settings findSettings();

}
