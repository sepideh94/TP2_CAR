package Lille.CAR.demo.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpSession;
import java.util.List;


@Controller
@RequestMapping("/store")
public class ArticleControlleur {
    
  
    @Autowired
    private OrderService orderService;    
    @Autowired
    private ArticleService articleService;  

       
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
        if (order == null || !order.getUser().getId().equals(user.getId())) {
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