
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpTaskRepository;
import domain.Application;
import domain.Category;
import domain.Customer;
import domain.FixUpTask;

@Service
@Transactional
public class FixUpTaskService {

	// Managed Repository

	@Autowired
	private FixUpTaskRepository		fixUpTaskRepository;

	// Supporting Services

	@Autowired
	private ActorService			actorService;

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private CategoryService			categoryService;

	@Autowired
	private AdministratorService	administratorService;


	// Constructor

	public FixUpTaskService() {
		super();
	}

	// Simple CRUD Methods

	public FixUpTask create() {
		final FixUpTask f;
		Collection<Application> apps;

		f = new FixUpTask();
		apps = new ArrayList<>();

		f.setApplications(apps);
		f.setTicker(this.getTicker());

		return f;
	}

	public FixUpTask save(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getPublicationDate().after(new Date(System.currentTimeMillis() - 24 * 3600 * 1000l)));
		Assert.isTrue(fixUpTask.getStartDate().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(fixUpTask.getEndDate().after(new Date(System.currentTimeMillis())));
		Assert.isTrue(fixUpTask.getPublicationDate().before(fixUpTask.getStartDate()));
		Assert.isTrue(fixUpTask.getPublicationDate().before(fixUpTask.getEndDate()));
		Assert.isTrue(fixUpTask.getStartDate().before(fixUpTask.getEndDate()));

		FixUpTask result;

		result = this.fixUpTaskRepository.save(fixUpTask);

		return result;

	}

	// C.10.2
	public Collection<FixUpTask> findAll() {
		Collection<FixUpTask> result;

		result = this.fixUpTaskRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	// C.10.2
	public FixUpTask findOne(final int tripId) {
		FixUpTask result;

		result = this.fixUpTaskRepository.findOne(tripId);

		return result;
	}

	public FixUpTask findOneToEdit(final int tripId) {
		FixUpTask result;

		result = this.fixUpTaskRepository.findOne(tripId);

		//	this.checkPrincipal(result);

		return result;
	}

	public void delete(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);

		this.fixUpTaskRepository.delete(fixUpTask);
	}

	// Other Business Methods

	// B.38.5 Un Admin puede mostrar en el dashboard el ratio de fix-up tasks con complaint
	public Double getComplaintFixUpTasks() {

		return this.fixUpTaskRepository.getComplaintFixUpTasks();

	}

	// Tickers se generan automaticamente, con el pattern: YYMMDD-XXXXXX, X es
	// una letra en mayuscula

	public String getTicker() {
		String res = "";
		final Calendar c = Calendar.getInstance();

		String day = Integer.toString(c.get(Calendar.DATE));
		if (day.length() == 1)
			day = "0" + day;
		String month = Integer.toString(c.get(Calendar.MONTH) + 1); // +1 ya que Enero corresponde al valor 0

		if (month.length() == 1)
			month = "0" + month;
		String year = Integer.toString(c.get(Calendar.YEAR));

		// Por el patron solo necesitamos las ultimas 2 cifras del anho
		year = year.substring(2, 4);

		// Aqui obtengo las 4 letras mayusculas
		final Random r = new Random();
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		final int index1 = r.nextInt(alphabet.length());
		final int index2 = r.nextInt(alphabet.length());
		final int index3 = r.nextInt(alphabet.length());
		final int index4 = r.nextInt(alphabet.length());

		res = year + month + day + "-" + alphabet.charAt(index1) + alphabet.charAt(index2) + alphabet.charAt(index3) + alphabet.charAt(index4);

		// Compruebo que el ticker sea único
		// Si existe algún ticker igual, vuelvo a calcularlo

		for (final FixUpTask t : this.fixUpTaskRepository.findAll())
			if (t.getTicker().equals(res))
				res = this.getTicker();
		return res;
	}

	public Collection<FixUpTask> getFixUpTasksByCategory(final int categoryId) {

		return this.fixUpTaskRepository.getFixUpTasksByCategory(categoryId);
	}

	public Integer getAcceptedApplicationsByHandyWorkerId(final int handyWorkerId, final int fixUpTaskId) {

		return this.fixUpTaskRepository.getAcceptedApplicationsByHandyWorkerId(handyWorkerId, fixUpTaskId);
	}

	// C.11.2 Buscar fixUpTasks por palabra clave, que debe estar contenida en
	// su ticker, description o address

	public Collection<FixUpTask> getFixUpTasksByKeyWord(final String keyWord) {
		Assert.notNull(keyWord);

		Collection<FixUpTask> res;
		res = this.fixUpTaskRepository.getFixUpTasksByKeyWord(keyWord);
		return res;
	}

	// C.12.5 Un Administrador muestra un Dashboard con estas estadisticas: 

	//The average, the minimum, the maximum, and the standard deviation of the 
	//number of fix-up tasks per user.
	public Double[] computeAvgMinMaxStdvPerUser() {

		return this.fixUpTaskRepository.computeAvgMinMaxStdvPerUser();
	}

