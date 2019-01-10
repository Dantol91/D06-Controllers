
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdministratorRepository;
import domain.Administrator;

@Service
@Transactional
public class AdministratorService {

	// Managed repository 

	@Autowired
	private AdministratorRepository	administratorRepository;

	//Supporting Services

	@Autowired
	private FolderService			folderService;

	@Autowired
	private ServiceUtils			serviceUtils;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ActorService			actorService;


	//Constructor

	public AdministratorService() {
		super();
	}

	// CRUD methods

	public Administrator create() {
		Administrator result;
		result = new Administrator();
		result.setUserAccount(this.userAccountService.create("ADMIN"));
		result.getUserAccount().setBanned(false);
		result.setSuspicious(false);

		return result;

	}

	public Administrator save(final Administrator administrator) {

		Assert.notNull(administrator);
		Boolean isCreating = null;

		if (administrator.getId() == 0) {
			isCreating = true;
			administrator.setSuspicious(false);

		} else {
			isCreating = false;

			this.serviceUtils.checkIdSave(administrator);

			Administrator adminDB;
			Assert.isTrue(administrator.getId() > 0);

			adminDB = this.administratorRepository.findOne(administrator.getId());

			administrator.setSuspicious(adminDB.getSuspicious());
			administrator.setUserAccount(adminDB.getUserAccount());

			this.serviceUtils.checkAuthority("ADMIN");

			this.serviceUtils.checkActor(administrator);

		}
		Administrator res;

		res = this.administratorRepository.save(administrator);
		this.flush();
		if (isCreating)
			this.folderService.createSystemFolders(res);
		return res;
	}

	public Administrator findOne(final int administratorId) {
		return this.administratorRepository.findOne(administratorId);
	}

	public Collection<Administrator> findAll() {
		Collection<Administrator> res;
		res = this.administratorRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	// other business methods 

	public void flush() {
		this.administratorRepository.flush();
	}

	public void banActor(final Administrator a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.getUserAccount().setBanned(true);
		this.administratorRepository.save(a);

	}

	public void unBanActor(final Administrator a) {
		Assert.notNull(a);
		this.serviceUtils.checkAuthority("ADMIN");
		a.getUserAccount().setBanned(false);
		this.administratorRepository.save(a);

	}

}
