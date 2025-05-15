package co.simplon.dev2dev_business.dtos;

import org.hibernate.validator.constraints.URL;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ArticleShareDto(@NotBlank @Size(max = 3000) @URL String link) {
}
