
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Category;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class CategoryServiceTest extends AbstractTest {

	// Service under test 
	@Autowired
	private CategoryService	categoryService;


	// Test 

	@Test
	public void testCreateCategory() {
		super.authenticate("Customer1");

		Category category;

		category = this.categoryService.create();

		Assert.notNull(category);
		Assert.isNull(category.getParentCategory());

		super.unauthenticate();

		System.out.println("CategoryCreate: " + category);
	}

	// Test negativo: category = null 
	@Test(expected = IllegalArgumentException.class)
	public void TestSaveCategory() {
		super.authenticate("administrator1");

		Category category, saved;
		final Collection<Category> allCategories;

		category = null;

		saved = this.categoryService.save(category);

		allCategories = this.categoryService.findAll();

		Assert.isTrue(!allCategories.contains(saved));

		super.unauthenticate();
	}

	// Test negativo: categoria sin padre
	@Test(expected = IllegalArgumentException.class)
	public void negativeTestSave_dos() {
		super.authenticate("administrator1");

		Category category, saved;
		Collection<Category> allCategories;

		category = this.categoryService.create();
		saved = this.categoryService.save(category);

		allCategories = this.categoryService.findAll();

		Assert.isTrue(!allCategories.contains(saved));

		super.unauthenticate();
	}

}
