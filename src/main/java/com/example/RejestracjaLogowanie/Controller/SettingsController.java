package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserDetailsService;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SettingsController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "profileSettings", method = RequestMethod.GET)
    public String profileSettings(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        return "profileSettings";
    }

    @PostMapping("/updatingLogin")
    public void updateLogin (Model model,@RequestParam("username") String userName){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setLogin(userName);
        userRepository.save(user);
        SecurityContextHolder.clearContext();
    }

    @PostMapping("/updatingPasswd")
    public String updatePasswd (Model model, @RequestParam("password") String password, @RequestParam("password1") String password1 ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setHashhasla("password");
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingEmail")
    public String updateEmail (Model model, @RequestParam("email") String email){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setEmail(email);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingName")
    public String updateName (Model model, @RequestParam("name") String name){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setImie(name);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingLastName")
    public String updateLastName (Model model, @RequestParam("lastname") String lastName){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setNazwisko(lastName);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingBankAccountNumber")
    public String updateData (Model model, @RequestParam("bankaccnumb") String bankAccNumb){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setNrkontabankowego(bankAccNumb);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }


}




