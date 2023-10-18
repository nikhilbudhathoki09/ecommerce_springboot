package com.ecommerce.the_mesh.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.the_mesh.entities.Brand;

public interface BrandRepository  extends JpaRepository<Brand, Integer>{
    
    @Query(value = "SELECT * from brand b WHERE b.brand_name =:name", nativeQuery = true)
    public Brand getByBrandName(@Param("name") String name);


    @Query(value = "SELECT * from brand b WHERE b.brand_name<>:name", nativeQuery = true)
    public List<Brand> allBrandExceptFor(@Param("name") String name);
    
}
