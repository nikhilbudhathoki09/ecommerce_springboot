package com.ecommerce.the_mesh.exception;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;



@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestException.class})
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException apiRequestException){

        // var http =  new ResponseEntity<>("hello world",HttpStatus.OK);
        // return ResponseEntity.ok().body("Welcome User");  //wrap the response into the response entity

        ApiException apiException = new ApiException(apiRequestException.getMessage() , HttpStatus.BAD_REQUEST, ZonedDateTime.now(ZoneId.of("Z")));

        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }

    

    
}
