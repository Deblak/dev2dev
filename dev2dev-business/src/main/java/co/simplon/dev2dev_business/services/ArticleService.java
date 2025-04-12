package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.components.NotificationManager;
import co.simplon.dev2dev_business.configs.JwtHelper;
import co.simplon.dev2dev_business.dtos.ArticleDtoValid;
import co.simplon.dev2dev_business.dtos.ArticleShareDto;
import co.simplon.dev2dev_business.entities.Account;
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
import java.util.Set;


@Service
public class ArticleService {
    private final ArticleRepository repository;

    public ArticleService(ArticleRepository repository) {
        this.repository = repository;
    }

//    @Transactional
//    public void createSharedArticle(ArticleShareDto inputs) {
//        Document doc = null;
//        String link = inputs.link();
//        try {
//            doc = Jsoup.connect(link).get();
//        } catch (IOException e) {
////            throw new RuntimeException(e); //this ex will return error 401 maybe because filtre security
//            throw new ArticleShareLinkException("Link is not correct", e);
//        }
//
//        Elements titleElements = doc.select("meta[property=og:title]");
//        String title = titleElements.attr("content");
//        Elements imgElements = doc.select("meta[property=og:img]");
//        String img = imgElements.attr("content");
//        Elements descElements = doc.select("meta[property=og:description]");
//        String description = descElements.attr("content");
//
//        ArticleDtoValid articleDto = new ArticleDtoValid(title);
//        Set<ConstraintViolation<ArticleDtoValid>> violations = validator.validate(articleDto);
//        if (!violations.isEmpty()){
//            //need this part for debug
////            for (ConstraintViolation<ArticleDtoValid> violation : violations) {
////                System.out.println("PROGRAMMATIC VALIDATION");
////                System.out.println(violation.getMessage());
////                System.out.println("--------------------------");
////            }
//           throw new ConstraintViolationException(violations);
//        }else {
//            String userEmail = JwtHelper.getSubject();
////            Account account =
//            Article article = new Article();
//            article.setLink(link);
//            article.setTitle(title);
//            article.setDescription(description);
//            article.setImage(img);
//            article.setPublishedDate(null);
//            repository.save(article);
//            notificationManager.notifyUsersForArticle(article.getTitle());
//        }
//    }

    public boolean existsByLink(String link){
        Boolean istrue = repository.existsByLinkIgnoreCase(link);
        System.out.println(istrue);
        return istrue;
//        return repository.existsByLinkIgnoreCase(link);
    }
}
