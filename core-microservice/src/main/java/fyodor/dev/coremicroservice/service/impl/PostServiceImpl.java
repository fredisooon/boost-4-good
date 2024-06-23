package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.repository.PostRepository;
import fyodor.dev.coremicroservice.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public Post createPost(final Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional(readOnly = true)
    public Post getPost(final UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }

    @Override
    @Transactional
    public Post updatePost(final Post post) {
        return postRepository.save(post);
    }

    @Override
    @Transactional
    public void deletePost(final UUID postId) {
        postRepository.deleteById(postId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostsBySubscription(final UUID subscriptionId) {
        return postRepository.findBySubscriptionId(subscriptionId);
    }
}
