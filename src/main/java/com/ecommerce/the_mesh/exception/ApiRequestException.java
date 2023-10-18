package com.ecommerce.the_mesh.exception;


public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message){ //this passes the string message to the client
        super(message);
    }
    
}
