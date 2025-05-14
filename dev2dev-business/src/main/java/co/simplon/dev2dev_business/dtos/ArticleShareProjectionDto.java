package co.simplon.dev2dev_business.dtos;

import java.time.LocalDate;

public record ArticleShareProjectionDto(String link, String title, String description, LocalDate sharedDate) {

}
