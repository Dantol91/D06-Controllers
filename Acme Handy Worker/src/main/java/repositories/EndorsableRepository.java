
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Endorsable;

@Repository
public interface EndorsableRepository extends JpaRepository<Endorsable, Integer> {

	@Query("select a from Endorsable a where a.userAccount.id = ?1")
	Endorsable findOneByUserAccount(int userAccountId);
}
