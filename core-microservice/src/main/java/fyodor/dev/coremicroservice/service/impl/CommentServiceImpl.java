package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.feed.Comment;
import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Override
    public Comment createComment(Post post, User user, String content) {
        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        return comment;
    }

    @Override
    public void updateComment(Comment comment, String content) {
        comment.setContent(content);
    }
}