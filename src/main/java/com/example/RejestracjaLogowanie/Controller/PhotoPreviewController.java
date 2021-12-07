package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.Photo;
import com.example.RejestracjaLogowanie.PhotoRepository;
import com.example.RejestracjaLogowanie.Post;
import com.example.RejestracjaLogowanie.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.util.Objects;


@Controller
public class PhotoPreviewController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/photoPreview/post/{idPost}")
    public String photoPreview(Model model, @PathVariable(name="idPost") Long idPost){

        Post post = postRepository.findPostByidPost(idPost);
        model.addAttribute("post", post);

        File directory=new File("C:\\Users\\Hardpc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images\\user1");

        if(directory.list()!=null) {
            String[] imagename = Objects.requireNonNull(directory.list());
            model.addAttribute("photos", imagename);
        }

        return "photoPreview";
    }

    @GetMapping("/photoPreview")
String photo(){
        return "photoPreview";
    }

}
