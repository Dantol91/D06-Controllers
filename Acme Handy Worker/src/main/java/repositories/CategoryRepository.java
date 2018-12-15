
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

	@Query("select c from Category c where c.parentCategory.id=?1 group by c.id")
	Collection<Category> getCategoriesbByParentCategory(int id);

	//Con esta consulta selecciono las categorías que son categoría padre	
	@Query("select c from Category c where c.parentCategory.name = 'CATEGORY'")
	Collection<Category> getParentCategories();

	@Query("select c from Category c where c.parentCategory.id= ?1")
	Collection<Category> getChildCategories(int parentCategoryId);

	@Query("select c.parentCategory from Category c")
	Collection<Category> getParentCategoriesByChildCategoryName(String childCategoryName);

	@Query("select c from Category c join c.childCategories children where children.id = ?1")
	Category getParent(int childCategoryId);

	@Query("select c from Category c where c.name != 'CATEGORY'")
	Collection<Category> getAllCategories();

}
