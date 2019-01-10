
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select c from Complaint c where c.referee.id = ?1")
	Collection<Complaint> SearchComplaintByReferee(Integer refereeId);

	@Query("select c from Complaint c where c.referee IS NULL")
	Collection<Complaint> SearchComplaintWithoutReferee();
	
	@Query("select c from Complaint c where c.fixuptask.id = ?1")
	Collection<Complaint> findByFixupTaskId(int fixupTaskId);
}
