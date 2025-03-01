package Lille.CAR.demo.bib;

import jakarta.persistence.*;

@Entity
@Table(name = "articles")  
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    private String name;
    private int quantity;
    private double price;
    
    public Article() {}

    public Article(Order order, String name, int quantity, double price) {
        this.order = order;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
