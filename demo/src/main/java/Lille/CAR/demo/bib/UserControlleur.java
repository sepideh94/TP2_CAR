package Lille.CAR.demo.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/store")
public class UserControlleur {

    @Autowired
    private UserItf service;
    

    @GetMapping("/home")
    public ModelAndView home(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView("home");
        if (error != null) {
            modelAndView.addObject("error", "Email ou mot de passe incorrect!");
        }
        return modelAndView;
    }

    @PostMapping("/signup")
    public RedirectView signup(@RequestParam String email, 
                               @RequestParam String password, 
                               @RequestParam String firstName, 
                               @RequestParam String lastName) {
        User newUser = new User(email, password, firstName, lastName);
        service.register(newUser);
        return new RedirectView("/store/home");
        
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email, @RequestParam String password, HttpSession session) {
        User user = service.login(email, password);
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
    
    @GetMapping("/commande")
    public ModelAndView commandPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/store/home");
        }
        ModelAndView modelAndView = new ModelAndView("commande");
        
        List<Order> orders = service.getUserOrders(user);
        
        modelAndView.addObject("orders", orders);
        return modelAndView;
    }
 
    @PostMapping("/commande/add")
    public RedirectView placeOrder(HttpSession session, 
    		                       @RequestParam String title) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new RedirectView("/store/home");
        }
        service.createOrder(user, title);
        return new RedirectView("/store/commande");
    }
    
    @PostMapping("/article")
    public RedirectView addArticle(HttpSession session, 
                                   @RequestParam Long orderId, 
                                   @RequestParam String name, 
                                   @RequestParam int quantity,
                                   @RequestParam double price) {
    	
    User user = (User) session.getAttribute("user");
    Order order = service.getOrderById(orderId);

    service.addArticle(order, name, quantity, price);
    return new RedirectView("/store/commande");
    }

    

}
