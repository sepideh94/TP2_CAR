package Lille.CAR.demo.bib;

import java.util.List;

public interface OrderItf {
    
    List<Order> getUserOrders(User user);
    Order createOrder(User user, String title);
    Order getOrderById(Long id);
    
}