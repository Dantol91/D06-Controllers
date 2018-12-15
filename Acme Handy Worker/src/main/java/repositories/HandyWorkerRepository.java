
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;
import domain.Phase;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select h from HandyWorker h where h.userAccount.id = ?1")
	HandyWorker findByUserAccountId(int userAccountId);

	@Query("select h from HandyWorker h join h.applications a where a.id = ?1")
	HandyWorker findByApplicationId(int applicatioId);

	@Query("select h from HandyWorker h join h.curriculum c where c.id = ?1")
	HandyWorker getHandyWorkerByCurriculumId(int curriculumId);

	@Query("select a.handyWorker from Application a group by a.handyWorker order by sum(a.fixUpTask.complaints.size)")
	HandyWorker getTopThreeHandyWorkersComplaints();

	@Query("select h1 from HandyWorker h1 where h1.applications.size/ (select avg(h2.applications.size) from HandyWorker h2)>=1.1 order by h1.applications.size")
	Collection<HandyWorker> getHandyWorkerApplications();

	@Query("select distinct h from HandyWorker h join h.applications a where a.status='ACCEPTED' and a.fixUpTask.customer.id=?1")
	Collection<HandyWorker> getEndorseHandyWorkers(int customerId);

	@Query("select a.handyWorker.id from FixUpTask f join f.applications a where ?1 member of f.phases and a.status = 'ACCEPTED'")
	int getPhaseByHandyWorkerId(Phase phase);

}
