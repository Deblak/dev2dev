package co.simplon.dev2dev_business.controllers;

import co.simplon.dev2dev_business.services.ArticleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @GetMapping
    ResponseEntity<Void> getAll(){
        service.findAll();
        System.out.println("find all");
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/test")
    ResponseEntity<Void> test(){
        service.findByLinkIgnoreCase("https://www.lemonde.fr/economie/article/2025/05/12/guerre-commerciale-comment-la-chine-a-fait-plier-donald-trump_6605469_3234.html");
        System.out.println("test");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
