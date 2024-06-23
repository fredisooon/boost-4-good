package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.feed.Comment;
import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.service.impl.CommentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CommentServiceImplTest {

    private CommentService commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl();
    }

    @Test
    void whenCreateComment_thenReturnComment() {
        Post post = new Post();
        User user = new User();
        String content = "This is a test comment";

        Comment comment = commentService.createComment(post, user, content);

        assertNotNull(comment);
        assertEquals(post, comment.getPost());
        assertEquals(user, comment.getUser());
        assertEquals(content, comment.getContent());
        assertNotNull(comment.getCreatedAt());
    }

    @Test
    void whenUpdateComment_thenContentUpdated() {
        Comment comment = new Comment();
        String newContent = "Updated content";

        commentService.updateComment(comment, newContent);

        assertEquals(newContent, comment.getContent());
    }
}

