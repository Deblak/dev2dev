package co.simplon.dev2dev_business.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "t_provider")
public class ProviderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "link")
    private String link;

    @Column(name="description")
    private String description;

    @Column(name="last_date_updated")
    private String lastDateUpdated;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastDateUpdated() {
        return lastDateUpdated;
    }

    public void setLastDateUpdated(String lastDateUpdated) {
        this.lastDateUpdated = lastDateUpdated;
    }
}
