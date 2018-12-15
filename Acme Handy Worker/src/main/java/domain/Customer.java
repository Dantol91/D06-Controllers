
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {

	// Constructors

	public Customer() {
		super();
	}


	// Attributes

	private double	score;


	@Digits(integer = 3, fraction = 2)
	@Range(min = -1, max = 1)
	public double getScore() {
		return this.score;
	}

	public void setScore(final double score) {
		this.score = score;
	}


	// Relationships

	private Collection<FixUpTask>	fixUpTasks;


	@Valid
	@OneToMany(mappedBy = "customer")
	public Collection<FixUpTask> getFixUpTasks() {
		return this.fixUpTasks;
	}

	public void setFixUpTasks(final Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

}
