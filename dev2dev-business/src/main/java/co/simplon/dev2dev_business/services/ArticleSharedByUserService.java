package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.components.NotificationManager;
import co.simplon.dev2dev_business.configs.JwtHelper;
import co.simplon.dev2dev_business.dtos.ArticleDtoValid;
import co.simplon.dev2dev_business.dtos.ArticleShareDto;
import co.simplon.dev2dev_business.entities.Account;
import co.simplon.dev2dev_business.entities.Article;
import co.simplon.dev2dev_business.entities.ArticleShared;
import co.simplon.dev2dev_business.exceptions.ArticleShareLinkException;
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
public class ArticleSharedByUserService {
    private final ArticleSharedService articleSharedService;
    private final ArticleService articleService;
    private final AccountService accountService;
    private final NotificationManager notificationManager;
    private final Validator validator;

    public ArticleSharedByUserService(ArticleSharedService articleSharedService, ArticleService articleService, AccountService accountService, NotificationManager notificationManager, Validator validator) {
        this.articleSharedService = articleSharedService;
        this.articleService = articleService;
        this.accountService = accountService;
        this.notificationManager = notificationManager;
        this.validator = validator;
    }

    @Transactional
    public void createSharedArticle(final ArticleShareDto inputs){
        final String link = inputs.link();
        String accountEmail = JwtHelper.getSubject();

        Account account = accountService.findByUsernameIgnoreCase(accountEmail).orElseThrow(()-> new BadCredentialsException(accountEmail));

        Optional<Article> optionalArticle = articleService.findByLinkIgnoreCase(link);
        ArticleShared articleShared = new ArticleShared();
        articleShared.setAccount(account);
        articleShared.setSharedAt(LocalDate.now());

        if (optionalArticle.isEmpty()) {
            Article article = saveArticle(link);
            articleShared.setArticle(article);
            notificationManager.notifyUsersForArticle(article.getTitle());
        }else{
            Article articleExits = optionalArticle.get();
            articleShared.setArticle(articleExits);
        }
        articleSharedService.save(articleShared);
}

    private Article saveArticle(String link) {
        Document doc = null;
        try {
            doc = Jsoup.connect(link).get();
        } catch (IOException e) {
//            throw new RuntimeException(e); //this ex will return error 401 maybe because filtre security
            throw new ArticleShareLinkException("Link is not correct", e);
        }

        String title = getInfoOgtag(doc, "title]");
        String img = getInfoOgtag(doc, "img]");
        String description = getInfoOgtag(doc, "description]");
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
            articleService.save(article);
            System.out.println("venir just qu'à d'ici");
            return article;
        }
    }

    private static String getInfoOgtag(Document doc, String info) {
        Elements titleElements = doc.select("meta[property=og:"+info);
        String title = titleElements.attr("content");
        return title;
    }
    }
