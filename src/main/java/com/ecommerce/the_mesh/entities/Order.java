package com.ecommerce.the_mesh.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int order_id;

    @ManyToOne
    @JoinColumn(name = "user_id" ,referencedColumnName = "user_id")
    private User user;
    private Date order_date;
    private double total_amount;



    public Order(){}


    public Order(User user, Date order_date, double total_amount) {
        this.user = user;
        this.order_date = order_date;
        this.total_amount = total_amount;
    }

    

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    @Override
    public String toString() {
        return "Orders [order_id=" + order_id + ", user=" + user + ", order_date=" + order_date + ", total_amount="
                + total_amount + "]";
    }

    
}
