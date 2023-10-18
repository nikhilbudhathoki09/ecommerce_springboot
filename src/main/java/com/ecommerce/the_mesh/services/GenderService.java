package com.ecommerce.the_mesh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.the_mesh.entities.Gender;
import com.ecommerce.the_mesh.exception.ApiRequestException;
import com.ecommerce.the_mesh.respositories.GenderRepository;

@Service
public class GenderService {

    public final GenderRepository genderRepo;

    public GenderService(GenderRepository genderRepo){
        this.genderRepo = genderRepo;
    }

    public Gender insertGender(Gender gender){
        Gender insertedGender = this.genderRepo.save(gender);
        return insertedGender;
    }

    public Gender findByGenderName(String name){
        Gender gender = this.genderRepo.getByGenderName(name);

        return gender;
    }

    public Gender getGenderById(int id){
        Optional<Gender> gender = this.genderRepo.findById(id);
        
        if(gender.isPresent()){
            return gender.get();
        }else{
            throw new ApiRequestException("Gender not found");
        }
    }

    public List<Gender> getallGenders(){
        List<Gender> allGenders = this.genderRepo.findAll();
        return allGenders;  
    }

    public List<Gender> allGenderExceptFor(String name){
        List<Gender> genders = this.genderRepo.allGenderExceptFor(name);
        return genders;
    }

    
}
