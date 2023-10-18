package com.ecommerce.the_mesh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.the_mesh.entities.Brand;
import com.ecommerce.the_mesh.exception.ApiRequestException;
import com.ecommerce.the_mesh.respositories.BrandRepository;

@Service
public class BrandService {

    private final BrandRepository brandRepo;

    public BrandService(BrandRepository brandRepo){
        this.brandRepo = brandRepo;
    }

    public Brand insertBrand (Brand brand){
        Brand insertedBrand = brandRepo.save(brand);
        return insertedBrand;
    }

    public Brand getBrandById(int id){
        Optional<Brand> brand = this.brandRepo.findById(id);

        if(brand.isPresent()){
            return brand.get();
        }else{
            throw new ApiRequestException("Brand not found");
        }
    }

    public List<Brand> getAllBrands(){
        List<Brand> allBrands = this.brandRepo.findAll();
        return allBrands;
    }

    public Brand findByBrandName(String name){
        Brand brand = this.brandRepo.getByBrandName(name);
        return brand;
    }

    public List<Brand> allBrandsExceptFor(String name){
        List<Brand> brands = this.brandRepo.allBrandExceptFor(name);
        return brands;
    }

    
    
}
