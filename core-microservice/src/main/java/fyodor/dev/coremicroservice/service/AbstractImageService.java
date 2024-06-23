package fyodor.dev.coremicroservice.service;

import fyodor.dev.coremicroservice.service.properties.MinioProperties;
import fyodor.dev.coremicroservice.util.MinioUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static fyodor.dev.coremicroservice.util.MinioUtil.createBucketIfNotExists;
import static fyodor.dev.coremicroservice.util.MinioUtil.validateFile;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractImageService implements ImageS3 {

    protected abstract MinioClient getMinioClient();

    protected abstract MinioProperties getMinioProperties();


    @Override
    public String upload(MultipartFile file) {
        log.info("MinIO UPLOAD | Start upload image [{}]", file.getOriginalFilename());
        try {
            createBucketIfNotExists(getMinioClient(), getMinioProperties().getProfileBucket());
            validateFile(file);

            String fileName = MinioUtil.generateFileName(file);
            try (InputStream inputStream = file.getInputStream()) {
                getMinioClient().putObject(PutObjectArgs.builder()
                        .stream(inputStream, inputStream.available(), -1)
                        .bucket(getMinioProperties().getProfileBucket())
                        .object(fileName)
                        .build());
            }

            log.info("MinIO UPLOAD | Image uploaded successfully: [{}]", fileName);
            return fileName;
        } catch (Exception e) {
            log.error("MinIO ERROR |Image upload failed.", e);
            throw new RuntimeException("Image upload failed. " + e.getMessage(), e);
        }
    }

    @Override
    @SneakyThrows
    public void deleteFromS3(String minioImageLink) {
        log.info("MinIO DELETE | Start delete image [{}]", minioImageLink);
        getMinioClient().removeObject(RemoveObjectArgs.builder()
                .bucket(getMinioProperties().getProfileBucket())
                .object(minioImageLink)
                .build());
        log.info("MinIO DELETE | Successfully deleted image = [{}] from bucket - {}", minioImageLink, getMinioProperties().getProfileBucket());
    }

    @Override
    public InputStreamResource getImage(String minioImageLink) {
        log.info("MinIO GET | Trying to retrieve image = [{}] from bucket - {}",
                minioImageLink, getMinioProperties().getProfileBucket());

        try (InputStream stream = getMinioClient().getObject(GetObjectArgs.builder()
                .bucket(getMinioProperties().getProfileBucket())
                .object(minioImageLink)
                .build());
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            stream.transferTo(buffer);
            buffer.flush();

            log.info("MinIO GET | Successfully retrieved image = [{}] from bucket - {}",
                    minioImageLink, getMinioProperties().getProfileBucket());

            return new InputStreamResource(new ByteArrayInputStream(buffer.toByteArray()));
        } catch (Exception e) {
            log.error("MinIO ERROR | Error retrieving image = [{}] from bucket - {}. Cause: [{}]",
                    minioImageLink, getMinioProperties().getProfileBucket(), e.getMessage());
            throw new RuntimeException("Failed to retrieve image from MinIO", e);
        }
    }
}
