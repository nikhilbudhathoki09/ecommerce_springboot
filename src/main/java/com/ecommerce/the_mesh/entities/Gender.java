package com.ecommerce.the_mesh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String gender_id;

    @NotBlank(message = "This field cannot be empty")
    private String gender_name;

    public String getGender_id() {
        return gender_id;
    }

    public void setGender_id(String gender_id) {
        this.gender_id = gender_id;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }

    @Override
    public String toString() {
        return "Gender [gender_id=" + gender_id + ", gender_name=" + gender_name + "]";
    }

    
}
