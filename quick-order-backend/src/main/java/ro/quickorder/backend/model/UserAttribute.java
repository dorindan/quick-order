package ro.quickorder.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "user_attribute")
public class UserAttribute {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

