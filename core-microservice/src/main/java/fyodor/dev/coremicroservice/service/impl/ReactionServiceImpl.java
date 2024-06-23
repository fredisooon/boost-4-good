package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.feed.Reaction;
import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    @Override
    public Reaction createReaction(Post post, User user, ReactionType type) {
        Reaction reaction = new Reaction();
        reaction.setPost(post);
        reaction.setUser(user);
        reaction.setType(type);
        return reaction;
    }

    @Override
    public void updateReaction(Reaction reaction, ReactionType type) {
        reaction.setType(type);
    }
}