package Lille.CAR.demo.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserItf {
    
    @Autowired
    private UserRepository repo;

    public UserService(UserRepository userRepository) {
        this.repo = userRepository;
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
}
