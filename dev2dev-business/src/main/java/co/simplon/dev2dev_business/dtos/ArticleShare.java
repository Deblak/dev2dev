package co.simplon.dev2dev_business.dtos;

import co.simplon.dev2dev_business.dtos.validators.UniqueUrl;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record ArticleShare(@NotBlank @Size(max = 5) @URL @UniqueUrl String url) {
}
