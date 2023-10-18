package com.ecommerce.the_mesh.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ecommerce.the_mesh.entities.Product;
import com.ecommerce.the_mesh.exception.ApiRequestException;
import com.ecommerce.the_mesh.respositories.ProductRepository;

@Service
public class ProductService {

    private final  ProductRepository productRepo;

    public ProductService(ProductRepository productRepo){
        this.productRepo = productRepo;
    }

    public List<Product> getAllProducts(){
        List<Product> allProducts = this.productRepo.findAll();
        return allProducts;
    }

    public Optional<Product> getProdcutById(int id){
        Optional<Product> product = this.productRepo.findById(id);

        if(product.isPresent()){
            return product;
        }else{
            throw new ApiRequestException("Product not found");
        } 
    } 

    public List<Product> getMenProducts(){
        List<Product> menProducts = this.productRepo.getMenProducts();
        return menProducts;
    }

    public List<Product> getWomenProducts(){
        List<Product> womenProducts = this.productRepo.getWomenProducts();
        return womenProducts;
    }

    public List<Product> getSearchProducts(String search){
        List<Product> searchedProducts = this.productRepo.findProductsByNameOrBrand(search);
        return searchedProducts;
    }

    
    

       
}
