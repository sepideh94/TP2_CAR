package Lille.CAR.demo.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/store")
public class UserControlleur {
    
    @Autowired
    private UserService userService;      
         
    @GetMapping("/home")
    public ModelAndView home(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("home");
        if (error != null) {
            modelAndView.addObject("error", "Email or password incorrect!");
        }
        return modelAndView;
    }

    @PostMapping("/signup")
    public RedirectView signup(@RequestParam String email, 
                               @RequestParam String password, 
                               @RequestParam String firstName, 
                               @RequestParam String lastName) {
        User newUser = new User(email, password, firstName, lastName);
        userService.register(newUser);
        return new RedirectView("/store/home?success=true");
        
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = userService.login(email, password);
        if (user != null) {
            session.setAttribute("user", user); 
            return new RedirectView("/store/commande");
        }
        return new RedirectView("/store/home?error=true");
    }
    
    @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        session.invalidate(); 
        return new RedirectView("/store/home"); 
    }
}