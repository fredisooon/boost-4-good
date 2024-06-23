package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.feed.Subscription;
import fyodor.dev.coremicroservice.domain.feed.SubscriptionDefinition;
import fyodor.dev.coremicroservice.domain.feed.SubscriptionType;
import fyodor.dev.coremicroservice.domain.user.User;
import fyodor.dev.coremicroservice.repository.SubscriptionDefinitionRepository;
import fyodor.dev.coremicroservice.repository.SubscriptionRepository;
import fyodor.dev.coremicroservice.rest.dto.request.CreateSubscriptionRequest;
import fyodor.dev.coremicroservice.rest.dto.request.UpdateSubscriptionRequest;
import fyodor.dev.coremicroservice.service.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private SubscriptionDefinitionRepository subscriptionDefinitionRepository;

    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;

    private User subscriber;
    private User creator;
    private Subscription subscription;
    private CreateSubscriptionRequest createRequest;
    private UpdateSubscriptionRequest updateRequest;

    @BeforeEach
    void setUp() {
        subscriber = new User();
        creator = new User();
        subscription = new Subscription();
        subscription.setStartDate(LocalDateTime.now()); // Инициализация startDate

        createRequest = new CreateSubscriptionRequest();
        createRequest.setType(SubscriptionType.STANDARD);
        createRequest.setSubscriptionDefinitionId(UUID.randomUUID());
        createRequest.setCost(10);
        createRequest.setPeriod(1);

        updateRequest = new UpdateSubscriptionRequest();
        updateRequest.setType(SubscriptionType.STANDARD);
        updateRequest.setSubscriptionDefinitionId(UUID.randomUUID());
        updateRequest.setCost(20);
        updateRequest.setPeriod(2);
    }

    @Test
    void whenCreateTrackingSubscription_thenSubscriptionIsCreated() {
        createRequest.setType(SubscriptionType.TRACKING);

        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> {
            Subscription savedSubscription = invocation.getArgument(0);
            savedSubscription.setType(SubscriptionType.TRACKING); // Устанавливаем тип подписки явно
            return savedSubscription;
        });

        Subscription createdSubscription = subscriptionService.createSubscription(subscriber, creator, createRequest);

        assertNotNull(createdSubscription);
        assertEquals(SubscriptionType.TRACKING, createdSubscription.getType());
        assertEquals(0, createdSubscription.getCost());
        assertNull(createdSubscription.getPeriod());
        assertNull(createdSubscription.getEndDate());
        assertNull(createdSubscription.getSubscriptionDefinition());

        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void whenCreateStandardSubscription_thenSubscriptionIsCreated() {
        SubscriptionDefinition subscriptionDefinition = new SubscriptionDefinition();
        when(subscriptionDefinitionRepository.findById(createRequest.getSubscriptionDefinitionId())).thenReturn(Optional.of(subscriptionDefinition));
        when(subscriptionRepository.save(any(Subscription.class))).thenAnswer(invocation -> {
            Subscription savedSubscription = invocation.getArgument(0);
            savedSubscription.setType(SubscriptionType.STANDARD); // Устанавливаем тип подписки явно
            return savedSubscription;
        });

        Subscription createdSubscription = subscriptionService.createSubscription(subscriber, creator, createRequest);

        assertNotNull(createdSubscription);
        assertEquals(SubscriptionType.STANDARD, createdSubscription.getType());
        assertEquals(createRequest.getCost(), createdSubscription.getCost());
        assertEquals(createRequest.getPeriod(), createdSubscription.getPeriod());
        assertNotNull(createdSubscription.getEndDate());
        assertEquals(subscriptionDefinition, createdSubscription.getSubscriptionDefinition());

        verify(subscriptionDefinitionRepository).findById(createRequest.getSubscriptionDefinitionId());
        verify(subscriptionRepository).save(any(Subscription.class));
    }

    @Test
    void whenCreateStandardSubscriptionWithInvalidDefinition_thenThrowResourceNotFoundException() {
        when(subscriptionDefinitionRepository.findById(createRequest.getSubscriptionDefinitionId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subscriptionService.createSubscription(subscriber, creator, createRequest));
        verify(subscriptionDefinitionRepository).findById(createRequest.getSubscriptionDefinitionId());
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    void whenUpdateTrackingSubscription_thenSubscriptionIsUpdated() {
        subscription.setType(SubscriptionType.STANDARD);

        updateRequest.setType(SubscriptionType.TRACKING);

        subscriptionService.updateSubscription(subscription, updateRequest);

        assertEquals(SubscriptionType.TRACKING, subscription.getType());
        assertEquals(0, subscription.getCost());
        assertNull(subscription.getPeriod());
        assertNull(subscription.getEndDate());
        assertNull(subscription.getSubscriptionDefinition());
    }

    @Test
    void whenUpdateStandardSubscription_thenSubscriptionIsUpdated() {
        SubscriptionDefinition subscriptionDefinition = new SubscriptionDefinition();
        when(subscriptionDefinitionRepository.findById(updateRequest.getSubscriptionDefinitionId())).thenReturn(Optional.of(subscriptionDefinition));

        subscriptionService.updateSubscription(subscription, updateRequest);

        assertEquals(SubscriptionType.STANDARD, subscription.getType());
        assertEquals(updateRequest.getCost(), subscription.getCost());
        assertEquals(updateRequest.getPeriod(), subscription.getPeriod());
        assertNotNull(subscription.getEndDate());
        assertEquals(subscription.getStartDate().plusMonths(updateRequest.getPeriod()), subscription.getEndDate());
        assertEquals(subscriptionDefinition, subscription.getSubscriptionDefinition());

        verify(subscriptionDefinitionRepository).findById(updateRequest.getSubscriptionDefinitionId());
    }

    @Test
    void whenUpdateStandardSubscriptionWithInvalidDefinition_thenThrowResourceNotFoundException() {
        when(subscriptionDefinitionRepository.findById(updateRequest.getSubscriptionDefinitionId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> subscriptionService.updateSubscription(subscription, updateRequest));
        verify(subscriptionDefinitionRepository).findById(updateRequest.getSubscriptionDefinitionId());
    }
}
