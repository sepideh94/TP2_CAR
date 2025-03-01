package Lille.CAR.demo.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/store")
public class UserControlleur {
    
    @Autowired
    private UserService userService;      
    @Autowired
    private OrderService orderService;    
    @Autowired
    private ArticleService articleService;  

    
    
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
        userService.register(newUser);
        return new RedirectView("/store/home");
        
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
    
    @GetMapping("/commande")
    public ModelAndView commandPage(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/store/home");
        }
        ModelAndView modelAndView = new ModelAndView("commande");
        
       List<Order> orders = orderService.getUserOrders(user);

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
        orderService.createOrder(user, title);
        return new RedirectView("/store/commande");
    }
    
    @GetMapping("/commande/{id}")
    public ModelAndView orderDetails(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/store/home");
        }

        Order order = orderService.getOrderById(id);
        if (order == null) {
            return new ModelAndView("redirect:/store/commande");
        }

        List<Article> articles = order.getArticles();

        ModelAndView modelAndView = new ModelAndView("articleDetails");
        modelAndView.addObject("order", order);
        modelAndView.addObject("articles", articles);

        return modelAndView;
    }
    
    @PostMapping("/commande/{orderId}/addArticle")
    public String addArticle(@PathVariable Long orderId,
                             @RequestParam String name,
                             @RequestParam int quantity,
                             @RequestParam double price,
                             HttpSession session) {                   
        Order order = orderService.getOrderById(orderId);
        articleService.addArticle(order, name, quantity, price);
        return "redirect:/store/commande/" + orderId;
    }
    
    @PostMapping("/commande/{orderId}/deleteArticle")
    public String deleteArticle(@PathVariable Long orderId, 
                                @RequestParam Long articleId,
                                HttpSession session) {
    	articleService.deleteArticle(articleId);
        return "redirect:/store/commande/" + orderId;
    }
    
    @GetMapping("/commande/{orderId}/printArticles")
    public ModelAndView printOrderArticles(@PathVariable Long orderId, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new ModelAndView("redirect:/store/home");
        }

        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return new ModelAndView("redirect:/store/commande");
        }

        List<Article> articles = order.getArticles();
        double grandTotal = articles.stream()
                .mapToDouble(article -> article.getQuantity() * article.getPrice())
                .sum();

        ModelAndView modelAndView = new ModelAndView("printArticles");
        modelAndView.addObject("order", order);
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("grandTotal", grandTotal);

        return modelAndView;
    }
    
    
}