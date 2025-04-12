package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.components.NotificationManager;
import co.simplon.dev2dev_business.configs.JwtHelper;
import co.simplon.dev2dev_business.dtos.ArticleDtoValid;
import co.simplon.dev2dev_business.dtos.ArticleShareDto;
import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.entities.Article;
import co.simplon.dev2dev_business.entities.ArticleShared;
import co.simplon.dev2dev_business.exceptions.ArticleShareLinkException;
import co.simplon.dev2dev_business.repositories.AccountRepository;
import co.simplon.dev2dev_business.repositories.ArticleRepository;
import co.simplon.dev2dev_business.repositories.ArticleSharedRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class ArticleSharedService {
    private final ArticleRepository articleRepository;
    private final AccountRepository accountRepository;
    private final Validator validator;
    private final NotificationManager notificationManager;
    private final ArticleSharedRepository articleSharedRepository;

    public ArticleSharedService(ArticleRepository articleRepository, AccountRepository accountRepository, Validator validator, NotificationManager notificationManager, ArticleSharedRepository articleSharedRepository) {
        this.articleRepository = articleRepository;
        this.accountRepository = accountRepository;
        this.validator = validator;
        this.notificationManager = notificationManager;
        this.articleSharedRepository = articleSharedRepository;
    }

    @Transactional
    public void createSharedArticle(ArticleShareDto inputs) {
        String link = inputs.link();
        System.out.println("1");
        System.out.println(link);
        String accountEmail = JwtHelper.getSubject();
        System.out.println(accountEmail);
        System.out.println("toangggggggggggggg");

        Account account = accountRepository.findByUsernameIgnoreCase(accountEmail).orElseThrow(() -> new BadCredentialsException(accountEmail));
        System.out.println(account);
        Optional<Article> optionalArticle = articleRepository.findByLinkIgnoreCase(link);
        ArticleShared articleShared = new ArticleShared();
        articleShared.setAccount(account);
        articleShared.setSharedAt(LocalDate.now());

        if (!optionalArticle.isPresent()) {
            System.out.println("pas article save");
            Article article = saveArticle(link);
            articleShared.setArticle(article);
            System.out.println("venir just qu'à d'ici 22222222");
            notificationManager.notifyUsersForArticle(article.getTitle());
        }else {
            System.out.println("have article no save");
            Article articleExits = optionalArticle.get();
            articleShared.setArticle(articleExits);
//            notificationManager.notifyUsersForArticle(articleExits.getTitle());
        }
        System.out.println("venir just qu'à d'ici 3333333");
        System.out.println(articleShared);
        articleSharedRepository.save(articleShared);
//        notificationManager.notifyUsersForArticle(article.getTitle());
        System.out.println("venir just qu'à d'ici 4444444");

    }

    private Article saveArticle(String link) {
        Document doc = null;
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
        System.out.println("test save save");

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
            article.setPublishedDate(null);
            System.out.println("save save save");
            System.out.println(article);
            articleRepository.save(article);
            System.out.println("venir just qu'à d'ici");
            //notificationManager.notifyUsersForArticle(article.getTitle());
            return article;
        }
    }
}
