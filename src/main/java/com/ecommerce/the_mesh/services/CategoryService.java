package com.ecommerce.the_mesh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.the_mesh.entities.Category;
import com.ecommerce.the_mesh.exception.ApiRequestException;
import com.ecommerce.the_mesh.respositories.CategoryRepository;

@Service
public class CategoryService {
    private final CategoryRepository catsRepo;

    public CategoryService(CategoryRepository catsRepo){
        this.catsRepo = catsRepo;
    }

    public Category insertCategory (Category Category){
        Category insertedCategory = catsRepo.save(Category);
        return insertedCategory;
    }

    public Category getCategoryById(int id){
        Optional<Category> Category = this.catsRepo.findById(id);

        if(Category.isPresent()){
            return Category.get();
        }else{
            throw new ApiRequestException("Category not found");
        }
    }

    public List<Category> getAllCategories(){
        List<Category> allCategories = this.catsRepo.findAll();
        return allCategories;
    }

    public Category findByCategoryName(String name){
        Category Category = this.catsRepo.getByCategoryName(name);
        return Category;
    }

    public List<Category> allCategoryExceptFor(String name){
        List<Category> Categories = this.catsRepo.allCategoryExceptFor(name);
        return Categories;
    }

    
}
