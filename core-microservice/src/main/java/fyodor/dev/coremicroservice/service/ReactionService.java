package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.feed.Reaction;
import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import fyodor.dev.coremicroservice.domain.user.User;

public interface ReactionService {

    Reaction createReaction(Post post, User user, ReactionType type);
    void updateReaction(Reaction reaction, ReactionType type);
}
