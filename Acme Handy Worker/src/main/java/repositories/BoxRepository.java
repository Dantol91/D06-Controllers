
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Box;

@Repository
public interface BoxRepository extends JpaRepository<Box, Integer> {

	@Query("select b from Actor a join a.boxes b where a.id = ?1 and b.name='out box'")
	Box getOutBoxFolderFromActorId(int id);

	@Query("select b from Actor a join a.boxes b where a.id = ?1 and b.name='in box'")
	Box getInBoxFromActorId(int id);

	@Query("select b from Actor a join a.boxes b where a.id = ?1 and b.name='spam box'")
	Box getSpamBoxFromActorId(int id);

	@Query("select b from Actor a join a.boxes b where a.id = ?1 and b.name='trash box'")
	Box getTrashBoxFromActorId(int id);

	@Query("select b from Box b join b.messages m where m.id = ?1")
	Collection<Box> getMessageBoxFromMessageId(int id);

	@Query("select b from Box b join b.messages m where m.id=?1 and b.actor.id=?2")
	Box getBoxFromMessageAndActorId(int messageId, int actorId);

	@Query("select b from Box b where b.actor.id=?1 and b.parentBox=null")
	Collection<Box> getFirstLevelBoxesFromActorId(int actorId);

	@Query("select b from Box b join b.messages m where m.id=?1")
	Box getBoxFromMessageId(int messageId);

	@Query("select b from Box b where b.parentBox.id=?1")
	Collection<Box> getChildBoxes(int boxId);

}
