package fyodor.dev.coremicroservice.domain.feed;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ReactionType {

    LIKE("like"),
    DISLIKE("dislike");
//    LOVE,
//    WOW,
//    SAD,
//    ANGRY

    private String code;
}
