
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.UserAccount;
import domain.Complaint;
import domain.Note;
import domain.Referee;
import domain.Url;

@Service
@Transactional
public class RefereeService {

	// Repository

	@Autowired
	private RefereeRepository	repository;

	// Services

	@Autowired
	private ActorService		actorService;
	@Autowired
	private FolderService		folderService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private NoteService			noteService;
	@Autowired
	private ReportService		reportService;
	@Autowired
	private ServiceUtils		serviceUtils;


	// CRUD methods

	public Referee findOne(final Integer id) {
		this.serviceUtils.checkId(id);
		return this.repository.findOne(id);
	}

	public Collection<Referee> findAll(final Collection<Integer> ids) {
		this.serviceUtils.checkIds(ids);
		return this.repository.findAll(ids);
	}

	public Collection<Referee> findAll() {
		return this.repository.findAll();
	}

	public Referee create() {

		final Referee res = new Referee();
		res.setUserAccount(new UserAccount());
		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		res.getUserAccount().addAuthority(authority);
		res.getUserAccount().setBanned(false);
		res.setSuspicious(false);
		return res;
	}

	public Referee save(final Referee object) {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final Referee referee = (Referee) this.serviceUtils.checkObjectSave(object);
		Boolean isCreating = null;
		if (object.getId() == 0) {
			isCreating = true;
			object.getUserAccount().setBanned(false);
			object.getUserAccount().setPassword(encoder.encodePassword(object.getUserAccount().getPassword(), null));
			object.setSuspicious(false);
			this.serviceUtils.checkAuthority(Authority.ADMIN);
		} else {
			isCreating = false;
			referee.setAddress(object.getAddress());
			referee.setEmail(object.getEmail());
			referee.setMiddleName(object.getMiddleName());
			referee.setName(object.getName());
			referee.setPhone(object.getPhone());
			referee.setPhoto(object.getPhoto());
			referee.setSurname(object.getSurname());
			referee.setUserAccount(object.getUserAccount());
			referee.getUserAccount().setPassword(encoder.encodePassword(object.getUserAccount().getPassword(), null));
			this.serviceUtils.checkActor(referee);
			this.serviceUtils.checkAuthority(Authority.REFEREE);
		}
		final Referee res = this.repository.save(object);
		this.flush();
		if (isCreating)
			this.folderService.createSystemFolders(res);
		return res;
	}
	// Other methods

	public void changeBanned(final Referee referee) {
		final Referee ref = (Referee) this.serviceUtils.checkObject(referee);
		if (this.isSuspicious(ref) && !ref.getUserAccount().getBanned())
			ref.getUserAccount().setBanned(true);
		else
			ref.getUserAccount().setBanned(false);
		this.serviceUtils.checkAuthority(Authority.ADMIN);
		this.repository.save(ref);
	}

	public boolean isSuspicious(final Referee r) {
		boolean res = false;
		Assert.notNull(r);
		this.serviceUtils.checkId(r.getId());
		final Referee referee = this.repository.findOne(r.getId());
		Assert.notNull(referee);
		res = this.actorService.isSuspicious(referee);
		if (!res)
			for (final Complaint c : this.complaintService.findAllComplaintsByReferee(referee)) {
				res = this.actorService.containsSpam(c.getDescription()) || this.actorService.containsSpam(this.reportService.findByComplaint(c).getDescription());
				if (!res)
					for (final Note n : this.noteService.findAllByReport(this.reportService.findByComplaint(c))) {
						for (final String comment : n.getComments()) {
							res = this.actorService.containsSpam(comment);
							if (res)
								break;
						}
						if (res)
							break;
					}
				if (!res)
					for (final Url u : c.getAttachments()) {
						res = this.actorService.containsSpam(u.getUrl());
						if (res)
							break;
					}
				if (res)
					break;
			}
		return res;
	}

	public void banActor(final Referee r) {
		Assert.notNull(r);
		this.serviceUtils.checkAuthority("ADMIN");
		r.getUserAccount().setBanned(true);
		this.repository.save(r);

	}

	public void unbanActor(final Referee r) {
		Assert.notNull(r);
		this.serviceUtils.checkAuthority("ADMIN");
		r.getUserAccount().setBanned(false);
		this.repository.save(r);

	}

	public void flush() {
		this.repository.flush();
	}

}
