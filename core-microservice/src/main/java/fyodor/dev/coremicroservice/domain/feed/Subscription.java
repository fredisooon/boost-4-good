package fyodor.dev.coremicroservice.domain.feed;


import fyodor.dev.coremicroservice.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_creator_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_subscriber_id", nullable = false)
    private User subscriber;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_definition_id", nullable = false)
    private SubscriptionDefinition subscriptionDefinition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionType type;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(nullable = false)
    private Integer cost;

    @Column(nullable = false)
    private Integer period;
}
