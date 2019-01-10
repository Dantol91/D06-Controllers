
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Endorsable {

	// Constructors

	public Customer() {
		super();
	}


	// Attributes 

	// Relationships

	private Collection<FixupTask>	fixupTasks;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<FixupTask> getFixupTasks() {
		return this.fixupTasks;
	}
	public void setFixupTasks(final Collection<FixupTask> fixupTasks) {
		this.fixupTasks = fixupTasks;
	}

}
