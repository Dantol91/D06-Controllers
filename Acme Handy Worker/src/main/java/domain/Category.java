
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(uniqueConstraints = {
	@UniqueConstraint(columnNames = {
		"nameEnglish", "parent_category"
	}), @UniqueConstraint(columnNames = {
		"nameSpanish", "parent_category"
	})
})
public class Category extends DomainEntity {

	// Constructors

	public Category() {
		super();
	}


	// Attributes 

	private String		nameEnglish;
	private String		nameSpanish;

	//Relationships

	private Category	parentCategory;


	@Valid
	@ManyToOne(optional = false)
	public Category getParentCategory() {
		return this.parentCategory;
	}

	@NotBlank
	@NotNull
	public String getNameEnglish() {
		return this.nameEnglish;
	}

	public void setNameEnglish(final String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	@NotBlank
	@NotNull
	public String getNameSpanish() {
		return this.nameSpanish;
	}

	public void setNameSpanish(final String nameSpanish) {
		this.nameSpanish = nameSpanish;
	}

	public void setParentCategory(final Category parentCategory) {
		this.parentCategory = parentCategory;
	}

}
