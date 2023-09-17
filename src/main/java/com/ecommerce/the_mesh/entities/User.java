package com.ecommerce.the_mesh.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity  //mapping this java class as a entity 
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


    public User(){
    }

    public User(int user_id, String email, String password, String user_name, int is_admin, String image_user, String gender, String user_address, String user_phone){

        this.user_id = user_id;
		this.email = email;
		this.password = password;
		this.user_name = user_name;
		this.is_admin = 2;
		this.image_user = image_user;
		this.gender = gender;
		this.user_address = user_address;
		this.user_phone = user_phone;

    }

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getIs_admin() {
        return is_admin;
    }

    public void setIs_admin(int is_admin) {
        this.is_admin = is_admin;
    }

    public String getImage_user() {
        return image_user;
    }

    public void setImage_user(String image_user) {
        this.image_user = image_user;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUser_address() {
        return user_address;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    @Override
    public String toString() {
        return "Users [user_id=" + user_id + ", email=" + email + ", password=" + password + ", user_name=" + user_name
                + ", is_admin=" + is_admin + ", image_user=" + image_user + ", gender=" + gender + ", user_address="
                + user_address + ", user_phone=" + user_phone + "]";
    }


    
}
