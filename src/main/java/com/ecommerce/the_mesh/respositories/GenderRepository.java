package com.ecommerce.the_mesh.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.ecommerce.the_mesh.entities.Gender;


public interface GenderRepository extends JpaRepository<Gender, Integer> {

    @Query(value = "SELECT * from gender g WHERE g.gender_name = :name", nativeQuery = true)
    public Gender getByGenderName(@Param("name") String name);


    @Query(value = "SELECT * FROM gender g WHERE g.gender_name<> :name", nativeQuery = true)
    public List<Gender> allGenderExceptFor(@Param("name") String name);
    
}
