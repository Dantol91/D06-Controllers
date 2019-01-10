
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Tutorial;

@Repository
public interface TutorialRepository extends GenericRepository<Tutorial> {

	@Query("select t from Tutorial t where t.handyWorker.id = ?1")
	Collection<Tutorial> findTutorialsByHandyWorker(int handyWorkerId);
	
	@Query("select t from Tutorial t join t.sponsorships s where s.id = ?1")
	Collection<Tutorial> findTutorialsBySponsorship(int sponsorshipId);
	
}
