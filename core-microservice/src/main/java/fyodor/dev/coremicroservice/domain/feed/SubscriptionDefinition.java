package fyodor.dev.coremicroservice.domain.feed;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "subscription_definition")
public class SubscriptionDefinition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer cost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubscriptionLevelType level;

    @Column
    private String description;

    @OneToMany(mappedBy = "subscriptionDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subscription> subscriptions = new ArrayList<>();
}
