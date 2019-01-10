
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select h from Complaint c join c.fixuptask f join f.applications a join a.handyWorker h where a.status='ACCEPTED' group by h order by count(c) desc")
	Collection<HandyWorker> findTop3HandyWorkerWithMoreComplaints();

	@Query("select h from Application a5 join a5.handyWorker h where (((select count(a)*1.0 from Application a where a.handyWorker.id = h.id and a.status = 'ACCEPTED')) / ((select count(a2)*1.0 from Application a2 where a2.status = 'ACCEPTED') / (select count(h1) from HandyWorker h1))) >= 1.1 group by h order by count(a5)")
	Collection<HandyWorker> listHandyWorkerApplication();

	@Query("select h from Application a join a.handyWorker h where a.status = 'ACCEPTED' and a.fixupTask.id = ?1")
	HandyWorker findByAssignedFixupTask(Integer fixupTaskId);

}
