package fyodor.dev.authmicroservice.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("${api.version}/ping")
public class PingController {

    private final AtomicInteger counter = new AtomicInteger();

    @GetMapping
    public String getCountRequest() {
        String responseInfo = String.format("%d - requests to endpoint at %s",
                counter.incrementAndGet(), PingController.class.getPackage());

        return responseInfo;
    }
}
