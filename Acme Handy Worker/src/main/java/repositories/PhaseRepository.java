
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Phase;

@Repository
public interface PhaseRepository extends GenericRepository<Phase> {

	@Query("select p from Phase p where p.workPlan.id = ?1")
	Collection<Phase> findByWorkPlan(Integer workPlanId);

}
