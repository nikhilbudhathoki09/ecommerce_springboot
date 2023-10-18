package com.ecommerce.the_mesh.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.the_mesh.entities.Order;
import com.ecommerce.the_mesh.respositories.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;


    public Order addOrder(Order order){
        Order savedOrder = this.orderRepo.save(order);
        return savedOrder;
    }

    public List<Order> getAllOrders(){
        List<Order> allOrders = this.orderRepo.findAll();
        return allOrders;
    }


    
}
