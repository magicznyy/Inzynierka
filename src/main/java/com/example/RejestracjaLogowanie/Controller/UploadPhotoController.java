package com.example.RejestracjaLogowanie.Controller;



import com.example.RejestracjaLogowanie.Post;
import com.example.RejestracjaLogowanie.PostRepository;
import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class UploadPhotoController {
    public static String uploadDir = System.getProperty("user.dir") + "/uploads";

    @Autowired
   private  UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/uploadPhoto")
    public String uploadPhoto(){

        return "uploadPhoto" ;
    }

    @RequestMapping("/upload")
    public String upload (Model model, @RequestParam("image") MultipartFile image, @RequestParam("description") String description, @RequestParam("tags") String tags)
        {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();


            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

            Post post = new Post(description,tags, now, user);
           postRepository.save(post);


        StringBuilder builder = new StringBuilder();

        builder.append("C:\\Users\\HardPc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images\\user" + user.getId().toString() + "\\" + "dupa.png");

        String path = builder.toString();


        Path fileNameAndPath = Paths.get(path);
        try{
            Files.write(fileNameAndPath, image.getBytes());
        }
        catch(Exception e){
            System.out.printf("Problem z zapisem");
            e.printStackTrace();
    }
        return "uploadPhoto";
    }

}
