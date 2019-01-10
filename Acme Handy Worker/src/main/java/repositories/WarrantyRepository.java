
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Warranty;

@Repository
public interface WarrantyRepository extends GenericRepository<Warranty> {

	@Query("select w from Warranty w where w.draft = false")
	Collection<Warranty> findWarrantyNotDraft();

}
