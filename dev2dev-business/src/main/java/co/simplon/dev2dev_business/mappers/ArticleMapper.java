package co.simplon.dev2dev_business.mappers;

import co.simplon.dev2dev_business.dtos.ArticleRssDto;
import co.simplon.dev2dev_business.entities.Article;

public class ArticleMapper {

    public static Article toArticleEntity(ArticleRssDto articleRssDto) {
        Article article = new Article();
        article.setTitle(articleRssDto.title());
        article.setDescription(articleRssDto.description());
        article.setLink(articleRssDto.link());
        article.setPublishedDate(articleRssDto.publishedDate());
        return article;
    }
}
