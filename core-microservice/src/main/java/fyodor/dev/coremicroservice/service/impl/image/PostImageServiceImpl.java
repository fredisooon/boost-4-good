package fyodor.dev.coremicroservice.service.impl.image;

import fyodor.dev.coremicroservice.service.AbstractImageService;
import fyodor.dev.coremicroservice.service.properties.MinioProperties;
import io.minio.MinioClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostImageServiceImpl extends AbstractImageService { // TODO [18.06.2024]: 

    @Getter
    private final MinioClient minioClient;
    @Getter
    private final MinioProperties minioProperties;
}
