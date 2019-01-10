
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Sponsorship;

@Repository
public interface SponsorshipRepository extends GenericRepository<Sponsorship> {

	@Query("select s from Sponsorship s where s.sponsor.id = ?1")
	Collection<Sponsorship> findBySponsor(int sponsorId);
	
}
