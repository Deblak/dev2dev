package co.simplon.dev2dev_business.dtos;

import jakarta.validation.constraints.NotBlank;

public record ArticleDtoValid(@NotBlank(message = "The URL is invalid because it lacks the article title.") String title) {
}
