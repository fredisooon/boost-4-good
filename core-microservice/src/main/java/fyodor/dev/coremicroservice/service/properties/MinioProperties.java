package fyodor.dev.coremicroservice.service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProperties {

    private String profileBucket;
    private String postBucket;
    private String messageBucket;
    private String url;
    private String accessKey;
    private String secretKey;
}
