package fyodor.dev.coremicroservice.service.facade;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.exception.UnauthorizedException;
import fyodor.dev.coremicroservice.domain.feed.Post;
import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.domain.image.Image;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.ImageRepository;
import fyodor.dev.coremicroservice.repository.SubscriptionRepository;
import fyodor.dev.coremicroservice.repository.UserRepository;
import fyodor.dev.coremicroservice.rest.dto.*;
import fyodor.dev.coremicroservice.rest.mapper.PostMapper;
import fyodor.dev.coremicroservice.rest.mapper.SubscriptionMapper;
import fyodor.dev.coremicroservice.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostFacadeService {

    private final PostService postService;
    private final SubscriptionRepository subscriptionRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final SubscriptionMapper subscriptionMapper;

    @Transactional
    public PostDto createPost(String userLogin, CreatePostRequest createPostRequest) {
        User user = userRepository.findByUsername(userLogin)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Subscription subscription = subscriptionRepository.findById(createPostRequest.getSubscriptionId())
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));

        List<Image> images = imageRepository.findAllById(createPostRequest.getImageIds());

        Post post = postMapper.toEntity(createPostRequest);
        post.setSubscription(subscription);
        post.setImages(images);
        post.setUser(user);

        Post createdPost = postService.createPost(post);
        return postMapper.toDto(createdPost);
    }

    @Transactional(readOnly = true)
    public PostDto getPost(UUID postId) {
        Post post = postService.getPost(postId);
        return postMapper.toDto(post);
    }

    @Transactional
    public PostDto updatePost(String userLogin, UUID postId, UpdatePostRequest updatePostRequest) {
        Post post = postService.getPost(postId);

        if (!post.getUser().getUsername().equals(userLogin)) {
            throw new UnauthorizedException("User not authorized to update this post");
        }

        postMapper.updatePostFromDto(updatePostRequest, post);
        List<Image> images = imageRepository.findAllById(updatePostRequest.getImageIds());
        post.setImages(images);

        Post updatedPost = postService.updatePost(post);
        return postMapper.toDto(updatedPost);
    }

    @Transactional
    public void deletePost(String userLogin, UUID postId) {
        Post post = postService.getPost(postId);

        if (!post.getUser().getUsername().equals(userLogin)) {
            throw new UnauthorizedException("User not authorized to delete this post");
        }

        postService.deletePost(postId);
    }

    @Transactional(readOnly = true)
    public List<PostDto> getAllPosts() {
        return postService.getAllPosts().stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostDto> getPostsBySubscription(UUID subscriptionId) {
        return postService.getPostsBySubscription(subscriptionId).stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FeedDataDto getFeed(String userLogin, Integer limit, Integer commentsLimit, Boolean onlyAllowed) {
        List<Subscription> subscriptions = subscriptionRepository.findAllBySubscriberUsername(userLogin);
        List<Post> feedPosts = new ArrayList<>();
        for (Subscription subscription : subscriptions) {
            UUID subId = subscription.getId();
            List<Post> postsBySubscription = postService.getPostsBySubscription(subId);
            if (onlyAllowed) {
                postsBySubscription = postsBySubscription.stream()
                        .filter(post -> post.getSubscription().getSubscriptionDefinition().getLevel().ordinal() <=
                                                  subscription.getSubscriptionDefinition().getLevel().ordinal())
                        .toList();
            }
            feedPosts.addAll(postsBySubscription);
        }
        feedPosts = feedPosts.stream()
                .sorted((p1, p2) -> p2.getCreatedAt().compareTo(p1.getCreatedAt()))
                .limit(limit)
                .collect(Collectors.toList());

        List<PostDto> postDtos = feedPosts.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());

        List<SubscriptionDto> subscriptionDtos = subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());

        FeedDataDto feedDataDto = new FeedDataDto();
        feedDataDto.setPosts(postDtos);
        feedDataDto.setBlogs(subscriptionDtos);
        return feedDataDto;
    }
}
