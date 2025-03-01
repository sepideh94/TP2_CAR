package Lille.CAR.demo.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrderService implements OrderItf {

    @Autowired
    private OrderRepository orderRepo;

    public OrderService(OrderRepository orderRepository ) {
        this.orderRepo = orderRepository;
    }  
       
    @Override
    public Order createOrder(User user, String title) {
        Order order = new Order(user, title);
        return orderRepo.save(order);
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return orderRepo.findByUser(user);
    }

    public List<Order> findAllOrders() {
        return orderRepo.findAll();
    }
    
    @Override
    public Order getOrderById(Long id) {
        return orderRepo.findById(id).orElse(null);
    }
    
}




