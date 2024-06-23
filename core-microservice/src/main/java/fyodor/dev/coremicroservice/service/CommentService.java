package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.feed.Comment;
import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.user.User;


public interface CommentService {

    Comment createComment(Post post, User user, String content);
    void updateComment(Comment comment, String content);

}