
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class SocialProfile extends DomainEntity {

	// Constructors

	public SocialProfile() {
		super();
	}


	// Attributes 

	private String	nick;
	private String	networkName;
	private String	profile;

	// Relationships

	private Actor	actor;


	@NotBlank
	@NotNull
	public String getNick() {
		return this.nick;
	}

	public void setNick(final String nick) {
		this.nick = nick;
	}

	@NotBlank
	@NotNull
	public String getNetworkName() {
		return this.networkName;
	}

	public void setNetworkName(final String networkName) {
		this.networkName = networkName;
	}

	@URL
	@NotNull
	public String getProfile() {
		return this.profile;
	}

	public void setProfile(final String profile) {
		this.profile = profile;
	}

	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
