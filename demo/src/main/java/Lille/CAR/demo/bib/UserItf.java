package Lille.CAR.demo.bib;

import java.util.List;

public interface UserItf {
	List<User> findAll(); 
    User register(User user);
    User login(String email, String password);
    
    List<Order> getUserOrders(User user);
    Order createOrder(User user, String title);
    Order getOrderById(Long id);
    
    List<Article> getOrderArticles(Order order);
    Article addArticle(Order order, String name, int quantity, double price); 
    void deleteArticle(Long id);
   
}
