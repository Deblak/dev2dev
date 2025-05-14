package co.simplon.dev2dev_business.repositories;

import co.simplon.dev2dev_business.entities.ArticleShared;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleSharedRepository extends JpaRepository<ArticleShared, Long> {
    @Query("""
        select count(*)> 0 from ArticleShared at
        where at.article.link = :link
        and at.account.username = :accountEmail
    """)
    boolean existByLinkAndAccountEmail(@Param(value = "link") String link,@Param(value = "accountEmail") String accountEmail);
}
