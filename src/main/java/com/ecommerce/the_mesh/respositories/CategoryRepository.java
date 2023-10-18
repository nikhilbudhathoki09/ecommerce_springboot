package com.ecommerce.the_mesh.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.the_mesh.entities.Category;

public interface CategoryRepository extends  JpaRepository<Category, Integer> {

    @Query(value = "SELECT * from categories c WHERE c.category_name =:name ",nativeQuery = true)
    public Category getByCategoryName(@Param("name") String name);

    @Query(value = "SELECT * from categories c WHERE c.category_name<>:name", nativeQuery = true)
    public List<Category> allCategoryExceptFor(@Param("name") String name);
    
}


