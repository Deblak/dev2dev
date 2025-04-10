package co.simplon.dev2dev_business.dtos;

import java.time.OffsetDateTime;

public record ProviderCreationResponseDto(
        String title,
        String description,
        String url,
        OffsetDateTime lastDateUpdated
) {
}
