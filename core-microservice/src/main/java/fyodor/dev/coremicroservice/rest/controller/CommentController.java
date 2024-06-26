package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.rest.dto.CommentDto;
import fyodor.dev.coremicroservice.rest.dto.request.CreateCommentRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateCommentRequest;
import fyodor.dev.coremicroservice.service.facade.CommentFacadeService;
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
@RequestMapping("/api/v1/core/crud/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "CommentController", description = "Управление комментариями у постов")
public class CommentController {

    CommentFacadeService commentFacadeService;

    @PostMapping
    @Operation(summary = "Создание комментария", description = "Создает комментарий к посту")
    public ResponseEntity<CommentDto> createComment(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                        @RequestHeader("X-User-login") String userLogin,
                                                    @Parameter(description = "Данные для создания комментария")
                                                        @RequestBody CreateCommentRequest createCommentRequest) {
        log.info("Creating new comment for post: {}", createCommentRequest.getPostId());
        CommentDto commentDto = commentFacadeService.createComment(userLogin, createCommentRequest);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/{commentId}")
    @Operation(summary = "Получение комментария", description = "Получение комментария по его UUID")
    public ResponseEntity<CommentDto> getComment(@Parameter(description = "UUID комментария")
                                                     @PathVariable UUID commentId) {
        log.info("Fetching comment with ID: {}", commentId);
        CommentDto commentDto = commentFacadeService.getComment(commentId);
        return ResponseEntity.ok(commentDto);
    }

    @PutMapping("/{commentId}")
    @Operation(summary = "Обновление комментария", description = "Обновляет комментарий по его UUID")
    public ResponseEntity<CommentDto> updateComment(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                        @RequestHeader("X-User-login") String userLogin,
                                                    @Parameter(description = "UUID комментария")
                                                        @PathVariable UUID commentId,
                                                    @Parameter(description = "Данные для обновления комментария")
                                                        @RequestBody UpdateCommentRequest updateCommentRequest) {
        log.info("Updating comment with ID: {}", commentId);
        CommentDto commentDto = commentFacadeService.updateComment(userLogin, commentId, updateCommentRequest);
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{commentId}")
    @Operation(summary = "Удаление комментария", description = "Удаляет комментарий по его UUID")
    public ResponseEntity<Void> deleteComment(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                  @RequestHeader("X-User-login") String userLogin,
                                              @Parameter(description = "UUID комментария")
                                                  @PathVariable UUID commentId) {
        log.info("Deleting comment with ID: {}", commentId);
        commentFacadeService.deleteComment(userLogin, commentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Получение всех комментариев", description = "Получение всех комментариев по посту")
    public ResponseEntity<List<CommentDto>> getCommentsByPost(@Parameter(description = "UUID поста")
                                                                  @PathVariable UUID postId) {
        log.info("Fetching comments for post with ID: {}", postId);
        List<CommentDto> comments = commentFacadeService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }
}
