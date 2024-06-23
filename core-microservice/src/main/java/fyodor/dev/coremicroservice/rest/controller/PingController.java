package fyodor.dev.coremicroservice.rest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/v1/core")
public class PingController {

    private final AtomicInteger counter = new AtomicInteger();

    @Value("${eureka.instance.instance-id}")
    private String instanceId;

    @GetMapping
    public String getCountRequest() {
        String responseInfo = String.format("%d - requests to endpoint at %s. Agent: %s",
                counter.incrementAndGet(),
                PingController.class.getPackage(),
                instanceId);

        return responseInfo;
    }
}
