package co.simplon.dev2dev_business.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import co.simplon.dev2dev_business.dtos.ArticleShareProjectionDto;
import co.simplon.dev2dev_business.entities.ArticleShared;

@Repository
public interface ArticleSharedRepository extends JpaRepository<ArticleShared, Long> {
	@Query(value = """
			SELECT a.link AS link,
				   a.description AS description,
				   taa.shared_at  AS sharedDate
			From t_articles a
			JOIN t_articles_accounts taa ON a.id = taa.article_id
			WHERE taa.shared_at IS NOT NULL
			""", nativeQuery = true)

	List<ArticleShareProjectionDto> findAllSharedArticles();
    @Query("""
        select count(*)> 0 from ArticleShared at
        where at.article.link = :link
        and at.account.username = :accountEmail
    """)
    boolean existByLinkAndAccountEmail(@Param(value = "link") String link,@Param(value = "accountEmail") String accountEmail);
}
