package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.io.File;

@Controller
public class RegistrationController{

    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(RegistrationController.class);


    @GetMapping("/registration")
    public String register(Model model)
    {

        return "Registration";

    }

    private Long getIdFromRepository(User user, UserRepository userRepository)
    {
        User userdata = userRepository.findUserByLogin(user.getLogin());
        return userdata.getId();

    }

   private void createDirectory(Long id)
   {

       String path = "src/main/resources/static/images/";

       File directory = new File(path+"user"+id);
       if (!directory.exists()) {
           if (directory.mkdir()) {
               System.out.println("Directory is created.");
           } else {
               System.out.println("Failed to create directory.");
           }
       }
   }

    @PostMapping("/process_register")
    public String send(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("Sa bledy lmao");
            return "process_success";
        }
        else {

            hashPasswd(user);
            log.info(">> User : {}", user.toString());
            userRepository.save(user);

            Long id=getIdFromRepository(user, userRepository);
            createDirectory(id);

            return "Login";
        }
    }


    public void hashPasswd(User user){
        user.setHashhasla(passwordEncoder.encode(user.getHashhasla()));

    }



}