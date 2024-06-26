package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.rest.dto.ReactionDto;
import fyodor.dev.coremicroservice.rest.dto.ReactionSummaryDto;
import fyodor.dev.coremicroservice.rest.dto.request.ReactionRequest;
import fyodor.dev.coremicroservice.service.facade.ReactionFacadeService;
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
@RequestMapping("/api/v1/core/crud/reaction")
@Tag(name = "ReactionController", description = "Управление реакциями у постов")
public class ReactionController {

    ReactionFacadeService reactionFacadeService;

    @GetMapping("/{reactionId}")
    @Operation(summary = "Получение реакции", description = "Получение реакции по её UUID")
    public ResponseEntity<ReactionDto> getReaction(@Parameter(description = "UUID реакции")
                                                   @PathVariable UUID reactionId) {
        log.info("Fetching reaction with ID: {}", reactionId);
        ReactionDto reactionDto = reactionFacadeService.getReaction(reactionId);
        return ResponseEntity.ok(reactionDto);
    }

    @PostMapping
    @Operation(summary = "Создание реакции", description = "Создает реакцию к посту")
    public ResponseEntity<Void> createReaction(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                   @RequestHeader("X-User-login") String userLogin,
                                               @Parameter(description = "Данные для создания реакции")
                                                    @RequestBody ReactionRequest reactionRequest) {
        log.info("Creating new reaction for post: {}", reactionRequest.getPostId());
        reactionFacadeService.createReaction(userLogin, reactionRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "Удаление реакции", description = "Удаление реакции по посту и типу реакции")
    public ResponseEntity<Void> deleteReaction(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                   @RequestHeader("X-User-login") String userLogin,
                                               @Parameter(description = "UUID поста")
                                                    @PathVariable UUID postId) {
        log.info("Deleting reaction from post: {}", postId);
        reactionFacadeService.deleteReaction(userLogin, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Получение всех реакций", description = "Получение всех реакций по посту")
    public ResponseEntity<List<ReactionSummaryDto>> getReactionsByPost(@Parameter(description = "UUID поста")
                                                                           @PathVariable UUID postId) {
        log.info("Fetching reactions for post with ID: {}", postId);
        List<ReactionSummaryDto> reactionSummary = reactionFacadeService.getReactionSummaryByPost(postId);
        return ResponseEntity.ok(reactionSummary);
    }
}
