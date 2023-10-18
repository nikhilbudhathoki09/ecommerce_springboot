package com.ecommerce.the_mesh.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.the_mesh.entities.Brand;
import com.ecommerce.the_mesh.entities.Order;
import com.ecommerce.the_mesh.services.BrandService;
import com.ecommerce.the_mesh.services.OrderService;
import com.ecommerce.the_mesh.util.FileUploadHelper;


@Controller
@RequestMapping(path = "/admin")
public class AdminController {
    
    @Value("${file.upload.brand.path}")
    private String brandUploadPath;
    private final OrderService orderService;
    private final BrandService brandService;
    private final FileUploadHelper fileHelper;

    public AdminController(OrderService orderService, BrandService brandService, FileUploadHelper fileHelper){
        this.brandService = brandService;
        this.orderService = orderService;
        this.fileHelper = fileHelper;
    }

    @GetMapping(value = "/home")
    public String getMainPage(){

        return "admin_view/index";   
    }

    @GetMapping(value = "/orders")
    public String getOrderPage(Model model){
        List<Order> orders = this.orderService.getAllOrders();
        model.addAttribute("orders", orders);  
        return "admin_view/view_orders";
    }

    @GetMapping(value = "/addBrands")
    public String getBrands(){
        return "admin_view/addBrands";
    }

    @PostMapping(value ="/addBrandsProcess")
    public String addBrandsProcess(@ModelAttribute("brand") Brand brand, @RequestParam("brand_logo") MultipartFile file){

        brand.setBrand_image(file.getOriginalFilename());
        Brand savedBrand = this.brandService.insertBrand(brand);

        boolean isUploaded = this.fileHelper.fileUploadHelper(file, brandUploadPath);

        if(isUploaded == true && savedBrand != null){
            return"redirect:/admin/home";
        }

        return "redirect:/admin/addBrands";
    }

    
}
