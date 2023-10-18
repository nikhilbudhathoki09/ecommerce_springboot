package com.ecommerce.the_mesh.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.the_mesh.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user u WHERE u.email = :email AND u.password = :password", nativeQuery = true)
    public Optional<User> checkVerifiedUser(@Param("email") String email, @Param("password") String password);

    
    
    
}
