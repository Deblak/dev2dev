package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.components.Notification;
import co.simplon.dev2dev_business.dtos.ArticleDtoValid;
import co.simplon.dev2dev_business.dtos.ArticleShare;
import co.simplon.dev2dev_business.entities.Article;
import co.simplon.dev2dev_business.exceptions.ArticleShareLinkException;
import co.simplon.dev2dev_business.repositories.ArticleRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;


@Service
public class ArticleService {
    private final ArticleRepository repository;
    private final Notification notification;
    private final Validator validator;

    public ArticleService(ArticleRepository repository, Notification notification, Validator validator) {
        this.repository = repository;
        this.notification = notification;
        this.validator = validator;
    }

    @Transactional
    public void createSharedArticle(ArticleShare inputs) {
        Document doc = null;
        String link = inputs.link();
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
//            throw new RuntimeException(e); //this ex will return error 401 maybe because filtre security
            throw new ArticleShareLinkException("Link is not correct", e);
        }

        Elements titleElements = doc.select("meta[property=og:title]");
        String title = titleElements.attr("content");
        Elements imgElements = doc.select("meta[property=og:img]");
        String img = imgElements.attr("content");
        Elements descElements = doc.select("meta[property=og:description]");
        String description = descElements.attr("content");
        LocalDate sharedAt = LocalDate.now();

        ArticleDtoValid articleDto = new ArticleDtoValid(title);
        Set<ConstraintViolation<ArticleDtoValid>> violations = validator.validate(articleDto);
        if (!violations.isEmpty()){
            //need this part for debug
//            for (ConstraintViolation<ArticleDtoValid> violation : violations) {
//                System.out.println("PROGRAMMATIC VALIDATION");
//                System.out.println(violation.getMessage());
//                System.out.println("--------------------------");
//            }
           throw new ConstraintViolationException(violations);
        }else {
            Article article = new Article();
            article.setLink(link);
            article.setTitle(title);
            article.setDescription(description);
            article.setImage(img);
            article.setSharedAt(sharedAt);
            article.setPublishedDate(null);
            repository.save(article);
            notification.create(article.getTitle());
        }
    }

    public boolean existsByLink(String link){
        return repository.existsByLinkIgnoreCase(link);
    }
}
