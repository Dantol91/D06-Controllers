
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed Repository

	@Autowired
	private SponsorRepository	sponsorRepository;

	// Supporting Service
	@Autowired
	private FolderService		folderService;
	@Autowired
	private UserAccountService	uAService;
	@Autowired
	private ServiceUtils		serviceUtils;


	// Simple CRUD methods

	public Sponsor create() {
		Sponsor e;
		e = new Sponsor();
		e.setUserAccount(new UserAccount());
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		e.getUserAccount().addAuthority(authority);
		e.setSuspicious(false);
		e.getUserAccount().setBanned(false);
		return e;
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int sponsorId) {
		return this.sponsorRepository.findOne(sponsorId);
	}

	public Sponsor save(final Sponsor sponsor) {
		/*
		 * Assert.notNull(e);
		 * if (e.getId() == 0) {
		 * this.folderService.createSystemFolders(e);
		 * e.setSuspicious(false);
		 * e.setUserAccount(this.uAService.create("SPONSOR"));
		 * 
		 * }
		 * return this.sponsorRepository.save(e);
		 */

		//comprobamos que el sponsor que nos pasan no sea nulo
		Assert.notNull(sponsor);
		Boolean isCreating = null;

		if (sponsor.getId() == 0) {
			isCreating = true;
			sponsor.setSuspicious(false);

			//comprobamos que ningún actor resté autenticado (ya que ningun actor puede crear los sponsors)
			//this.serviceUtils.checkNoActor();

		} else {
			isCreating = false;
			//comprobamos que su id no sea negativa por motivos de seguridad
			this.serviceUtils.checkIdSave(sponsor);

			//este sponsor será el que está en la base de datos para usarlo si estamos ante un sponsor que ya existe
			Sponsor sponsorBD;
			Assert.isTrue(sponsor.getId() > 0);

			//cogemos el sponsor de la base de datos
			sponsorBD = this.sponsorRepository.findOne(sponsor.getId());

			//Si el sponsor que estamos guardando es nuevo (no está en la base de datos) le ponemos todos sus atributos vacíos

			sponsor.setSuspicious(sponsorBD.getSuspicious());
			sponsor.setUserAccount(sponsorBD.getUserAccount());

			//Comprobamos que el actor sea un Sponsor
			this.serviceUtils.checkAuthority("SPONSOR");
			//esto es para ver si el actor que está logueado es el mismo que se está editando
			this.serviceUtils.checkActor(sponsor);

		}
		Sponsor res;
		//le meto al resultado final el sponsor que he ido modificando anteriormente
		res = this.sponsorRepository.save(sponsor);
		this.flush();
		if (isCreating)
			this.folderService.createSystemFolders(res);
		return res;
	}

	public void delete(final Sponsor s) {
		Assert.notNull(s);
		s.getUserAccount().setBanned(true);
	}

	public void banActor(final Sponsor s) {
		Assert.notNull(s);
		this.serviceUtils.checkAuthority("ADMIN");
		s.getUserAccount().setBanned(true);
		this.sponsorRepository.save(s);
	}

	public void unbanActor(final Sponsor s) {
		Assert.notNull(s);
		this.serviceUtils.checkAuthority("ADMIN");
		s.getUserAccount().setBanned(false);
		this.sponsorRepository.save(s);
	}

	public Sponsor findSponsorById(final int id) {
		return this.sponsorRepository.findSponsorById(id);
	}

	public void flush() {
		this.sponsorRepository.flush();
	}

}
