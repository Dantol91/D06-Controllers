
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WorkPlanRepository;
import domain.Application;
import domain.FixupTask;
import domain.HandyWorker;
import domain.WorkPlan;

@Service
@Transactional
public class WorkPlanService {

	//Managed Repository

	@Autowired
	private WorkPlanRepository	workPlanRepository;

	// Supporting Service
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private FixupTaskService	fixupTaskService;
	@Autowired
	private ApplicationService	applicationService;


	// Simple CRUD methods

	public WorkPlan create() {
		WorkPlan s;
		s = new WorkPlan();
		Assert.notNull(s);
		return s;
	}

	public Collection<WorkPlan> findAll() {
		return this.workPlanRepository.findAll();
	}

	public WorkPlan findOne(final int workPlanId) {
		return this.workPlanRepository.findOne(workPlanId);
	}

	public WorkPlan save(final WorkPlan w) {
		Assert.notNull(w);
		return this.workPlanRepository.save(w);
	}

	public void delete(final WorkPlan w) {
		Assert.notNull(w);
		final HandyWorker hw = w.getHandyWorker();
		this.handyWorkerService.save(hw);
		this.workPlanRepository.delete(w);
	}

	public Boolean checkStatusApplicationAccepted(final WorkPlan w) {
		Boolean res = false;
		final int fixupTaskId = w.getFixupTask().getId();
		final Collection<Application> applications = this.applicationService.findApplicationsByHandyWorker(w.getHandyWorker());
		for (final Application a : applications)
			if (a.getFixupTask().getId() == fixupTaskId) {
				if (a.getStatus().equals("ACCEPTED"))
					res = true;
				break;
			}
		return res;
	}

	public Collection<WorkPlan> findWorkPlanByHandyWorker(final HandyWorker h) {
		Assert.notNull(h);
		Assert.isTrue(h.getId() > 0);
		Assert.notNull(this.handyWorkerService.findOne(h.getId()));
		return this.workPlanRepository.findWorkPlanByHandyWorker(h.getId());
	}

	public Collection<WorkPlan> findWorkPlanByFixupTask(final FixupTask f) {
		Assert.notNull(f);
		Assert.isTrue(f.getId() > 0);
		Assert.notNull(this.handyWorkerService.findOne(f.getId()));
		return this.workPlanRepository.findWorkPlanByHandyWorker(f.getId());
	}

}
