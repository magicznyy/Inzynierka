package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.Comment;
import com.example.RejestracjaLogowanie.CommentRepository;
import com.example.RejestracjaLogowanie.PostRepository;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;


    //to jest upośledzone dodawanie z przeładowaniem i przekierowaniem spowrotem na strone główną
    @PostMapping("/addComment")
    public String addComment(@RequestParam(name="comment") String content){

        Comment comment = new Comment(content);
        commentRepository.save(comment);




        return "redirect:/mainPage";
    }



}
