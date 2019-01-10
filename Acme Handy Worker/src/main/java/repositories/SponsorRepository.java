
package repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsor;

@Repository
public interface SponsorRepository extends GenericRepository<Sponsor> {

	@Query("select s from Sponsor s where s.id = ?1")
	Sponsor findSponsorById(int id);

}
