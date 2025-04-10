package co.simplon.dev2dev_business.mappers;

import co.simplon.dev2dev_business.dtos.LastUpdatedDateDto;
import co.simplon.dev2dev_business.dtos.ProviderCreationBodyDto;
import co.simplon.dev2dev_business.dtos.ProviderCreationResponseDto;
import co.simplon.dev2dev_business.entities.ProviderEntity;

import java.time.OffsetDateTime;

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
    public static ProviderCreationResponseDto toProviderCreationResponse(ProviderEntity providerEntity) {
        ProviderCreationResponseDto providerCreationResponseDto = new ProviderCreationResponseDto(providerEntity.getTitle(), providerEntity.getDescription(), providerEntity.getLink(),providerEntity.getLastDateUpdated());
        return providerCreationResponseDto;
    }


    public static ProviderEntity toLastUpdateDateEntity(OffsetDateTime lastUpdateDate) {
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setLastDateUpdated(lastUpdateDate);
        return providerEntity;
    }
}
