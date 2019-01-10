
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Embeddable
@Access(AccessType.PROPERTY)
public class Url {

	// Attributes

	private String	url;


	@URL
	@NotBlank
	@NotNull
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}

	@Override
	public boolean equals(final Object o) {
		boolean res = false;
		if (o instanceof Url) {
			final Url u = (Url) o;
			res = u.getUrl().equals(this.getUrl());
		}
		return res;
	}

}
