
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Section;

@Repository
public interface SectionRepository extends GenericRepository<Section> {

	@Query("select s from Section s where s.tutorial.id = ?1")
	Collection<Section> findByTutorial(Integer tutorialId);

	@Query("select s from Section s where s.numberOrder = ?1 and s.tutorial.id = ?2")
	Collection<Section> findByNumberOrder(Integer numberOrder, Integer tutorialId);

	@Query("select s from Section s where s.numberOrder > ?1 and s.tutorial.id = ?2")
	Collection<Section> findNextSections(Integer numberOrder, Integer tutorialId);

}
