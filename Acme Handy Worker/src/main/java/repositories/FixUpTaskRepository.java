
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUpTask;

@Repository
public interface FixUpTaskRepository extends JpaRepository<FixUpTask, Integer> {

	@Query("select sum(case when f.complaints.size > 0 then 1.0 else 0.0 end)/count(DISTINCT f) as " + "Complaints_ratio from FixUpTask f")
	Double getComplaintFixUpTasks();

	@Query("select f from FixUpTask f join f.category c where c.id = ?1")
	Collection<FixUpTask> getFixUpTasksByCategory(int categoryId);

	@Query("select count(a) from HandyWorker h join h.applications a where a.fixUpTask.id = ?1 and h.id = ?2 and a.status = 'ACCEPTED'")
	Integer getAcceptedApplicationsByHandyWorkerId(int handyWorkerId, int fixUpTaskId);

	@Query("select f from FixUpTask f where f.ticker like CONCAT('%',?1,'%') or f.description like CONCAT('%',?1,'%') or f.address like CONCAT('%',?1,'%') and f.publicationDate >= CURRENT_DATE")
	Collection<FixUpTask> getFixUpTasksByKeyWord(String keyWord);

	@Query("select avg(c.fixUpTasks.size),min(c.fixUpTasks.size),max(c.fixUpTasks.size),sqrt(sum(c.fixUpTasks.size*c.fixUpTasks.size)/count(c.fixUpTasks.size)-(avg(c.fixUpTasks.size)*avg(c.fixUpTasks.size))) from Customer c")
	Double[] computeAvgMinMaxStdvPerUser();

	@Query("select avg(f.maxPrice), min(f.maxPrice) , max(f.maxPrice), sqrt(sum(f.maxPrice*f.maxPrice)/count(f.maxPrice)-(avg(f.maxPrice)*avg(f.maxPrice))) from FixUpTask f")
	Double[] computeAvgMinMaxStdvPerMaxPrice();

	@Query("select f from FixUpTask f where f.publicationDate > CURRENT_DATE")
	Collection<FixUpTask> getNotPublishedFixUpTaks();

	@Query("select a.fixUpTask from HandyWorker h join h.applications a where h.id = ?1")
	Collection<FixUpTask> getHandyWorkerApplicationFixUpTasks(int handyWorkerId);

	@Query("select f from FixUpTask f where f.publicationDate <= CURRENT_DATE")
	Collection<FixUpTask> getVisibleFixUpTasks();

	@Query("select f from FixUpTask f where f.endDate <= CURRENT_DATE")
	Collection<FixUpTask> getEndedFixUpTasks();

	@Query("select f from FixUpTask f where f.publicationDate <= CURRENT_DATE and f.category.id = ?1")
	Collection<FixUpTask> getVisibleFixUpTasksByCategory(int categoryId);

	@Query("select f from Customer c join c.fixUpTasks f where c.id = ?1")
	Collection<FixUpTask> getFixUpTasksByCustomerId(int customerId);

	@Query("select y from HandyWorker h join h.applications x join x.fixUpTask y where x.status = 'ACCEPTED' and h.id=?1")
	Collection<FixUpTask> getFixUpTasksHandyWorkerApplication(int handyWorkerId);

	@Query("select f from FixUpTask f join f.applications a where a.id = ?1")
	FixUpTask getFixUpTaskByApplicationId(int applicationId);
}
