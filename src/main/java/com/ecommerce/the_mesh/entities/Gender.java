package com.ecommerce.the_mesh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Gender {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer gender_id;

    @NotBlank(message = "This field cannot be empty")
    private String gender_name;
    
    @Override
    public String toString() {
        return "Gender [gender_id=" + gender_id + ", gender_name=" + gender_name + "]";
    }

    
}
