
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.WorkPlan;

@Repository
public interface WorkPlanRepository extends GenericRepository<WorkPlan> {

	@Query("select w from WorkPlan w where w.handyWorker.id = ?1")
	Collection<WorkPlan> findWorkPlanByHandyWorker(int handyWorkerId);
	
	@Query("select w from WorkPlan w where w.fixupTask.id = ?1")
	Collection<WorkPlan> findWorkPlanByFixupTask(int fixupTaskId);
	
}
