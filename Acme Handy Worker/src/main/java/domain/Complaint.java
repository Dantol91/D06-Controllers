
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Complaint extends Ticker {

	// Constructors

	public Complaint() {
		super();
	}


	// Attributes 

	private Date			moment;
	private String			description;
	private Collection<Url>	attachments;

	// Relationships

	private FixupTask		fixuptask;
	private Referee			referee;


	@Valid
	@ManyToOne(optional = false)
	public FixupTask getFixuptask() {
		return this.fixuptask;
	}

	public void setFixuptask(final FixupTask fixuptask) {
		this.fixuptask = fixuptask;
	}

	@Past
	@NotNull
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@NotNull
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	@ElementCollection
	public Collection<Url> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<Url> attachments) {
		this.attachments = attachments;
	}

	@Valid
	@ManyToOne(optional = true)
	public Referee getReferee() {
		return this.referee;
	}

	public void setReferee(final Referee referee) {
		this.referee = referee;
	}

}
