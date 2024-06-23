package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.feed.Post;

import java.util.List;
import java.util.UUID;

public interface PostService {

    Post createPost(Post post);
    Post getPost(UUID postId);
    Post updatePost(Post post);
    void deletePost(UUID postId);
    List<Post> getAllPosts();
    List<Post> getPostsBySubscription(UUID subscriptionId);

}
