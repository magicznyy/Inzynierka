package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
public class CommentController {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;


    //to jest upośledzone dodawanie z przeładowaniem i przekierowaniem spowrotem na strone główną
    @RequestMapping("/addComment")
    public String addComment(@RequestParam(name="comment") String content, @RequestParam(name="idPost") Long idPost){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        if(!content.isEmpty()){
        Post post = postRepository.findPostByidPost(idPost);
        Comment comment = new Comment(content, user, post);

        //w poscie jest lista komentarzy do tego posta
        post.addComment(comment);

        commentRepository.save(comment);

        System.out.println("Komentarze: " + post.getComments() );
        }
        return "redirect:/mainPage";
    }

    @RequestMapping("/addComment1")
    public String addComment1(@RequestParam(name="comment") String content, @RequestParam(name="idPost") Long idPost){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());

        Post post = postRepository.findPostByidPost(idPost);
        Comment comment = new Comment(content, user, post);

        //w poscie jest lista komentarzy do tego posta
        post.addComment(comment);

        commentRepository.save(comment);

        System.out.println("Komentarze: " + post.getComments() );
        return "redirect:/photoPreview/post/" + post.getIdPost();
    }






}
