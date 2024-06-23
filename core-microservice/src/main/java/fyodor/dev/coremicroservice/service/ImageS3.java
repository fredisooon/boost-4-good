package fyodor.dev.coremicroservice.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageS3 {

    String upload(MultipartFile file);
    void deleteFromS3(String minioImageLink);
    InputStreamResource getImage(String minioImageLink);
}
