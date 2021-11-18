package com.example.RejestracjaLogowanie.Controller;

import com.example.RejestracjaLogowanie.User;
import com.example.RejestracjaLogowanie.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

@Controller
public class ProfileController {



    UserRepository userRepository;
    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getProfilePic() /*w zaleznosci od id usera- finduserbyid i wtedy po folderach patrzymy*/
    {
        File picpath=new File("C:\\Users\\User\\Desktop\\Inzynierka\\src\\main\\resources\\static\\images\\profpic\\profpic.jpg");
        boolean doespicexists = picpath.exists();
        if(picpath.isFile())
        {
            System.out.println(picpath);
            File pic= new File("/images/profpic/profpic.jpg");
            return pic.toString();}
        else
        {
            File picnone=new File("/images/profpic/nopicture.jpg"); /*trzeba brac pod uwage ze cos sie zepsuje i tego tez nie bedzie*/
            return picnone.toString();
        }

    }

    private void userdata(Model model, UserRepository userRepository)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String login = auth.getName();
        User user=userRepository.findUserByLogin(login);
        model.addAttribute("login", user.getLogin());
        model.addAttribute("money", user.getSaldo());
        model.addAttribute("id", user.getId());
        File directory=new File("src/main/resources/static/images/user"+user.getId());
        if(directory.list()!=null) {
            String[] imagename = Objects.requireNonNull(directory.list());
            model.addAttribute("photos", imagename);
            System.out.println(Arrays.toString(imagename));

        }
    }
    @GetMapping("/profile")
    public  String display(Model model)
    {
        userdata(model, userRepository);

        if(!getProfilePic().equals("none"))
            model.addAttribute("profilepic", getProfilePic()); /*nie wiem czy to jest optymalne XD*/
        return "Photo";
    }

}
