package co.simplon.dev2dev_business.services;

import java.util.List;

import org.springframework.stereotype.Service;

import co.simplon.dev2dev_business.entities.ArticleShared;
import co.simplon.dev2dev_business.repositories.ArticleSharedRepository;

@Service
public class ArticleSharedService {

	private final ArticleSharedRepository articleSharedRepository;

	public ArticleSharedService(ArticleSharedRepository articleSharedRepository) {
		this.articleSharedRepository = articleSharedRepository;
	}

	public void save(ArticleShared articleShared) {
		articleSharedRepository.save(articleShared);
	}

	public List<ArticleShared> findAll() {
		return articleSharedRepository.findAll();
	}

    public boolean existByLinkAndAccountEmail(String link, String accountEmail){
        return articleSharedRepository.existByLinkAndAccountEmail(link, accountEmail);
    }
}
