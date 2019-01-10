
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends GenericRepository<Note> {

	@Query("select n from Note n where n.report.id = ?1")
	Collection<Note> findByReportId(int reportId);
	
}
