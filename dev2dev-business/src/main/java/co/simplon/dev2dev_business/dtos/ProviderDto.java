package co.simplon.dev2dev_business.dtos;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record ProviderDto(
        @NotBlank @Length(max = 200) String title,
        @NotBlank @Length(max = 1000) String description,
        @NotBlank @Length(max = 400) String url
) {
}
