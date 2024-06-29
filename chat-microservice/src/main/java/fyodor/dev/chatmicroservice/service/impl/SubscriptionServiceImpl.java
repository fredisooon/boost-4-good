package fyodor.dev.chatmicroservice.service.impl;

import fyodor.dev.chatmicroservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final RestTemplate restTemplate;

    @Value("${crud.microservice.url}")
    private String crudMicroserviceUrl;

    @Override
    public boolean hasValidSubscription(UUID creatorId, UUID readerId) {
        String url = String.format("%s/api/subscriptions/check?creatorId=%s&readerId=%s",
                crudMicroserviceUrl, creatorId, readerId);
        Boolean response = restTemplate.getForObject(url, Boolean.class);
        return response != null && response;
    }
}
