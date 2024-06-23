package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.repository.PostRepository;
import fyodor.dev.coremicroservice.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
    private UUID postId;

    @BeforeEach
    void setUp() {
        postId = UUID.randomUUID();
        post = new Post();
        post.setId(postId);
    }

    @Test
    void whenCreatePost_thenPostIsCreated() {
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post createdPost = postService.createPost(post);

        assertNotNull(createdPost);
        assertEquals(postId, createdPost.getId());
        verify(postRepository).save(post);
    }

    @Test
    void whenGetPost_thenPostIsReturned() {
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        Post foundPost = postService.getPost(postId);

        assertNotNull(foundPost);
        assertEquals(postId, foundPost.getId());
        verify(postRepository).findById(postId);
    }

    @Test
    void whenGetPost_thenThrowResourceNotFoundException() {
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> postService.getPost(postId));
        verify(postRepository).findById(postId);
    }

    @Test
    void whenUpdatePost_thenPostIsUpdated() {
        when(postRepository.save(any(Post.class))).thenReturn(post);

        Post updatedPost = postService.updatePost(post);

        assertNotNull(updatedPost);
        assertEquals(postId, updatedPost.getId());
        verify(postRepository).save(post);
    }

    @Test
    void whenDeletePost_thenPostIsDeleted() {
        doNothing().when(postRepository).deleteById(postId);

        postService.deletePost(postId);

        verify(postRepository).deleteById(postId);
    }

    @Test
    void whenGetAllPosts_thenReturnPostList() {
        when(postRepository.findAll()).thenReturn(Collections.singletonList(post));

        List<Post> posts = postService.getAllPosts();

        assertNotNull(posts);
        assertEquals(1, posts.size());
        verify(postRepository).findAll();
    }

    @Test
    void whenGetPostsBySubscription_thenReturnPostList() {
        UUID subscriptionId = UUID.randomUUID();
        when(postRepository.findBySubscriptionId(subscriptionId)).thenReturn(Collections.singletonList(post));

        List<Post> posts = postService.getPostsBySubscription(subscriptionId);

        assertNotNull(posts);
        assertEquals(1, posts.size());
        verify(postRepository).findBySubscriptionId(subscriptionId);
    }
}
