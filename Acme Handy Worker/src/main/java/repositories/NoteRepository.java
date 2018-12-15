
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {

	@Query("select min(r.notes.size), max(r.notes.size), avg(r.notes.size), stddev(r.notes.size) from Report r")
	Double[] computeMinMaxAvgStddevNotesPerReport();

	//@Query("select r from Referee r join r.complaints c join c.notes n where r.id=?1")
	//Collection<Note> getNotesToRefereeComplaints(int refereeId);

}
