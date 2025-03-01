package Lille.CAR.demo.bib;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByOrder(Order order);
    Article findById(long id); 
}