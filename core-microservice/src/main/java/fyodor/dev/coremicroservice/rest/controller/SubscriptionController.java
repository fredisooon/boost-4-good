package fyodor.dev.coremicroservice.rest.controller;

import fyodor.dev.coremicroservice.rest.dto.SubscriptionDefinitionDto;
import fyodor.dev.coremicroservice.rest.dto.SubscriptionDto;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionDefinitionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateSubscriptionRequest;
import fyodor.dev.coremicroservice.service.facade.SubscriptionDefinitionFacadeService;
import fyodor.dev.coremicroservice.service.facade.SubscriptionFacadeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v1/core/crud/subscriptions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "SubscriptionController", description = "Управление подписками")
public class SubscriptionController {

    SubscriptionFacadeService subscriptionFacadeService;
    SubscriptionDefinitionFacadeService subscriptionDefinitionFacadeService;

    @GetMapping("/api/subscriptions/check")
    @Operation(summary = "Валидация подписки", description = "Проверка доступности подписки для читателя и создателя")
    public ResponseEntity<Boolean> checkSubscription(@Parameter(description = "UUID создателя контента")
                                                         @RequestParam UUID creatorId,
                                                     @Parameter(description = "UUID читателя (работяги)")
                                                          @RequestParam UUID readerId) {
        log.info("Check subscription availability for creator [{}] and reader [{}]", creatorId, readerId);
        boolean hasValidSubscription = subscriptionFacadeService.hasValidSubscription(creatorId, readerId);
        return ResponseEntity.ok(hasValidSubscription);
    }

    @PostMapping
    @Operation(summary = "Создание подписки", description = "Создает подписку для пользователя")
    public ResponseEntity<SubscriptionDto> createSubscription(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                                  @RequestHeader("X-User-login") String userLogin,
                                                              @Parameter(description = "Данные для создания подписки")
                                                                    @RequestBody CreateSubscriptionRequest createSubscriptionRequest) {
        log.info("Creating new subscription for user: {}", userLogin);
        SubscriptionDto subscriptionDto = subscriptionFacadeService
                .createSubscription(userLogin, createSubscriptionRequest);
        return ResponseEntity.ok(subscriptionDto);
    }

    @GetMapping("/{subscriptionId}")
    @Operation(summary = "Получение подписки", description = "Получение подписки по её UUID")
    public ResponseEntity<SubscriptionDto> getSubscription(@Parameter(description = "UUID подписки")
                                                               @PathVariable UUID subscriptionId) {
        log.info("Fetching subscription with ID: {}", subscriptionId);
        SubscriptionDto subscriptionDto = subscriptionFacadeService.getSubscription(subscriptionId);
        return ResponseEntity.ok(subscriptionDto);
    }

    @PutMapping("/{subscriptionId}")
    @Operation(summary = "Обновление подписки", description = "Обновляет подписку по её UUID")
    public ResponseEntity<SubscriptionDto> updateSubscription(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                                  @RequestHeader("X-User-login") String userLogin,
                                                              @Parameter(description = "UUID подписки")
                                                                  @PathVariable UUID subscriptionId,
                                                              @Parameter(description = "Данные для обновления подписки")
                                                                  @RequestBody UpdateSubscriptionRequest updateSubscriptionRequest) {
        log.info("Updating subscription with ID: {}", subscriptionId);
        SubscriptionDto subscriptionDto = subscriptionFacadeService.updateSubscription(userLogin, subscriptionId, updateSubscriptionRequest);
        return ResponseEntity.ok(subscriptionDto);
    }

    @DeleteMapping("/{subscriptionId}")
    @Operation(summary = "Удаление подписки", description = "Удаляет подписку по её UUID")
    public ResponseEntity<Void> deleteSubscription(@Parameter(description = "Логин пользователя (передается автоматом при запросе через шлюз)")
                                                       @RequestHeader("X-User-login") String userLogin,
                                                   @Parameter(description = "UUID подписки")
                                                        @PathVariable UUID subscriptionId) {
        log.info("Deleting subscription with ID: {}", subscriptionId);
        subscriptionFacadeService.deleteSubscription(userLogin, subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Получение всех подписок пользователя", description = "Получение всех подписок для конкретного пользователя")
    public ResponseEntity<List<SubscriptionDto>> getSubscriptionsByUser(@Parameter(description = "UUID пользователя")
                                                                            @PathVariable UUID userId) {
        log.info("Fetching subscriptions for user with ID: {}", userId);
        List<SubscriptionDto> subscriptions = subscriptionFacadeService.getSubscriptionsByUser(userId);
        return ResponseEntity.ok(subscriptions);
    }

    @PostMapping("/definition")
    @Operation(summary = "Создание определения подписки", description = "Создает новое определение подписки для создателя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Определение подписки создано",
                    content = @Content(schema = @Schema(implementation = SubscriptionDefinitionDto.class))),
            @ApiResponse(responseCode = "400", description = "Ошибка при создании")
    })
    public ResponseEntity<SubscriptionDefinitionDto> createSubscriptionDefinition(@Parameter(description = "ID создателя")
                                                                                      @RequestParam UUID creatorId,
                                                                                  @Parameter(description = "Запрос на создание определения подписки")
                                                                                        @RequestBody CreateSubscriptionDefinitionRequest request) {
        SubscriptionDefinitionDto subscriptionDefinitionDto = subscriptionDefinitionFacadeService.createSubscriptionDefinition(creatorId, request);
        return ResponseEntity.ok(subscriptionDefinitionDto);
    }
}

