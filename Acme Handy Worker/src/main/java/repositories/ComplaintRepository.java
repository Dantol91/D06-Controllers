
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

	@Query("select min(f.complaints.size), max(f.complaints.size), avg(f.complaints.size), sqrt(sum(f.complaints.size*f.complaints.size)/count(f.complaints.size)-(avg(f.complaints.size)*avg(f.complaints.size))) from FixUpTask f")
	Double[] computeMinMaxAvgStddevComplaintsPerFixUpTasks();

	/*
	 * @Query("select c from Complaint c where c.referee.id = ?1")
	 * Collection<Complaint> findComplaintsByReferee(Integer refereeId);
	 * 
	 * @Query("select c from Complaint c where c.referee IS NULL")
	 * Collection<Complaint> findComplaintsWithoutReferee();
	 */
}
