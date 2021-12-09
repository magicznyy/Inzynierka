package com.example.RejestracjaLogowanie.Controller;


import com.example.RejestracjaLogowanie.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Controller
public class SettingsController extends UserInformation {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "profileSettings", method = RequestMethod.GET)
    public String profileSettings(Model model){
        userdata(model, userRepository);

        return "profileSettings";
    }

    @PostMapping("/updatingLogin")
    public String updateLogin (Model model,@RequestParam("username") String userName){
        User user = userCurr(model, userRepository);
        model.addAttribute("user", user);

        if(userRepository.findUserByLogin(userName)!=null)
        {

            ScriptEngineManager manager = new ScriptEngineManager();//nashorn engine
            ScriptEngine engine = manager.getEngineByName("JavaScript");

            try {
                FileReader reader = new FileReader("C:\\Users\\Hardpc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\js\\allPopUps.js");
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
        User user = userCurr(model, userRepository);
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
        User user = userCurr(model, userRepository);
        model.addAttribute("user", user);
        user.setEmail(email);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingName")
    public String updateName (Model model, @RequestParam("name") String name){
        User user = userCurr(model, userRepository);
        model.addAttribute("user", user);
        user.setImie(name);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingLastName")
    public String updateLastName (Model model, @RequestParam("lastName") String lastName){
        User user = userCurr(model, userRepository);
        model.addAttribute("user", user);
        user.setNazwisko(lastName);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/updatingBankAccountNumber")
    public String updateBankAccountNumber(Model model, @RequestParam("bankaccnumb") String bankAccNumb){
        User user = userCurr(model, userRepository);
        model.addAttribute("user", user);
        user.setNrkontabankowego(bankAccNumb);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    //@RequestMapping(value = "deleteAccount", method = RequestMethod.DELETE)
    @PostMapping("/deleteAccount")
    public String deleteAccount (Model model){
        User user = userCurr(model, userRepository);
        model.addAttribute("user", user);
        userRepository.delete(user);
        SecurityContextHolder.clearContext();
        return "redirect:/profileSettings";
    }


    @PostMapping("/updatingProfileDesc")
    public String updateProfileDescription (Model model, @RequestParam("profileDescription") String description){
        User user = userCurr(model, userRepository);
        model.addAttribute("user", user);
        user.setProfileDescription(description);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }

    @PostMapping("/uploadProfilePicture")
    public String updateProfilePicture (Model model, @RequestParam("profilePicture") MultipartFile image){

        User user = userCurr(model, userRepository);

        String photoExtension = image.getOriginalFilename().toString();
        photoExtension = photoExtension.substring(photoExtension.length() - 3);

        StringBuilder builder = new StringBuilder();

        if(Objects.equals(photoExtension,"jpg"))
            builder.append("C:\\Users\\Hardpc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images\\profpic\\user" + user.getId().toString()  + ".jpg");
        else
            builder.append("C:\\Users\\Hardpc\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images\\profpic\\user" + user.getId().toString()  + ".png");

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

        user.setProfilePicPath(path);
        userRepository.save(user);
        model.addAttribute("user", user);

        return "redirect:/profileSettings";
    }


    @RequestMapping("/updatingProfilePrivacy")
    String userPrivacy(Model model,  @RequestParam(value = "priviet", required = false) String priviet)
    {

        User user = userCurr(model, userRepository);
        model.addAttribute("user", user);
        System.out.println(user);
        user.setPrywatnosckonta(priviet);
        userRepository.save(user);
        return "redirect:/profileSettings";
    }


    @PostMapping("/uploadUserPosition")
    String userPosition(Model model, @RequestParam(value = "lat") Double lat, @RequestParam(value = "lng") Double lng)
    {
        User user = userCurr(model, userRepository);
        if(Objects.isNull(lat) || Objects.isNull(lng))
        {
            model.addAttribute("lat", 21.017532);
            model.addAttribute("lng", 52.237049);
        }

        user.setMapsCenterLatitude(lat);
        user.setMapsCenterLongitude(lng);
        userRepository.save(user);


        return "redirect:/profileSettings";
    }
}




