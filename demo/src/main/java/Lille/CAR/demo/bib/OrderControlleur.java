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
public class OrderControlleur {
    
    @Autowired
    private OrderService orderService;    
  
    
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
        if (order == null || !order.getUser().getId().equals(user.getId())) {
            return new ModelAndView("redirect:/store/commande");
        }

        List<Article> articles = order.getArticles();

        ModelAndView modelAndView = new ModelAndView("articleDetails");
        modelAndView.addObject("order", order);
        modelAndView.addObject("articles", articles);

        return modelAndView;
    }
}