
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Box extends DomainEntity {

	// Constructors

	public Box() {
		super();
	}


	// Attributes 

	private String	name;
	private boolean	systemBox;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotNull
	public boolean getSystemBox() {
		return this.systemBox;
	}

	public void setSystemBox(final boolean systemBox) {
		this.systemBox = systemBox;
	}


	// Relationships

	private Collection<Message>		messages;
	private Box						parentBox;
	private Collection<Category>	childBoxes;
	private Actor					actor;


	@NotNull
	@Valid
	@ManyToMany
	public Collection<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(final Collection<Message> messages) {
		this.messages = messages;
	}

	@OneToOne(optional = true)
	public Box getParentBox() {
		return this.parentBox;
	}

	public void setParentBox(final Box parentBox) {
		this.parentBox = parentBox;
	}

	@OneToMany
	public Collection<Category> getChildBoxes() {
		return this.childBoxes;
	}

	public void setChildBoxes(final Collection<Category> childBoxes) {
		this.childBoxes = childBoxes;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
