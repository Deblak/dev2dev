package co.simplon.dev2dev_business.controllers;

import co.simplon.dev2dev_business.dtos.ArticleShare;
import co.simplon.dev2dev_business.services.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService service;

    public ArticleController(ArticleService service) {
        this.service = service;
    }

    @PostMapping("/share")
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<Void> createSharedArticle(@RequestBody @Valid ArticleShare inputs){
        service.createSharedArticle(inputs);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
