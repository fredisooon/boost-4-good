package fyodor.dev.coremicroservice.domain.user;

import fyodor.dev.coremicroservice.domain.feed.Comment;
import fyodor.dev.coremicroservice.domain.feed.Reaction;
import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.domain.image.Image;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 16, nullable = false)
    private Set<Role> roles = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Image image;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reaction> reactions = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subscription> createdSubscriptions = new HashSet<>();

    @OneToMany(mappedBy = "subscriber", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Subscription> subscribedSubscriptions = new HashSet<>();

    public User() {}

    public User(String login) {
        this.username = login;
    }
}