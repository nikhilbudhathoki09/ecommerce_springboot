package com.ecommerce.the_mesh.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {


    
    public boolean fileUploadHelper(MultipartFile multipartFile , String path){
        boolean isUploaded = false;

        try{
            String fullUploadPath = path + File.separator+multipartFile.getOriginalFilename();  //it gives full path along with the name of thee file
            System.out.println("full upload path is"+fullUploadPath);

            Files.copy(multipartFile.getInputStream(), Paths.get(fullUploadPath),StandardCopyOption.REPLACE_EXISTING);
            isUploaded = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return isUploaded; 
    }
    

}
