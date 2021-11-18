package com.example.RejestracjaLogowanie.Controller;



import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class UploadPhotoController {
    public static String uploadDir = System.getProperty("user.dir") + "/uploads";



    @GetMapping("/uploadPhoto")
    public String uploadPhoto(){

        return "uploadPhoto" ;
    }

    @RequestMapping("/upload")
    public String upload (Model model, @RequestParam("image") MultipartFile image)
    {
        Path fileNameAndPath = Paths.get("C:\\Users\\HardPc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images");
        try{
            Files.write(fileNameAndPath, image.getBytes());
        }
        catch(Exception e){
            System.out.printf("Problem z zapisem");
    }
        return "uploadPhoto";
    }

}
