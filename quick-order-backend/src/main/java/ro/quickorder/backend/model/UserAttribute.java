package ro.quickorder.backend.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "user_attribute")
public class UserAttribute {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(updatable = false, nullable = false)
    private String id;
    @Enumerated(EnumType.STRING)
    private Language language;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public UserAttribute(Language language) {
        this.language = language;
    }

    public UserAttribute(){
        this.language = Language.EN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

