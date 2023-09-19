package com.ecommerce.the_mesh.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.ecommerce.the_mesh.entities.User;
import com.ecommerce.the_mesh.services.UserService;
import com.ecommerce.the_mesh.util.FileUploadHelper;


@Controller
@RequestMapping(path = "/users")
public class UserController {

    private User user = new User(); //its better to use an empty object rather than null cause it might arise a null pointter exception

    @Value("${file.upload.user.path}")
    private String userUploadPath;   //injecting the value of userUploadPath from which is static in application.properties
    private final UserService userService;  
    private final FileUploadHelper fileHelper;

public UserController(FileUploadHelper fileHelper, UserService userService){
    this.fileHelper = fileHelper;
    this.userService = userService;
}

@GetMapping(value = "/home")
public String getLoginPage(Model model, HttpSession session){ 
   User currentUser = (User)session.getAttribute("user");
   System.out.println(currentUser);
   model.addAttribute("user", currentUser);
    return "user_view/home";
}

@GetMapping("/signup")
public String getSignUpPage(Model model){
    model.addAttribute("user", new User());
    return "user_view/signup";
}

@PostMapping("/signup_process")
public String handleSignupForm(@Valid @ModelAttribute User user,
                                BindingResult result,
                                @RequestParam("user_image") MultipartFile file,
                                Model model) throws IOException{
    if (result.hasErrors()){
        return "user_view/signup";
    }
    
    user.setImage_user(file.getOriginalFilename());
    user.setIs_admin(2); //it assigns the user role to the user
    boolean isUploaded = this.fileHelper.fileUploadHelper(file, userUploadPath); //uploads the file and returns if it is uploaded or not
    User savedUser = this.userService.insertUser(user); //inserts the user object into the database

    if(isUploaded == true && savedUser!= null){
        System.out.println("The user is saved sucessfully");
        return"user_view/home";
    }

    return "user_view/signup";

}


@GetMapping("/login")
public String getLoginPage(Model model){
    model.addAttribute("user", new User()); //to auto populate the field with th:object and th:value
    return "user_view/login";
}

@PostMapping("/login_process")
public String handleLoginForm(@ModelAttribute("user") User user,Model model, HttpSession session){
    System.out.println(user.getEmail() + user.getPassword());
    User validUser = this.userService.validUser(user.getEmail(), user.getPassword()); //this user is from the input field 

    if(validUser != null){ //if the user is not null
        session.setAttribute("user", validUser);
        return "redirect:/users/home";    
    }else{
        model.addAttribute("errorMessage", "Your credentials donot match. Please try again");
        return "redirect:/users/login";
    }   
}


@GetMapping("/logout")
public String handleLogout(){
    this.user = null;
    return "redirect:/users/home";
}



    
}
