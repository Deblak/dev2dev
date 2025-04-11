package co.simplon.dev2dev_business.dtos;

import co.simplon.dev2dev_business.dtos.validators.UniqueLink;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;


import java.time.OffsetDateTime;

public record ArticleRssDto(
        @NotBlank @Length(max=255) String title,
        String description,
        @NotBlank @Length(max=2300) @UniqueLink @Pattern(regexp = "^https?:/{2}[^<>/\\s]+(?:/[^<>/\\s]+)*$",flags= Pattern.Flag.CASE_INSENSITIVE, message = "Url provided not valid. Please try again") String link,
        OffsetDateTime publishedDate) {
}
