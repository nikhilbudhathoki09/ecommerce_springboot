package com.ecommerce.the_mesh.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.the_mesh.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{
    
    
}
