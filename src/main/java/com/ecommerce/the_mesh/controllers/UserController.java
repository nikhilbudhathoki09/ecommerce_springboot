package com.ecommerce.the_mesh.controllers;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.the_mesh.entities.Order;
import com.ecommerce.the_mesh.entities.OrderItems;
import com.ecommerce.the_mesh.entities.Product;
import com.ecommerce.the_mesh.entities.User;
import com.ecommerce.the_mesh.exception.ApiRequestException;
import com.ecommerce.the_mesh.services.OrderItemService;
import com.ecommerce.the_mesh.services.OrderService;
import com.ecommerce.the_mesh.services.ProductService;
import com.ecommerce.the_mesh.services.UserService;
import com.ecommerce.the_mesh.util.FileUploadHelper;


@Controller
@RequestMapping(path = "/users")
public class UserController {
    
    private final Logger log = LoggerFactory.getLogger(UserController.class);
    private User user = new User(); //its better to use an empty object rather than null cause it might arise a null pointter exception

    @Value("${file.upload.user.path}")
    private String userUploadPath;   //injecting the value of userUploadPath from which is static in application.properties
    private final UserService userService;  
    private final FileUploadHelper fileHelper;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    private final OrderService orderService;

public UserController(FileUploadHelper fileHelper, UserService userService, ProductService productService, OrderItemService orderItemService, OrderService orderService){
    this.fileHelper = fileHelper;
    this.userService = userService;
    this.productService = productService;
    this.orderItemService = orderItemService;
    this.orderService = orderService;
}

@GetMapping(value = "/home")
public String getHomePage(Model model, HttpSession session){ 
    User currentUser = (User)session.getAttribute("user");
    log.info("currentUser: {}" ,currentUser);
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
    Optional<User >validUser = this.userService.validUser(user.getEmail(), user.getPassword()); //this user is from the input field 

    if(validUser.isPresent()){
        session.setAttribute("user", validUser.get());
        return "redirect:/users/home";
    }else{
        model.addAttribute("errorMessage", "Your credentials donot match. Please try again");
        return "user_view/login";
    }
}

@GetMapping("/logout")
public String handleLogout(HttpSession session){
    this.user = null;
    session.removeAttribute("user");
    return "redirect:/users/home";
}

@GetMapping("/profile")
public String getProfilePage(Model model, HttpSession session){
    this.user = (User)session.getAttribute("user");
    model.addAttribute("user", user);
    return"user_view/profile";
}

@PostMapping("/update_profile")
public String updateUser(@ModelAttribute User updatedUser,
                         @RequestParam("user_image") MultipartFile file,
                         Model model, HttpSession session ) throws IOException{
    user = (User)session.getAttribute("user"); //getting user from the current session
    updatedUser.setUser_id(user.getUser_id());      //setting the user_id value from the session

    this.user = this.userService.updateUser(updatedUser, file); //after it is updated
    session.setAttribute("user", user); //updating the user in the session
    return "redirect:/users/profile";
}

@GetMapping("/products")
public String getProductPage(Model model, HttpSession session){
    User currentUser = (User)session.getAttribute("user");
    model.addAttribute("user", currentUser);

    List<Product> allProducts = this.productService.getAllProducts();
    model.addAttribute("products", allProducts);
    return "/user_view/products";
}

@GetMapping("/product_description/{id}")
public String getProductDescriptionPage(@PathVariable("id") int id, Model model, HttpSession session){
    User currentUser = (User)session.getAttribute("user");
    model.addAttribute("user", currentUser);
    Optional<Product> product = this.productService.getProdcutById(id);

    if(product.isPresent()){
        model.addAttribute("product", product.get());
        return "user_view/productDescription";
    }else{
        throw new ApiRequestException("Product not found");
    }
}

@GetMapping("/products/Men")
public String getMenProducts(Model model, HttpSession session){
    User currentUser = (User)session.getAttribute("user");
    model.addAttribute("user", currentUser);
    List<Product> menProducts = this.productService.getMenProducts();
    model.addAttribute("products", menProducts);
    return "user_view/products";
    
}

@GetMapping("/products/Women")
public String getWomenProducts(Model model, HttpSession session){
    User currentUser = (User)session.getAttribute("user");
    model.addAttribute("user", currentUser);
    List<Product> womenProducts = this.productService.getWomenProducts();
    model.addAttribute("products", womenProducts); 
    return "user_view/products";
}

@GetMapping("/products/")
public String getSearchProducts(@RequestParam("productName") String productName ,Model model, HttpSession session){
    User currentUser = (User)session.getAttribute("user");
    model.addAttribute("user", currentUser);
    List<Product> products = this.productService.getSearchProducts(productName);
    model.addAttribute("products", products);
    return "user_view/products";
}

@GetMapping("/carts/add")
public String handleCartItems( @RequestParam("product_id") int product_id,  HttpSession session){
    User currentUser = (User)session.getAttribute("user");
    Product product = this.productService.getProdcutById(product_id).get();
    OrderItems orderItem = this.orderItemService.checkCartItems(currentUser.getUser_id(), product_id);

    System.out.println(orderItem);
    if(orderItem != null){
        System.out.println("Product already exists");
        return "redirect:/users/products";
    
    }else{
        System.out.println("current user on carts is : " + currentUser);
        Date date =  new java.sql.Date(System.currentTimeMillis());

        OrderItems item = new OrderItems();
        item.setProduct(product);
        item.setPrice(product.getProduct_price());
        item.setQuantity(1);
        item.setDate(date);
        item.setUser(currentUser);

        OrderItems savedItem = this.orderItemService.saveOrderItems(item);
        if(savedItem!= null){
            session.setAttribute("user", currentUser);
            return "redirect:/users/products";
        }else{
            System.out.println("There has been some error");
            session.setAttribute("user", currentUser);
            return "user_view/products";
        } 
    }  

}

@GetMapping("/carts/view")
public String getCartsPage(Model model, HttpSession session){
    User currentUser = (User)session.getAttribute("user");
    model.addAttribute("user", currentUser);
    
    double totalPrice = this.orderItemService.getTotalPrice(currentUser.getUser_id());

    List<OrderItems> carts = this.orderItemService.getCartItems(currentUser.getUser_id());
    model.addAttribute("cartItems", carts);
    model.addAttribute("totalPrice", totalPrice);
    return "user_view/cart";
}


@GetMapping("/carts/remove")
public String removeCartProducts(@RequestParam("order_item_id") int order_item_id,Model model, HttpSession session){
    User currentUser = (User) session.getAttribute("user");
    double totalPrice = this.orderItemService.getTotalPrice(currentUser.getUser_id());
    model.addAttribute("user",currentUser); 
    model.addAttribute("totalPrice", totalPrice);

    this.orderItemService.removeCartProduct(order_item_id);
    return "redirect:/users/carts/view";
}

@GetMapping("/carts/quantity")
public String setQuantity(@RequestParam("order_item_id") int order_item_id, @RequestParam("increase") boolean increase, Model model,HttpSession session){

    User currentUser = (User)session.getAttribute("user");
    model.addAttribute("user", currentUser);
    double totalPrice = this.orderItemService.getTotalPrice(currentUser.getUser_id());
    
    if(increase){
        this.orderItemService.increaseQuantity(order_item_id); //increasing the quantity by 1
        model.addAttribute("totalPrice", totalPrice);
        return "redirect:/users/carts/view";
    }else{
        this.orderItemService.decreaseQuantity(order_item_id);
        model.addAttribute("totalPrice", totalPrice);
        return "redirect:/users/carts/view";
    }

}

@GetMapping("/orders/add")
public String createOrder(@RequestParam("totalPrice") double totalPrice, Model model,  HttpSession session){
    User currentUser =(User)session.getAttribute("user");
    model.addAttribute("user", currentUser);
    Date date =  new java.sql.Date(System.currentTimeMillis());

    Order order = new Order();
    order.setOrder_date(date);
    order.setTotal_amount(totalPrice);
    order.setUser(currentUser);

    Order savedOrder = this.orderService.addOrder(order);
    if(savedOrder != null){
    this.orderItemService.moveToOrder(currentUser.getUser_id(), savedOrder.getOrder_id()); //updates the orderId in the database
    
    List<OrderItems> orderedItems = this.orderItemService.getOrderedItems(currentUser.getUser_id()); //gives the list of ordered items
    model.addAttribute("orderedItems",orderedItems);
    return "user_view/order";
    }else{
        model.addAttribute("totalPrice", totalPrice);
        return "redirect:/users/carts/view";
    }

}

@GetMapping("/orders")
public String getOrdersPage(Model model, HttpSession session){
    User currentUser =(User)session.getAttribute("user");
    model.addAttribute("user", currentUser);

    List<OrderItems> orderedItems = this.orderItemService.getOrderedItems(currentUser.getUser_id()); //gives the list of ordered items
    model.addAttribute("orderedItems",orderedItems);
    return "user_view/order";

}

}

