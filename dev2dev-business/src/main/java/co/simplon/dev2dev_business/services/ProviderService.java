package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.dtos.ArticleRssDto;
import co.simplon.dev2dev_business.dtos.ProviderCreationBodyDto;
import co.simplon.dev2dev_business.jparepositories.ProviderJpaRepository;
import co.simplon.dev2dev_business.mappers.ArticleMapper;
import co.simplon.dev2dev_business.mappers.ProviderMapper;
import co.simplon.dev2dev_business.repositories.ArticleRepository;
import co.simplon.dev2dev_business.utils.DateUtils;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.*;


@Service
@Transactional(readOnly = true)
public class ProviderService {

    private final ProviderJpaRepository providerJpaRepository;
    private final ArticleRepository articleRepository;
    private final Validator validator;

    public ProviderService(ProviderJpaRepository providerJpaRepository, ArticleRepository articleRepository, Validator validator){
        this.providerJpaRepository = providerJpaRepository;
        this.articleRepository = articleRepository;
        this.validator = validator;
    }

    @Transactional
    public void create(ProviderCreationBodyDto body) throws IOException {
        try {
            RssReader rssReader = new RssReader();
            // un flux paresseux (lazy)qui ne s'execute que lorsqu'elle est consommées (Collection() / findfirst() etc)
            List<Item> rssFeed = rssReader.read(body.url()).toList();
            System.out.println(rssFeed);
            List<Map<String,Optional<String>>> storeArticles = new ArrayList<>();
            Optional<String> lastUpdateDate = rssFeed.stream().findFirst()
                    .flatMap(item -> item.getChannel().getPubDate());
            System.out.println(lastUpdateDate);
             rssFeed
                    .forEach(item -> {
                        Map<String, Optional<String>> itemData = new HashMap<>();
                        itemData.put("Title", item.getTitle());
                        itemData.put("Description", item.getDescription());
                        itemData.put("Link", item.getLink()); //vérifier si le lien exists deja
                        itemData.put("Published", item.getPubDate());
                        storeArticles.add(itemData);
                    });
            verifyValidArticles(ProviderMapper.toArticleDtosList(storeArticles));
            OffsetDateTime getLastUpdate = null;
                String lastUpdate = lastUpdateDate.get();
                getLastUpdate = DateUtils.convertStringToOffsetDateTime(lastUpdate);
                System.out.println("this is the value of time : " + getLastUpdate);
            providerJpaRepository.save(ProviderMapper.toProviderEntity(body, ProviderMapper.toLastUpdatedDateDto(getLastUpdate)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("RSS reading error.Cannot parse the link. Try again with a valid link", e);
        }

    }
    public void verifyValidArticles(List<ArticleRssDto> articleDtos) {//Modifer avec le dto article
        for(ArticleRssDto articleDto : articleDtos) {
            Set<ConstraintViolation<ArticleRssDto>> constraintViolations = validator.validate(articleDto);
            if(!constraintViolations.isEmpty()) {
                throw new ConstraintViolationException(constraintViolations);
            }else {
                 articleRepository.save(ArticleMapper.toArticleEntity(articleDto));
            }
        }
    }
    public boolean existsByTitle(String value) {
        Boolean isTrue =  providerJpaRepository.existsByTitleIgnoreCase(value);
        System.out.println(isTrue);
        return isTrue;
    }
    public boolean existsByRssLink(String value) {
        Boolean isTrue =  providerJpaRepository.existsByLinkIgnoreCase(value);
        System.out.println(isTrue);
        return isTrue;
    }
    public boolean existsByLink(String link){
    Boolean isTrue = articleRepository.existsByLinkIgnoreCase(link);
        System.out.println(isTrue);
        return isTrue;

    }
}
