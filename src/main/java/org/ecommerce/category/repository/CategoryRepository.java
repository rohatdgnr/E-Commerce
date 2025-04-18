package org.ecommerce.category.repository;

import org.ecommerce.category.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String category);

    boolean existsByName(String name);
}
