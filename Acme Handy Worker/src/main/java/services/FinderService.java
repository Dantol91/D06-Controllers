
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Finder;
import domain.FixUpTask;
import domain.HandyWorker;

@Service
@Transactional
public class FinderService {

	//M anaged repository
	@Autowired
	private FinderRepository		finderRepository;

	// Supporting services

	@Autowired
	private ActorService			actorService;
	@Autowired
	private ConfigurationService	configurationService;


	// Constructor

	public FinderService() {
		super();
	}

	// Simple CRUD methods

	public Finder create() {

		final Finder finder = new Finder();
		return finder;

	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(final int finderId) {
		Finder result;

		result = this.finderRepository.findOne(finderId);

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		Finder result;

		finder.setEndDate(new Date(System.currentTimeMillis()));

		result = this.finderRepository.save(finder);
		return result;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);

		this.finderRepository.delete(finder);
	}

	// Other Bussines Methods 

	public Finder getFinderByHandyWorkerId(final int handyWorkerId) {
		return this.finderRepository.getFinderByHandyWorkerId(handyWorkerId);
	}

	public Boolean checkSearchIsInCache(final Finder finder) {

		final HandyWorker h;
		final Finder lastFinder;
		Long passedTime;
		Long diffHours;
		Boolean isInCache = true;
		final Date now = new Date(System.currentTimeMillis());
		final Long nowMS = now.getTime();
		final Date search = finder.getEndDate();
		final Long searchMS = search.getTime();

		//Calculamos cuanto ha pasado desde la última búsqueda

		h = (HandyWorker) this.actorService.findByPrincipal();
		lastFinder = this.finderRepository.getFinderByHandyWorkerId(h.getId());
		passedTime = (nowMS - searchMS) / 1000; //Pasamos a segundos

		diffHours = passedTime / 3600; //Pasamos a horas

		if ((finder.getEndDate() != null && !finder.getEndDate().equals(lastFinder.getEndDate())) || (finder.getStartDate() != null && !finder.getStartDate().equals(lastFinder.getStartDate()))
			|| (finder.getKeyword() != null && !finder.getKeyword().equals(lastFinder.getKeyword())) || (finder.getMaxPrice() != null && !finder.getMaxPrice().equals(lastFinder.getMaxPrice()))
			|| (finder.getMinPrice() != null && !finder.getMinPrice().equals(lastFinder.getMinPrice())) || diffHours > this.configurationService.getConfiguration().getFinderCached())
			isInCache = false;

		return isInCache;

	}

	//	public Finder transformFinder(Finder finder){
	//		
	//		if(finder.getMinPrice()==null){
	//			finder.setMinPrice(0.);
	//		}
	//		
	//		if(finder.getMaxPrice()==null){
	//			finder.setMPrice(999999.);
	//		}
	//		
	//		if(finder.getStartDate()==null){
	//			Date startDate = new Date();
	//			String startDateString = "06/06/1000";
	//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	//			try {
	//			    startDate = df.parse(startDateString);
	//			    finder.setStartDate(startDate);
	//			} catch (ParseException e) {
	//			    e.printStackTrace();
	//			}
	//		}
	//		
	//		if(finder.getEndDate()==null){
	//			Date endDate = new Date();
	//			String endDateString = "06/06/3000";
	//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	//			try {
	//			    endDate = df.parse(endDateString);
	//			    finder.setEndDate(endDate);
	//			} catch (ParseException e) {
	//			    e.printStackTrace();
	//			}
	//		}
	//		
	//		if(finder.getKeyWord()==null){
	//			finder.setKeyWord("");
	//		}
	//		
	//		return finder;
	//	}

	public void saveNewResults(final Finder finder, final Collection<FixUpTask> fixUpTasks) {
		finder.setFixUpTasks(fixUpTasks);
	}

}
