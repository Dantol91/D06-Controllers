
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsement;

@Repository
public interface EndorsementRepository extends JpaRepository<Endorsement, Integer> {

	@Query("select e from Endorsement e where e.sender.id = ?1")
	Collection<Endorsement> getBySenderId(int senderId);
	
	@Query("select e from Endorsement e where e.receiver.id = ?1")
	Collection<Endorsement> getByReceiverId(int receiverId);
	
}
