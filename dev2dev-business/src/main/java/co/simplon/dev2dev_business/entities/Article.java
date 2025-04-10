package co.simplon.dev2dev_business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "t_articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }
    private void setId(long id) {
        this.id = id;
    }

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "shared_at")
    private LocalDate sharedAt;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "author")
    private String author;

    public Article() {
        //ORM
    }

    public Article(String url, String title, String description, String image, LocalDate sharedAt, LocalDate publishedDate, String author) {
        this.url = url;
        this.title = title;
        this.description = description;
        this.image = image;
        this.sharedAt = sharedAt;
        this.publishedDate = publishedDate;
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public LocalDate getSharedAt() {
        return sharedAt;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSharedAt(LocalDate sharedAt) {
        this.sharedAt = sharedAt;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", sharedAt=" + sharedAt +
                ", publishedDate=" + publishedDate +
                ", author='" + author + '\'' +
                '}';
    }
}
