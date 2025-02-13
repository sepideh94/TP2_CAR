package Lille.CAR.demo.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserItf {

    @Autowired
    private UserRepository repo;
    
    @Autowired
    private OrderRepository orderRepo;
    
    @Autowired
    private ArticleRepository articleRepo;

    public UserService(UserRepository userRepository, OrderRepository orderRepository,
                       ArticleRepository articleRepository ) {
        this.repo = userRepository;
        this.orderRepo = orderRepository;
        this.articleRepo = articleRepository;
    }

    @Override
    public List<User> findAll() { 
        return repo.findAll();
    }

    @Override
    public User register(User user) { 
        return repo.save(user);
    }

    @Override
    public User login(String email, String password) { 
        User user = repo.findByEmail(email); 
        if (user != null && user.getPassword().equals(password)) { 
            return user; 
        }
        return null; 
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
    
    @Override
    public List<Article> getOrderArticles(Order order) {
        return articleRepo.findByOrder(order);
    }

    public Article addArticle(Order order, String name, int quantity, double price) {
        Article article = new Article(order, name, quantity, price);
        return articleRepo.save(article);
    }

    public void deleteArticle(Long articleId) {
        articleRepo.deleteById(articleId);
    }
}
