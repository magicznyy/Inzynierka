package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
public class MainPageController {


    @Autowired
    PostRepository postRepository;
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    UserRepository userRepository;

    UserInformation userInformation;

    @GetMapping("/mainPage")
    public String mainPage(Model model){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user=userRepository.findUserByLogin(login);
        if(Objects.nonNull(user.getPrywatnosckonta()))
            model.addAttribute("userpriviet", 1);
        else model.addAttribute("userpriviet", 0);

        if(user.getProfilePicPath()==null)
            model.addAttribute("profilepic", "/images/profpic/nopicture.jpg");
        else
            model.addAttribute("profilepic", user.getProfilePicPath());



        return "mainPage";
    }

    //to jest straszne, ale dziala na razie
    public List<Post> getFollowedUsersPosts(Integer lastPostID){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user1 = (User) userRepository.findUserByLogin(userDetails.getUsername());

        List<Post> followedUsersPosts = new ArrayList<Post>();
        List<FollowedUser> followedUsers = user1.getFollowedUsers();

        //brak postow o takim id
        if(lastPostID < -1 || lastPostID==0)
            return followedUsersPosts;

        //pobierz posty o dowolnym id
        if(lastPostID==-1)
            lastPostID=Integer.MAX_VALUE;

        int postsNumber=0;
        for(FollowedUser fUser : followedUsers){
            List<Post> tempPosts = new ArrayList<Post>(fUser.getFollowedUser().getPosts());

            for(int i = tempPosts.size(); i-- >0;){
                if(tempPosts.get(i).getIdPost()<lastPostID && postsNumber<25) {
                    followedUsersPosts.add(tempPosts.get(i));
                    postsNumber++;
                }

            }
        }

        return followedUsersPosts;
    }


    @ResponseBody
    @GetMapping(value = "/mainPage/data/getPosts/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public String mainPageFetchPosts(@PathVariable("id") Integer lastPostID) throws JsonProcessingException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

        List<Post> followedUsersPosts = new ArrayList<Post>();
        ObjectMapper mapper = new ObjectMapper();
        String wynik = mapper.writeValueAsString( getFollowedUsersPosts(lastPostID));

        return wynik;
    }



    @ResponseBody
    @GetMapping(value = "/mainPage/data/avatar/{login}")
    public ResponseEntity<byte[]> mainPageGetAvatar(@PathVariable("login") String login) throws IOException {
        String path = "\\static";

        path +=  userRepository.findUserByLogin(login).getProfilePicPath();
        ClassPathResource imageFile = new ClassPathResource(path);

        byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageBytes);
    }

    @ResponseBody
    @GetMapping(value = "/mainPage/data/photo/{id}")
    public ResponseEntity<byte[]> mainPageGetPhotoSmall(@PathVariable("id") Long id) throws IOException {
        String path = "\\static";
        Photo photo = photoRepository.getById(id);
        path +=  photo.getPath();


        //utworzenie ścieżki do miniatury
        File file = new File(path);
        String folder = file.getParent();

        path =  file.getParent() + "\\resized" + "\\" + file.getName();

        ClassPathResource imageFile = new ClassPathResource(path);

        String photoExtension = path.substring(path.length()-3).toLowerCase();
        MediaType mediaType=MediaType.ALL;
        if(photoExtension.equals("jpg"))
            mediaType = MediaType.IMAGE_JPEG;
        else if(photoExtension.equals("png"))
            mediaType = MediaType.IMAGE_PNG;


        byte[] imageBytes = StreamUtils.copyToByteArray(imageFile.getInputStream());
        return ResponseEntity.ok().contentType(mediaType).body(imageBytes);
    }




}
