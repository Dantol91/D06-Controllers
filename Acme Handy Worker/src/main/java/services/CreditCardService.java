
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CreditCardRepository;
import domain.CreditCard;

@Service
@Transactional
public class CreditCardService {

	//Managed repository
	@Autowired
	private CreditCardRepository	creditCardRepository;


	//Constructor
	public CreditCardService() {
		super();
	}

	//Simple CRUD methods

	public CreditCard create() {
		CreditCard res;

		res = new CreditCard();

		return res;
	}

	public CreditCard save(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		//	Assert.isTrue(checkExpiration(creditCard), "message.error.expiration");

		CreditCard result;

		result = this.creditCardRepository.save(creditCard);

		return result;
	}

	public void delete(final CreditCard creditCard) {
		Assert.notNull(creditCard);

		this.creditCardRepository.delete(creditCard);
	}

	public Collection<CreditCard> findAll() {

		Collection<CreditCard> result;

		result = this.creditCardRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public CreditCard findOne(final int creditCardId) {
		CreditCard result;

		result = this.creditCardRepository.findOne(creditCardId);

		return result;
	}

	//Other business methods

	/*
	 * public boolean checkExpiration(CreditCard c){
	 * Boolean res = true;
	 * 
	 * if((c.getExpirationYear()==LocalDate.now().getYear()
	 * && (c.getExpirationMonth() == LocalDate.now().getMonthOfYear()
	 * || c.getExpirationMonth() < LocalDate.now().getMonthOfYear()))
	 * || c.getExpirationYear()<LocalDate.now().getYear()){
	 * res = false;
	 * }
	 * 
	 * return res;
	 * }
	 */
}
