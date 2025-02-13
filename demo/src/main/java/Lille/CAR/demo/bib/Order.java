package Lille.CAR.demo.bib;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")  
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  
    private User user;

    private String title;  

    public Order() {}

    public Order(User user, String title ) {
        this.user = user;
        this.title = title;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}

