package Lille.CAR.demo.bib;

import java.util.List;

public interface UserItf {
	List<User> findAll(); 
    User register(User user);
    User login(String email, String password);
}
