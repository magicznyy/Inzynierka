package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserDetailsService;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class SettingsController {

    @Autowired
    private PasswordEncoder passwordEncoder;

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
    public String updateLogin (Model model,@RequestParam("username") String userName){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);

        if(userRepository.findUserByLogin(userName)!=null)
        {

            ScriptEngineManager manager = new ScriptEngineManager();//nashorn engine
            ScriptEngine engine = manager.getEngineByName("JavaScript");

            try {
                FileReader reader = new FileReader("C:\\Users\\HardPc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\js\\allPopUps.js");
                engine.eval(reader);
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            user.setLogin(userName);
            userRepository.save(user);
            SecurityContextHolder.clearContext();
        }
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingPasswd")
    public String updatePasswd (Model model, @RequestParam("currpassword") String currpassword, @RequestParam("password") String password, @RequestParam("repeatpassword") String password1 ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);

        if(passwordEncoder.matches(currpassword,user.getHashhasla()))
        {
            user.setHashhasla(passwordEncoder.encode(password));
        }

        else
        {

        }

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
    public String updateLastName (Model model, @RequestParam("lastName") String lastName){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setNazwisko(lastName);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingBankAccountNumber")
    public String updateBankAccountNumber(Model model, @RequestParam("bankaccnumb") String bankAccNumb){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setNrkontabankowego(bankAccNumb);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    //@RequestMapping(value = "deleteAccount", method = RequestMethod.DELETE)
    @PostMapping("/deleteAccount")
    public String deleteAccount (Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        userRepository.delete(user);
        SecurityContextHolder.clearContext();
        return "redirect:/profileSettings";
    }


    @PostMapping("/updatingProfileDesc")
    public String updateProfileDescription (Model model, @RequestParam("profileDescription") String description){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userRepository.findUserByLogin(userDetails.getUsername());
        model.addAttribute("user", user);
        user.setProfileDescription(description);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

}




