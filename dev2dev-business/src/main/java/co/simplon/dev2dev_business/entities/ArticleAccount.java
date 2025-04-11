package co.simplon.dev2dev_business.entities;

import jakarta.persistence.Column;

import java.time.LocalDate;

public class ArticleAccount {
    @Column(name = "shared_at")
    private LocalDate sharedAt;
}
//LocalDate sharedAt = LocalDate.now();