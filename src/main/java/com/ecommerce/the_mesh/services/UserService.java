package com.ecommerce.the_mesh.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.the_mesh.entities.User;
import com.ecommerce.the_mesh.respositories.UserRepository;
import com.ecommerce.the_mesh.util.FileUploadHelper;

@Service
public class UserService {
    
    @Value("${file.upload.user.path}")
    private String userUploadPath;
    private final UserRepository userRepo;
    private final FileUploadHelper fileHelper;
    
    public UserService(UserRepository userRepo, FileUploadHelper fileHelper){
        this.userRepo = userRepo;
        this.fileHelper = fileHelper;
    }

    public User insertUser(User user){ //for saving the user in the database
        User savedUser = this.userRepo.save(user);
        return savedUser;
    }

    public User getUserById(int id){  //getting the user object by id
        User user = this.userRepo.findById(id).get();
        return user;
    }

    public User validUser(String email, String password){  //checking the user is valid or not from the database(used when login)
        User validUser = this.userRepo.checkVerifiedUser(email, password);
        return validUser;
    }

    public User updateUser(User updatedUser , MultipartFile file){
        User existingUser = userRepo.findById(updatedUser.getUser_id()).get(); //this is to get the user from the database

        if(!file.isEmpty()){
            updatedUser.setImage_user(file.getOriginalFilename());
            this.fileHelper.fileUploadHelper(file, userUploadPath);
        }else{
            updatedUser.setImage_user(existingUser.getImage_user());
        }

        //setting the values updated by the user
        existingUser.setUser_name(updatedUser.getUser_name());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setGender(updatedUser.getGender());
        existingUser.setUser_address(updatedUser.getUser_address());
        existingUser.setUser_phone(updatedUser.getUser_phone());
        existingUser.setImage_user(updatedUser.getImage_user());
        
        updatedUser = this.userRepo.save(existingUser); //saving the updated details in the database
        return updatedUser;
    }

    
}
