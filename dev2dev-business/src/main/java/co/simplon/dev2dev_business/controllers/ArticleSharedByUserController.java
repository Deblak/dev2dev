package co.simplon.dev2dev_business.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.simplon.dev2dev_business.dtos.ArticleShareDto;
import co.simplon.dev2dev_business.dtos.ArticleShareProjectionDto;
import co.simplon.dev2dev_business.services.ArticleSharedByUserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/articles")
public class ArticleSharedByUserController {
	private final ArticleSharedByUserService articleSharedByUserService;

	public ArticleSharedByUserController(ArticleSharedByUserService articleSharedByUserService) {
		this.articleSharedByUserService = articleSharedByUserService;
	}

	@PostMapping(value = "/share", consumes = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Void> createSharedArticle(@RequestBody @Valid ArticleShareDto inputs)  {
		articleSharedByUserService.createSharedArticle(inputs);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/share")
	public List<ArticleShareProjectionDto> getAllArticles() {
		// Données de la projection
		List<ArticleShareProjectionDto> projectionList = articleSharedByUserService.getAll();

		return projectionList;
	}

}
