
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.CreditCard;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CreditCardServiceTest extends AbstractTest {

	// Service under test 
	@Autowired
	private CreditCardService	creditCardService;


	@Test
	public void createSaveAndDeleteCreditCard() {
		CreditCard cdc, cdcsaved;
		Collection<CreditCard> cdcBefore = new ArrayList<>();
		Collection<CreditCard> cdcAfter = new ArrayList<>();

		cdc = this.creditCardService.create();
		Assert.notNull(cdc);

		cdc.setBrandName("brandName1");
		cdc.setCVV(123);
		cdc.setExpirationMonth("9");
		cdc.setExpirationYear("2019");
		cdc.setHolderName("holderName1");
		cdc.setNumber("5310536263555044");

		cdcsaved = this.creditCardService.save(cdc);
		Assert.notNull(cdcsaved);

		cdcBefore = this.creditCardService.findAll();
		Assert.isTrue(cdcBefore.contains(cdcsaved));

		this.creditCardService.delete(cdcsaved);

		cdcAfter = this.creditCardService.findAll();
		Assert.isTrue(!cdcAfter.contains(cdcsaved));

		System.out.println("Tarjeta de Crédito Create: " + this.creditCardService.create());
		System.out.println("Tarjeta de Crédito Save: " + this.creditCardService.save(cdc));
		System.out.println("Tarjeta de Crédito FindAll: " + this.creditCardService.findAll());
	}

}
