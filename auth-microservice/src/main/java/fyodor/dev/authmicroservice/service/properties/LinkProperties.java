package fyodor.dev.authmicroservice.service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "rest")
public class LinkProperties {

    private String userServiceLink;
}
