package fyodor.dev.coremicroservice.domain.feed;

import fyodor.dev.coremicroservice.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "reactions")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ReactionType type;

    public Reaction(Post post, User user, ReactionType type) {
        this.post = post;
        this.user = user;
        this.type = type;
    }
}