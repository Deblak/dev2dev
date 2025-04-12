package co.simplon.dev2dev_business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "articles_accounts")
public class ArticleShared {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "shared_at")
    private LocalDate sharedAt;

    public ArticleShared(Long id, Article article, Account account, LocalDate sharedAt) {
        this.id = id;
        this.article = article;
        this.account = account;
        this.sharedAt = sharedAt;
    }

    public Long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public Account getAccount() {
        return account;
    }

    public LocalDate getSharedAt() {
        return sharedAt;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setSharedAt(LocalDate sharedAt) {
        this.sharedAt = sharedAt;
    }
}
//LocalDate sharedAt = LocalDate.now();