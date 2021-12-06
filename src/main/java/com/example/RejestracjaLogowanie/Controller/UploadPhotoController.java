package com.example.RejestracjaLogowanie.Controller;



import com.example.RejestracjaLogowanie.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class UploadPhotoController {
    public static String uploadDir = System.getProperty("user.dir") + "/uploads";

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private  PinRepository pinRepository;



    @GetMapping("/uploadPhoto")
    public String uploadPhoto(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("lat", user.getMapsCenterLatitude());
        model.addAttribute("lng", user.getMapsCenterLongitude());
        return "uploadPhoto" ;
    }

    @PostMapping("/upload")
    public String upload (Model model, @RequestParam("image") MultipartFile image, @RequestParam("description") String description, @RequestParam("tags") String tags, @RequestParam(value = "lat", required=false) Double lat, @RequestParam(value = "lng", required=false) Double lng, @RequestParam(value = "pindescription", required=false) String pindescription, @RequestParam(value = "color", required=false) String color)

        {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
          User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

        System.out.println("id: " + user.getId());

        Photo photo = new Photo("sciezka");
        photoRepository.save(photo);

        Post post = new Post(tags, description, new Date(), user, photo);
        postRepository.save(post);



        List<User> photos =  userRepository.findAll();
        for (User user1 :photos) {
            System.out.println( "Przed:" + user1.getLogin());

        }






        String photoExtension = image.getOriginalFilename().toString();
        photoExtension = photoExtension.substring(photoExtension.length() - 3);

        StringBuilder builder = new StringBuilder();

        if(Objects.equals(photoExtension,"jpg"))

            builder.append("C:\\Users\\Hardpc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images\\user" + user.getId().toString() + "\\" + photo.getPhotoId() + ".jpg");
        else
            builder.append("C:\\Users\\Hardpc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images\\user" + user.getId().toString() + "\\" + photo.getPhotoId() + ".png");

        String path = builder.toString();

        Path fileNameAndPath = Paths.get(path);
        try{
            Files.write(fileNameAndPath, image.getBytes());
        }
        catch(Exception e){
            System.out.printf("Problem z zapisem");
            e.printStackTrace();
        }

        int index = path.lastIndexOf("images");
        path = path.substring(index-1);

        photo.setPath(path);
        photoRepository.save(photo);

        if(!Objects.isNull(lng))
        {
            Pin pin = new Pin(lng, lat, pindescription, color, user, photo);
            pinRepository.save(pin);
        }


        return "uploadPhoto";
    }

}
