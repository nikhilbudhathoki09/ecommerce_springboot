package com.ecommerce.the_mesh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity  //mapping this java class as a entity 
@Getter
@Setter
@ToString
@AllArgsConstructor //constructor for all the parameters
@NoArgsConstructor  //default constructor
@Builder  
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int user_id;

    @NotEmpty(message = "Email cannot be set empty.")
    @Email(message = "Please enter a valid email")
    private String email;

    @NotBlank(message = "Password cannot be set empty")
    @Size(min = 3, max = 15, message = "Length of the Password must me 3-15 characters" )
    private String password;

    @NotEmpty(message = "Name cannot be empty")
    private String user_name;
    private int is_admin;

    private String image_user;    
    private String gender;

    @NotEmpty(message = "Address cannot be empty")
    private String user_address;

    @NotEmpty(message = "Phone Number is mandatory")
    private String user_phone;


  public User(String email, String password, String user_name, int is_admin, String image_user, String gender, String user_address, String user_phone){
      
    this.email = email;
    this.password = password;
    this.user_name = user_name;
    this.is_admin = 2;
    this.image_user = image_user;
    this.gender = gender;
    this.user_address = user_address;
    this.user_phone = user_phone;

  }

    
}