	//The average, the minimum, the maximum, and the standard deviation 
	//of the maximum price of the fix-up tasks.

	public Double[] computeAvgMinMaxStdvPerMaxPrice() {

		return this.fixUpTaskRepository.computeAvgMinMaxStdvPerMaxPrice();
	}

	public Collection<FixUpTask> getNotPublishedFixUpTaks() {
		return this.fixUpTaskRepository.getNotPublishedFixUpTaks();
	}

	public Collection<FixUpTask> getHandyWorkerApplicationFixUpTasks(final int handyWorkerId) {
		return this.fixUpTaskRepository.getHandyWorkerApplicationFixUpTasks(handyWorkerId);
	}

	public Collection<FixUpTask> getVisibleFixUpTasks() {
		return this.fixUpTaskRepository.getVisibleFixUpTasks();
	}

	public Collection<FixUpTask> getWithoutApplicationFixUpTasks(final int handyWorkerId) {
		final Collection<FixUpTask> handyWorkerFixUpTasks;
		Collection<FixUpTask> allFixUpTasks;

		handyWorkerFixUpTasks = this.fixUpTaskRepository.getHandyWorkerApplicationFixUpTasks(handyWorkerId);
		allFixUpTasks = this.fixUpTaskRepository.getVisibleFixUpTasks();

		allFixUpTasks.removeAll(handyWorkerFixUpTasks);

		return allFixUpTasks;
	}

	public Boolean checkFixUpTaskIsPublished(final FixUpTask fixUpTask) {
		Boolean isPublished = false;

		if (fixUpTask.getPublicationDate().before(new Date(System.currentTimeMillis())))
			isPublished = true;

		return isPublished;
	}

	public Collection<FixUpTask> getEndedFixUpTasks() {

		return this.fixUpTaskRepository.getEndedFixUpTasks();

	}
	public Collection<FixUpTask> getVisibleFixUpTasksByCategory(final int categoryId) {

		return this.fixUpTaskRepository.getVisibleFixUpTasksByCategory(categoryId);
	}

	public Collection<FixUpTask> showAllFixUpTaskByCategory(final int categoryId, final Collection<FixUpTask> result) {

		Category category;

		category = this.categoryService.findOne(categoryId);

		if (!category.getChildCategories().isEmpty())
			for (final Category c : category.getChildCategories())
				this.showAllFixUpTaskByCategory(c.getId(), result);

		//	result.addAll(this.getVisibleFixUpTasksByCategory(categoryId));

		return result;
	}

	public FixUpTask getFixUpTaskByApplicationId(final int applicationId) {
		return this.fixUpTaskRepository.getFixUpTaskByApplicationId(applicationId);
	}

	public Collection<FixUpTask> getFixUpTasksByCustomerId(final int customerId) {
		return this.fixUpTaskRepository.getFixUpTasksByCustomerId(customerId);
	}

	public Collection<FixUpTask> getFixUpTasksHandyWorkerApplication(final int handyWorkerId) {
		return this.fixUpTaskRepository.getFixUpTasksHandyWorkerApplication(handyWorkerId);
	}

	public void checkPrincipal(final FixUpTask f) {
		Customer c;

		c = (Customer) this.actorService.findByPrincipal();

		Assert.isTrue(c.getFixUpTasks().contains(f));
	}

	public FixUpTask saveFromCreate(final FixUpTask fixUpTask) {
		Assert.notNull(fixUpTask);
		Assert.isTrue(fixUpTask.getPublicationDate().after(new Date(System.currentTimeMillis() - 24 * 3600 * 1000l)), "message.error.publicationDate");
		Assert.isTrue(fixUpTask.getStartDate().after(new Date(System.currentTimeMillis())), "message.error.startDate");
		Assert.isTrue(fixUpTask.getEndDate().after(new Date(System.currentTimeMillis())), "message.error.endDate");
		Assert.isTrue(fixUpTask.getPublicationDate().before(fixUpTask.getStartDate()), "message.error.startDatePubliDate");
		Assert.isTrue(fixUpTask.getPublicationDate().before(fixUpTask.getEndDate()), "message.error.endDatePubliDate");
		Assert.isTrue(fixUpTask.getStartDate().before(fixUpTask.getEndDate()), "message.error.startDateEndDate");

		FixUpTask result;
		final Customer c;
		Collection<FixUpTask> fixUpTasks;

		c = (Customer) this.actorService.findByPrincipal();
		fixUpTasks = c.getFixUpTasks();

		result = this.fixUpTaskRepository.save(fixUpTask);
		fixUpTasks.add(result);
		c.setFixUpTasks(fixUpTasks);
		this.customerService.save(c);

		// Comprobamos si es spam
		this.administratorService.checkIsSpam(fixUpTask.getDescription());

		return result;
	}

}
