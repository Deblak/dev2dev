package co.simplon.dev2dev_business.dtos;

import co.simplon.dev2dev_business.dtos.validators.UniqueLink;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record ArticleShare(@NotBlank @Size(max = 3000) @URL @UniqueLink String link) {
}
