package co.simplon.dev2dev_business.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;


import java.time.OffsetDateTime;

public record ArticleRssDto(
        @NotBlank @Length(max=255) String Title,
        String description,
        @NotBlank @Length(max=2300) String link,
        OffsetDateTime publishedDate) {
}
