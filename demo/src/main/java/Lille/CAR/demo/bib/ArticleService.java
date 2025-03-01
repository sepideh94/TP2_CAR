package Lille.CAR.demo.bib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ArticleService implements ArticleItf {
    
    @Autowired
    private ArticleRepository articleRepo;

    public ArticleService(ArticleRepository articleRepository ) {
        this.articleRepo = articleRepository;
    }  
    
    @Override
    public List<Article> getOrderArticles(Order order) {
        return articleRepo.findByOrder(order);
    }
    
    @Override
    public void addArticle(Order order, String name, int quantity, double price) {
        Article article = new Article(order, name, quantity, price);
        articleRepo.save(article);       
    }
    
   @Override
    public void deleteArticle(Long articleId) {
        articleRepo.deleteById(articleId);
    }
    
}




