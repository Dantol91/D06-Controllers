
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	@Query("select sum(case when a.status = 'PENDING' " + "then 1.0 else 0.0 end)/count(a) as pendingStatus_ratio from Application a")
	Double getPendingRatio();

	@Query("select sum(case when a.status = 'REJECTED' " + "then 1.0 else 0.0 end)/count(a) as rejectedStatus_ratio from Application a")
	Double getRejectedRatio();

	@Query("select sum(case when a.status = 'ACCEPTED' " + "then 1.0 else 0.0 end)/count(a) as acceptedStatus_ratio from Application a")
	Double getAcceptedRatio();

	@Query("select a from HandyWorker h join h.applications a where h.id = ?1")
	Collection<Application> getHandyWorkerApplications(int handyWorkerId);

	@Query("select a from HandyWorker h join h.applications a where h.id = ?1 group by a.status")
	Collection<Application> getHandyWorkerApplicationsByStatus(int handyWorkerId);

	@Query("select distinct a.status from HandyWorker h join h.applications a where h.id = ?1")
	Collection<String> getSetOfStatus(int handyWorkerId);

	@Query("select a from HandyWorker h join h.applications a where h.id = ?1 and a.status= ?2")
	Collection<Application> getApplicationsByStatusAndHandyWorkerId(int handyWorkerId, String status);

	@Query("select avg(f.applications.size), min(f.applications.size) , max(f.applications.size), sqrt(sum(f.applications.size*f.applications.size)/count(f.applications.size)-(avg(f.applications.size)*avg(f.applications.size))) from FixUpTask f")
	Double[] computeAvgMinMaxStdevApplicationPerFixUpTask();

	@Query("select avg(a.offeredPrice), min(a.offeredPrice) , max(a.offeredPrice), sqrt(sum(a.offeredPrice*a.offeredPrice)/count(a.offeredPrice)-(avg(a.offeredPrice)*avg(a.offeredPrice))) from Application a")
	Double[] computeAvgMinMaxStdvPerOfferedPrice();

	@Query("select a from Application a where a.status='PENDING' and a.registerMoment < CURRENT_DATE")
	Double getPendingRatioCannotChange();

}
