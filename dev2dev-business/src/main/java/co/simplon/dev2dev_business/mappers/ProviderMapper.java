package co.simplon.dev2dev_business.mappers;

import co.simplon.dev2dev_business.dtos.ArticleRssDto;
import co.simplon.dev2dev_business.dtos.LastUpdatedDateDto;
import co.simplon.dev2dev_business.dtos.ProviderCreationBodyDto;
import co.simplon.dev2dev_business.entities.ProviderEntity;
import co.simplon.dev2dev_business.utils.DateUtils;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProviderMapper {

    public static ProviderEntity toProviderEntity(ProviderCreationBodyDto providerCreationBodyDto, LastUpdatedDateDto lastUpdatedDateDto) {
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setTitle(providerCreationBodyDto.title());
        providerEntity.setLink(providerCreationBodyDto.url());
        providerEntity.setDescription(providerCreationBodyDto.description());
        providerEntity.setLastDateUpdated(lastUpdatedDateDto.lastDateUpdatedRetrive());
        return providerEntity;
    }

    public static LastUpdatedDateDto toLastUpdatedDateDto(OffsetDateTime date) {
        LastUpdatedDateDto lastUpdatedDateDto = new LastUpdatedDateDto(date);
        return lastUpdatedDateDto;
    }

    public static List<ArticleRssDto> toArticleDtosList(List<Map<String,Optional<String>>> storeArticles ) {
        List<ArticleRssDto> articleRssDtoList = new ArrayList<>();
        storeArticles.forEach(article -> {
            ArticleRssDto articleRssDto = new ArticleRssDto(
                    article.get("Title").orElse(null),
                    article.get("Description").orElse(null),
                    article.get("Link").orElse(null),
                    DateUtils.convertStringToOffsetDateTime(article.get("Published").orElse(null))
            );
            articleRssDtoList.add(articleRssDto);
        });
        System.out.println(articleRssDtoList);
        return articleRssDtoList;
    }


    public static ProviderEntity toLastUpdateDateEntity(OffsetDateTime lastUpdateDate) {
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setLastDateUpdated(lastUpdateDate);
        return providerEntity;
    }
}
