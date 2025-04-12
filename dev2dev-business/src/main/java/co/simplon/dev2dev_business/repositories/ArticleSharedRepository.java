package co.simplon.dev2dev_business.repositories;

import co.simplon.dev2dev_business.entities.ArticleShared;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSharedRepository extends JpaRepository<ArticleShared, Long> {
}
