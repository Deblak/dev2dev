package co.simplon.dev2dev_business.services;

import co.simplon.dev2dev_business.dtos.ProviderDto;
import co.simplon.dev2dev_business.entities.ProviderEntity;
import co.simplon.dev2dev_business.jparepositories.ProviderJpaRepository;
import co.simplon.dev2dev_business.mappers.ProviderMapper;
import org.springframework.stereotype.Service;

@Service
public class ProviderService {

    private final ProviderJpaRepository providerJpaRepository;

    public ProviderService(ProviderJpaRepository providerJpaRepository){
        this.providerJpaRepository = providerJpaRepository;
    }

    public void create(ProviderDto body) {
        providerJpaRepository.findByTitle(body.title()).ifPresent( err -> new RuntimeException("Title Already Exists"));
        providerJpaRepository.findByLink(body.url()).ifPresent( err -> new RuntimeException("Link Already Exists"));

        providerJpaRepository.save(ProviderMapper.toProviderEntity(body));

//        RssReader rssReader = new RssReader();
//        List<Item> items = rssReader.read(body.url())
//                .toList();
//        Stream<Item> rssFeed = rssReader.read(body.url());
//        List<String> channel = rssFeed.map(i -> i.getChannel().getTitle()).toList();
//        System.out.println(items);
//        System.out.println(channel);

    }
}
