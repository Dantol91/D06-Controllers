
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import domain.Category;
import domain.FixUpTask;

@Service
@Transactional
public class CategoryService {

	// Managed repository
	@Autowired
	private CategoryRepository	categoryRepository;

	// Supporting services

	@Autowired
	private FixUpTaskService	fixUpTaskService;


	// Constructor
	public CategoryService() {
		super();
	}

	// Simple CRUD methods

	public Category create() {
		Category c;
		Collection<Category> childCategories;

		c = new Category();
		childCategories = new ArrayList<>();
		c.setChildCategories(childCategories);

		return c;
	}

	public Collection<Category> findAll() {
		final Collection<Category> result = this.categoryRepository.findAll();
		Assert.notNull(result);
		return result;

	}

	public Category findOne(final int categoryId) {
		final Category result = this.categoryRepository.findOne(categoryId);
		return result;
	}

	public Category save(final Category category) {
		Assert.notNull(category);

		// Se comprueba que no hay dos categorías con la misma parentCategory y
		// mismo nombre
		if (category.getId() != 0) {

			Collection<Category> categories;

			if (category.getParentCategory() != null) {

				//Comprobamos que una categoría no es padre/hija de sí misma
				Assert.isTrue(!category.getParentCategory().equals(category), "message.error.parentCategory");

				categories = this.categoryRepository.getCategoriesbByParentCategory(category.getParentCategory().getId());
			} else
				categories = this.categoryRepository.getParentCategories();
			for (final Category c : categories)
				if (c.getId() != category.getId())
					Assert.isTrue(!c.getName().equals(category.getName()), "message.error.childCategories");

		}

		//Se actualiza el padre
		final Collection<Category> aux = category.getParentCategory().getChildCategories();
		aux.add(category);
		category.getParentCategory().setChildCategories(aux);

		//Si estamos poniendo al padre como hijo de uno de sus hijos,
		// lo ponemos en el nivel en el que estaba el padre

		final Category parentAux = this.getParent(category.getId());
		category.getParentCategory().setParentCategory(parentAux);

		final Category parentAuxsaved = this.categoryRepository.save(category.getParentCategory());
		category.setParentCategory(parentAuxsaved);

		//Se actualizan las categorias hijas

		final Collection<Category> childAux = new ArrayList<>();
		final Category childSaved;

		for (final Category c : category.getChildCategories())
			c.setParentCategory(category);

		category.setChildCategories(childAux);

		final Category result = this.categoryRepository.save(category);
		return result;
	}

	public void delete(final Category category) {
		Assert.notNull(category);
		Assert.isTrue(category.getId() != 0);
		final Collection<FixUpTask> fixUpTasks;
		Collection<Category> children;

		children = category.getChildCategories();

		if (!children.isEmpty()) {
			final Iterator<Category> iter = children.iterator();
			while (iter.hasNext()) {

				final Category c = iter.next();
				this.delete(c);
				iter.remove();
			}

		}

		fixUpTasks = this.fixUpTaskService.getFixUpTasksByCategory(category.getId());

		// Al borrar una categoría se asigna a las fixUpTask que tenían la categoría padre
		// que en el peor de los casos será CATEGORY (equivalente a que la fixUpTask no tiene categoría)

		if (!fixUpTasks.isEmpty())
			for (final FixUpTask f : fixUpTasks)
				//			parent = this.getParent(f.getCategories().getId());
				//			f.setCategories(parent);
				this.fixUpTaskService.save(f);

		this.categoryRepository.delete(category);

	}

	//Other business methods

	public Collection<Category> getCategoriesbByParentCategory(final int id) {
		return this.categoryRepository.getCategoriesbByParentCategory(id);
	}

	public Collection<Category> getParentCategories() {
		return this.categoryRepository.getParentCategories();
	}

	public Collection<Category> getChildCategories(final Integer parentCategoryId) {
		return this.categoryRepository.getChildCategories(parentCategoryId);
	}

	public Category getParent(final int childCategoryId) {
		return this.categoryRepository.getParent(childCategoryId);
	}

	public Collection<Category> getAllCategories() {
		return this.categoryRepository.getAllCategories();
	}
}
