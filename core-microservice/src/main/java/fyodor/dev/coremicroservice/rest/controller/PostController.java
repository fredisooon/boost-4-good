package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.rest.dto.*;
import fyodor.dev.coremicroservice.service.facade.PostFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/v1/core/crud/posts")
@Tag(name="PostController", description="Основная лента пользователя")
public class PostController {

    PostFacadeService postFacadeService;

    @GetMapping("/feed")
    @Operation(summary = "Получение полной информации ленты", description = "Получение полной информации по ленте для пользователя")
    public ResponseEntity<FeedDataDto> getFeed(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                   @RequestHeader("X-User-login") String userLogin,
                                               @Parameter(description = "Количество постов в ответе")
                                                    @RequestParam("limit") Integer limit,
                                               @Parameter(description = "Максимальное количество комментариев под каждым постом")
                                                   @RequestParam("comments_limit") Integer commentsLimit,
                                               @Parameter(description = "Уровень доступа к постам")
                                                   @RequestParam("only_allowed") Boolean onlyAllowed) {
        log.info("Fetching feed for user: {}", userLogin);
        FeedDataDto feedData = postFacadeService.getFeed(userLogin, limit, commentsLimit, onlyAllowed);
        return ResponseEntity.ok(feedData);
    }

    @PostMapping
    @Operation(summary = "Создание поста", description = "Создание нового поста")
    public ResponseEntity<PostDto> createPost(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                  @RequestHeader("X-User-login") String userLogin,
                                              @Parameter(description = "Объект поста")
                                                @RequestBody CreatePostRequest createPostRequest) {
        log.info("Creating new post with title: {} for user: {}", createPostRequest.getTitle(), userLogin);
        PostDto postDto = postFacadeService.createPost(userLogin, createPostRequest);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "Получение поста", description = "Получение опубликованного поста")
    public ResponseEntity<PostDto> getPost(@Parameter(description = "UUID поста")
                                               @PathVariable UUID postId) {
        log.info("Fetching post with ID: {}", postId);
        PostDto postDto = postFacadeService.getPost(postId);
        return ResponseEntity.ok(postDto);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "Обновление поста", description = "Обновление существующего поста")
    public ResponseEntity<PostDto> updatePost(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                  @RequestHeader("X-User-login") String userLogin,
                                              @Parameter(description = "UUID поста")
                                                @PathVariable UUID postId,
                                              @Parameter(description = "Поля для обновления объекта")
                                                  @RequestBody UpdatePostRequest updatePostRequest) {
        log.info("Updating post with ID: {}", postId);
        PostDto postDto = postFacadeService.updatePost(userLogin, postId, updatePostRequest);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "Удаление поста", description = "Удаление поста по UUID")
    public ResponseEntity<Void> deletePost(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                               @RequestHeader("X-User-login") String userLogin,
                                           @Parameter(description = "UUID поста")
                                               @PathVariable UUID postId) {
        log.info("Deleting post with ID: {}", postId);
        postFacadeService.deletePost(userLogin, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Получение всех постов", description = "Получение всех постов")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        log.info("Fetching all posts");
        List<PostDto> posts = postFacadeService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/subscription/{subscriptionId}")
    @Operation(summary = "Получение постов по подписке", description = "Получение постов опубликованных по подписке")
    public ResponseEntity<List<PostDto>> getPostsBySubscription(@Parameter(description = "UUID подписки")
                                                                    @PathVariable UUID subscriptionId) {
        log.info("Fetching posts for subscription with ID: {}", subscriptionId);
        List<PostDto> posts = postFacadeService.getPostsBySubscription(subscriptionId);
        return ResponseEntity.ok(posts);
    }
}
