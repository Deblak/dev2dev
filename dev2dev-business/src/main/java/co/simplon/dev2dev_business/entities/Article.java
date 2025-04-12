package co.simplon.dev2dev_business.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "link")
    private String link;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "image")
    private String image;

    @Column(name = "published_date")
    private OffsetDateTime publishedDate;

    @OneToMany(mappedBy = "article", orphanRemoval = true)
    List<ArticleShared> articleShareds = new ArrayList<>();

    public Article() {
        //ORM
    }

    public Article(Long id, String link, String title, String description, String image, OffsetDateTime publishedDate) {
        this.id = id;
        this.link = link;
        this.title = title;
        this.description = description;
        this.image = image;
        this.publishedDate = publishedDate;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public OffsetDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(OffsetDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getImage() {
        return image;
    }

    public void setLink(String link) {
        this.link = link;
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

    public List<ArticleShared> getArticleShareds() {
        return articleShareds;
    }

    public void setArticleShareds(List<ArticleShared> articleShareds) {
        this.articleShareds = articleShareds;
    }

    @Override
    public String toString() {
        return "Article{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }


}
