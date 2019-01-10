
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Ticker extends DomainEntity {

	// Constructors

	public Ticker() {
		super();
	}


	// Attributes 

	private String	ticker;


	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^\\d{6}-([A-Z]|\\d){6}$")
	@NotNull
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

}
