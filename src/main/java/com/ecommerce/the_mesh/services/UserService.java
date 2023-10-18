package com.ecommerce.the_mesh.services;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.the_mesh.entities.User;
import com.ecommerce.the_mesh.exception.ApiRequestException;
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

    public Optional<User> validUser(String email, String password){  //checking the user is valid or not from the database(used when login)
        Optional<User> validUser = this.userRepo.checkVerifiedUser(email, password);
        // if(!validUser.isPresent()){
        //     // throw new IllegalStateException("User doesnot exist");
        //     throw new ApiRequestException("User doesnot exist");
        // }else{
        //     return validUser; 
        // }
        return  validUser;
        
    }

    public User updateUser(User updatedUser , MultipartFile file){
        // User existingUser = userRepo.findById(updatedUser.getUser_id()).get(); //this is to get the user from the database
        Optional<User> existingUser = userRepo.findById(updatedUser.getUser_id());

        if(!file.isEmpty()){
            updatedUser.setImage_user(file.getOriginalFilename());
            this.fileHelper.fileUploadHelper(file, userUploadPath);
        }else{
            if(existingUser.isPresent()){
                updatedUser.setImage_user(existingUser.get().getImage_user());   
            }else{
                throw new ApiRequestException("User doesnot exists");
            }
            
        }  
     //setting the values updated by the user
        if(existingUser.isPresent()){
            existingUser.get().setUser_name(updatedUser.getUser_name());
            existingUser.get().setPassword(updatedUser.getPassword());
            existingUser.get().setGender(updatedUser.getGender());
            existingUser.get().setUser_address(updatedUser.getUser_address());
            existingUser.get().setUser_phone(updatedUser.getUser_phone());
            existingUser.get().setImage_user(updatedUser.getImage_user());
            updatedUser = this.userRepo.save(existingUser.get()); //saving the updated details in the database
        }else{
            throw new ApiRequestException("User doesnot exists");
        }    
        return updatedUser;
    }

    
}
