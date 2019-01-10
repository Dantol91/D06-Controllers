
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.ProfessionalRecord;

@Repository
public interface ProfessionalRecordRepository extends GenericRepository<ProfessionalRecord> {

	@Query("select p from ProfessionalRecord p where p.curriculum.id = ?1")
	Collection<ProfessionalRecord> findByCurriculumId(Integer curriculumId);

}
