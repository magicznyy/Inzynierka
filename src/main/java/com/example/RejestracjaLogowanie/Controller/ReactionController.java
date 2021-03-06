package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;


@Controller
public class ReactionController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private UserRepository userRepository;



    @RequestMapping("/addReaction")
    public String addReaction(@RequestParam(name="idPostReakcja") Long idPost)
    {
        System.out.println("przekazano id= " + idPost);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());


        if(!isReactionSet(user, idPost)) {
            Post post = postRepository.findPostByidPost(idPost);
            Reaction reaction = new Reaction(post, user);
            user.addReaction(reaction);
            post.addReaction(reaction);
            reactionRepository.save(reaction);
            System.out.println(user.getId() + " +1 do " + post.getIdPost());
        }
        else{
            removeReaction(idPost, user);
            System.out.println(user.getId() + " -1 do " + idPost);
        }
            return "redirect:/mainPage";

    }


    @RequestMapping("/removeReaction")
    public String removeReaction(Long idPost, User user)
    {
        List<Reaction> usersReactions = user.getReactions();

        for (Reaction tempReaction : usersReactions) {
            if(tempReaction.getPost().getIdPost() == idPost){
                usersReactions.remove(tempReaction);
                tempReaction.getPost().getReactions().remove(tempReaction);
                reactionRepository.delete(tempReaction);
                break;
            }
        }

        return "redirect:/mainPage";
    }


    public boolean isReactionSet(User user, Long idPost ){

        List<Reaction> usersReactions = user.getReactions();


        for (Reaction tempReaction : usersReactions) {
            if(tempReaction.getPost().getIdPost() == idPost)
                return true;
        }
        return false;
    }


}
