
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CategoryRepository;
import security.Authority;
import domain.Category;
import domain.FixupTask;

@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryRepository	categoryRepository;

	@Autowired
	private FixupTaskService	fixUpTaskService;
	@Autowired
	private ServiceUtils		serviceUtils;


	//Constructor

	public CategoryService() {
		super();
	}
	// CRUD methods

	public Category create() {
		final Category c = new Category();
		return c;
	}

	public Category findOne(final int categoryId) {
		return this.categoryRepository.findOne(categoryId);
	}

	public Collection<Category> findAll() {
		Collection<Category> c;
		c = this.categoryRepository.findAll();
		Assert.notNull(c);

		return c;
	}

	public Category save(final Category category) {
		Assert.notNull(category);
		System.out.println("Entra en el save");
		final Category padre = category.getParentCategory();

		if (!category.getNameEnglish().equals("CATEGORY")) {
			System.out.println("Entra en el if");

			return this.categoryRepository.save(category);
		} else {
			System.out.println("Entra en el else");
			throw new IllegalArgumentException("Incompatibilidad de recursividad en el guardado");
		}
	}
	public Boolean tieneHijas(final Category c) {
		final Boolean res = true;
		if (this.findByParent(c).isEmpty())
			return false;
		return res;

	}

	public void changeFixupTaskCategory(final Category cat) {
		final Collection<FixupTask> res = this.fixUpTaskService.findAll();
		for (final FixupTask f : res)
			if (f.getCategory().equals(cat)) {
				final Category padre = cat.getParentCategory();
				f.setCategory(padre);
				this.fixUpTaskService.save(f);
			}
	}

	public void delete(final Category cat) {
		Assert.notNull(cat);
		Assert.isTrue(this.categoryRepository.exists(cat.getId()));
		if (cat.getNameEnglish().equals("CATEGORY")) {
			System.out.println("Pasa por el primer if del delete");
			throw new IllegalArgumentException("NO SE PUEDE BORRAR LA CATEGORIA RAIZ");

		}
		if (this.tieneHijas(cat) == true) {
			for (final Category hija : this.findByParent(cat))
				//this.delete(hija);
				this.heredaAbuelo(hija);
			this.changeFixupTaskCategory(cat);
			this.categoryRepository.delete(cat);
		}

		else {
			this.changeFixupTaskCategory(cat);
			this.categoryRepository.delete(cat);
		}

	}

	public void heredaAbuelo(final Category c) {
		final Category padre = c.getParentCategory();
		final Category abuelo = padre.getParentCategory();

		c.setParentCategory(abuelo);
	}

	public Collection<Category> findByParent(final Category parent) {
		Assert.notNull(parent);
		Assert.isTrue(parent.getId() > 0);
		Assert.notNull(this.categoryRepository.findOne(parent.getId()));
		return this.categoryRepository.findByParentId(parent.getId());
	}

	public Collection<Category> findAll(final Category dependency) {
		return this.findByParent(dependency);
	}

	public Category create(final Category dependency) {
		this.serviceUtils.checkObject(dependency);
		this.serviceUtils.checkPermisionActor(null, new String[] {
			Authority.ADMIN
		});
		final Category res = new Category();
		res.setParentCategory(dependency);
		return res;
	}

}
