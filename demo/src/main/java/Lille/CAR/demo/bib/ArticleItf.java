package Lille.CAR.demo.bib;

import java.util.List;

public interface ArticleItf {
	
	List<Article> getOrderArticles(Order order);
    void addArticle(Order order, String name, int quantity, double price); 
    void deleteArticle(Long id);
    
}