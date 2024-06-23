package fyodor.dev.coremicroservice.domain.image;

import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.chat.Message;
import fyodor.dev.coremicroservice.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "images")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "minio_link", nullable = false)
    private String minioLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private ImageType type;

    @OneToOne(mappedBy = "image", fetch = FetchType.LAZY)
    private User user;

    @ManyToMany(mappedBy = "images", fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToMany(mappedBy = "images", fetch = FetchType.LAZY)
    private List<Message> messages;

}
