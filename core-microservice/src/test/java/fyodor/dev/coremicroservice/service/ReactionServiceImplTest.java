package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.feed.Reaction;
import fyodor.dev.coremicroservice.domain.feed.ReactionType;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.service.impl.ReactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ReactionServiceImplTest {

    private ReactionService reactionService;

    @BeforeEach
    void setUp() {
        reactionService = new ReactionServiceImpl();
    }

    @Test
    void whenCreateReaction_thenReactionIsCreated() {
        Post post = new Post();
        User user = new User();
        ReactionType type = ReactionType.LIKE;

        Reaction reaction = reactionService.createReaction(post, user, type);

        assertNotNull(reaction);
        assertEquals(post, reaction.getPost());
        assertEquals(user, reaction.getUser());
        assertEquals(type, reaction.getType());
    }

    @Test
    void whenUpdateReaction_thenReactionIsUpdated() {
        Reaction reaction = new Reaction();
        ReactionType newType = ReactionType.DISLIKE;

        reactionService.updateReaction(reaction, newType);

        assertEquals(newType, reaction.getType());
    }
}

