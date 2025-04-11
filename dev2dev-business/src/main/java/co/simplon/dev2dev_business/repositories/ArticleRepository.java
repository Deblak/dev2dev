package co.simplon.dev2dev_business.repositories;

import co.simplon.dev2dev_business.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByLinkIgnoreCase(String value);
}
