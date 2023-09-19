package com.ecommerce.the_mesh.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.the_mesh.entities.User;
import com.ecommerce.the_mesh.respositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepo;

    


    //for saving the user in the database
    public User insertUser(User user){
        User savedUser = this.userRepo.save(user);

        return savedUser;
    }


    //getting the user object by id
    public User getUserById(int id){
        User user = this.userRepo.findById(id).get();

        return user;
    }

   


   
    public User validUser(String email, String password){
        User validUser = this.userRepo.checkVerifiedUser(email, password);
        return validUser;
    
    }

    
}
