package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.entities.Article;
import co.simplon.dev2dev_business.repositories.ArticleRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

    public boolean existsByLink(String link){
        return repository.existsByLinkIgnoreCase(link);
    }

    public Optional<Article> findByLinkIgnoreCase(String link) {
        return repository.findByLinkIgnoreCase(link);
    }

    public void save(Article article) {
        repository.save(article);
    }

    public void findAll() {
        repository.findAll();
    }
}
