package co.simplon.dev2dev_business.mappers;

import co.simplon.dev2dev_business.dtos.ProviderDto;
import co.simplon.dev2dev_business.entities.ProviderEntity;

public class ProviderMapper {

    static ProviderEntity toProviderEntity(ProviderDto providerDto) {
        ProviderEntity providerEntity = new ProviderEntity();
        providerEntity.setTitle(providerDto.title());
        providerEntity.setLink(providerDto.url());
        providerEntity.setDescription(providerDto.description());
        providerEntity.setLastDateUpdated(providerDto.last_date_updated());
        return providerEntity;
    }
}
