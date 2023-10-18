package com.ecommerce.the_mesh.respositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ecommerce.the_mesh.entities.OrderItems;

public interface OrderItemRepository extends JpaRepository<OrderItems , Integer> {


    @Query(value = "SELECT * from order_items oi WHERE oi.user_id =:id AND oi.order_id Is NULL", nativeQuery = true)
    public List<OrderItems> getCarts(@Param("id") int id);


    @Query(value = "SELECT * from order_items oi WHERE oi.user_id =:id AND oi.product_id =:product_id AND oi.order_id Is NULL", nativeQuery = true)
    public OrderItems checkCartItems(@Param("id") int user_id, @Param("product_id") int product_id);

    @Query(value = "SELECT * FROM order_items oi WHERE oi.user_id = :id AND oi.order_id IS NOT NULL", nativeQuery = true)
    public List<OrderItems> getOrderedItems(@Param("id") int id);

    @Query(value = "SELECT * FROM order_items oi WHERE oi.order_id IS NOT NULL", nativeQuery = true)
    public List<OrderItems> getOrderedItems();

    // @Query(value = "SELECT DISTINCT oi.order_id, oi.* FROM order_items oi WHERE oi.order_id IS NOT NULL", nativeQuery = true)
    // public List<OrderItems> getOrderedItems();


    
    
    
}
