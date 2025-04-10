package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.dtos.LastUpdatedDateDto;
import co.simplon.dev2dev_business.dtos.ProviderCreationBodyDto;
import co.simplon.dev2dev_business.dtos.ProviderCreationResponseDto;
import co.simplon.dev2dev_business.jparepositories.ProviderJpaRepository;
import co.simplon.dev2dev_business.mappers.ProviderMapper;
import co.simplon.dev2dev_business.utils.DateUtils;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import jakarta.validation.ConstraintViolation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class ProviderService {

    private final ProviderJpaRepository providerJpaRepository;

    public ProviderService(ProviderJpaRepository providerJpaRepository){
        this.providerJpaRepository = providerJpaRepository;
    }

    @Transactional(readOnly = true)
    public ProviderCreationResponseDto create(ProviderCreationBodyDto body) throws IOException {
        try {
            RssReader rssReader = new RssReader();
            // un flux paresseux (lazy)qui ne s'execute que lorsqu'elle est consommées (Collection() / findfirst() etc)
            Stream<Item> rssFeed = rssReader.read(body.url());
            Optional<String> lastUpdateDate = rssFeed.findFirst()
                    .flatMap(item -> item.getChannel().getPubDate());
            OffsetDateTime getLastUpdate = null;
                String lastUpdate = lastUpdateDate.get();
                getLastUpdate = DateUtils.convertStringToOffsetDateTime(lastUpdate);
                System.out.println("this is the value of time : " + getLastUpdate);
                //Implemnter la lecture des articles
            rssFeed.close();
            rssFeed =rssReader.read(body.url());
            HashMap<String,Optional<String>> storeArticles = new HashMap<>();
//            rssFeed.map(item ->
//                    String toto = item.getTitle();
//            String author = item.getAuthor() != null ? item.getAuthor() : "Auteur inconnu";
//                    storeArticles.put("author", item.getAuthor()),
//                    storeArticles.put("t", item.getLinks())
//                    )


                //fin
            providerJpaRepository.save(ProviderMapper.toProviderEntity(body, ProviderMapper.toLastUpdatedDateDto(getLastUpdate)));
            return ProviderMapper.toProviderCreationResponse(ProviderMapper.toProviderEntity(body, ProviderMapper.toLastUpdatedDateDto(getLastUpdate)));
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException("RSS reading error.Cannot parse the link. Try again with a valid link", e);
        }

    }

    public void valide(LastUpdatedDateDto lastUpdatedDateDto) {

    }
    public boolean existsByTitle(String value) {
        Boolean isTrue =  providerJpaRepository.existsByTitleIgnoreCase(value);
        System.out.println(isTrue);
        return isTrue;
    }
    public boolean existsByLink(String value) {
        Boolean isTrue =  providerJpaRepository.existsByLinkIgnoreCase(value);
        System.out.println(isTrue);
        return isTrue;

    }
}
