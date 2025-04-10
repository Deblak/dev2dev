package co.simplon.dev2dev_business.dtos;

import co.simplon.dev2dev_business.dtos.validators.UniqueUrl;
import co.simplon.dev2dev_business.dtos.validators.UniqueTitle;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

public record ProviderCreationBodyDto(
        @NotBlank @Length(max = 200)  @UniqueTitle String title,
        @NotBlank @Length(max = 1000) String description,
        @NotBlank @Length(max = 400)  @UniqueUrl @Pattern(regexp = "^https?:/{2}[^<>/\\s]+(?:/[^<>/\\s]+)*$",flags= Pattern.Flag.CASE_INSENSITIVE, message = "Url provided not valid. Please try again") String url
) {
}
