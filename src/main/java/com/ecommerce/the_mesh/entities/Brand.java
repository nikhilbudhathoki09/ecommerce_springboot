package com.ecommerce.the_mesh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity

public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //auto-increment in the primary key (id) values
    private int brand_id;

    @NotBlank(message = "Brand name cannot be set empty")
    private String brand_name;

    private String brand_image;
    


    public Brand(){}


    public Brand(int brand_id, String brand_name, String brand_image){
        this.brand_id = brand_id;
        this.brand_name = brand_name;
        this.brand_image = brand_image;
    }

        public Brand(String brand_name, String brand_image){
        this.brand_name = brand_name;
        this.brand_image = brand_image;
    }


    public int getBrand_id() {
        return brand_id;
    }


    public void setBrand_id(int brand_id) {
        this.brand_id = brand_id;
    }


    public String getBrand_name() {
        return brand_name;
    }


    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }


    public String getBrand_image() {
        return brand_image;
    }


    public void setBrand_image(String brand_image) {
        this.brand_image = brand_image;
    }

    @Override
    public String toString() {
        return "Brands [brand_id=" + brand_id + ", brand_name=" + brand_name + ", brand_image=" + brand_image + "]";
    }

 
}
