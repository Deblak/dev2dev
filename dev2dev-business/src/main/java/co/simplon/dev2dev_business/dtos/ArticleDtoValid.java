package co.simplon.dev2dev_business.dtos;

import jakarta.validation.constraints.NotBlank;

public record ArticleDtoValid(@NotBlank String title) {
}
