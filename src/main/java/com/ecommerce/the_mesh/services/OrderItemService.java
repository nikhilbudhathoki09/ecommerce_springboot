package com.ecommerce.the_mesh.services;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.the_mesh.entities.OrderItems;
import com.ecommerce.the_mesh.exception.ApiRequestException;
import com.ecommerce.the_mesh.respositories.OrderItemRepository;
import com.ecommerce.the_mesh.respositories.OrderRepository;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepo;
    private final OrderRepository orderRepo;

    public OrderItemService(OrderItemRepository orderItemRepo, OrderRepository orderRepo){
        this.orderItemRepo = orderItemRepo;
        this.orderRepo = orderRepo;
    }

    public OrderItems saveOrderItems(OrderItems orderItem){
        OrderItems registeredOrder = this.orderItemRepo.save(orderItem);
        return registeredOrder;
    }

    public Optional<OrderItems> getOrderItemsById(int id){
        Optional<OrderItems> orderItem = this.orderItemRepo.findById(id);

        if(orderItem.isPresent()){
            return orderItem;
        }else{
            throw new ApiRequestException("orderItem is not found");
        }
    }

    public List<OrderItems> getCartItems(int user_id){
        List<OrderItems> orderItems = this.orderItemRepo.getCarts(user_id);
        return orderItems;
    }

    public List<OrderItems> getOrderedItems(int user_id){
        List<OrderItems> orderedItems = this.orderItemRepo.getOrderedItems(user_id);
        return orderedItems;
    }

    public List<OrderItems> getOrderedItems(){
        List<OrderItems> orderedItems = this.orderItemRepo.getOrderedItems();
        return orderedItems;
    }

    public void removeCartProduct(int order_item_id){
        OrderItems orderItem = getOrderItemsById(order_item_id).get();
        this.orderItemRepo.delete(orderItem);
    }

    public OrderItems checkCartItems(int user_id, int product_id){
        OrderItems orderItems = this.orderItemRepo.checkCartItems(user_id, product_id);
        System.out.println(orderItems);
        return orderItems;   
    }

    public void decreaseQuantity(int order_item_id) {
        OrderItems orderItem = getOrderItemsById(order_item_id).get();

        if (orderItem != null) {
            // Decrease the quantity with a minimum limit of 1
            int newQuantity = Math.max(orderItem.getQuantity() - 1, 1);
            orderItem.setQuantity(newQuantity);
            this.orderItemRepo.save(orderItem);
        }
    }

    public void increaseQuantity(int order_item_id ){
        OrderItems orderItem =getOrderItemsById(order_item_id).get();

        if (orderItem != null) {
            // Increase the quantity with 1
            int newQuantity = orderItem.getQuantity()+1;
            orderItem.setQuantity(newQuantity);
            // Update the order item in the database
            this.orderItemRepo.save(orderItem);
        }
    }

    public double getTotalPrice(int user_id){
        double totalPrice = 0;
        List<OrderItems> orderItems = orderItemRepo.getCarts(user_id);

        for (OrderItems item : orderItems) {
            totalPrice += item.getPrice() *item.getQuantity();
            
        }
        return totalPrice;
    }

    public void moveToOrder(int user_id, int order_id){
        List<OrderItems> orderItems = getCartItems(user_id);
        for (OrderItems item: orderItems) {
            item.setOrder(orderRepo.findById(order_id).get());   
            this.orderItemRepo.save(item);
        }
    }


    
}
