package com.ecommerce.the_mesh.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.the_mesh.entities.Product;

@Repository
public interface ProductRepository  extends JpaRepository <Product, Integer>{

    @Query(value = "SELECT * from product p WHERE p.gender_id = 1", nativeQuery = true)
    public List<Product> getMenProducts();

    @Query(value = "SELECT * from product p WHERE p.gender_id = 2", nativeQuery = true)
    public List<Product> getWomenProducts();

    @Query("SELECT p FROM Product p WHERE p.brand.brand_name LIKE %:search% OR p.product_name LIKE %:search%")
    public List<Product> findProductsByNameOrBrand(@Param("search") String search);
    

    


    
}
