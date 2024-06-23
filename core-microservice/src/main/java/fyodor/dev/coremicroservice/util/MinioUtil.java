package fyodor.dev.coremicroservice.util;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Slf4j
@UtilityClass
public class MinioUtil {

    public static String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    public static String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    public static MediaType determineMediaType(String fileName) {
        String extension = getFileExtension(fileName);
        return getMediaTypeForExtension(extension);
    }

    private String getFileExtension(String fileName) {
        return fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1) : "";
    }

    private MediaType getMediaTypeForExtension(String extension) {
        return switch (extension.toLowerCase()) {
            case "jpg", "jpeg" -> MediaType.IMAGE_JPEG;
            case "png" -> MediaType.IMAGE_PNG;
            case "gif" -> MediaType.IMAGE_GIF;
            case "svg" -> MediaType.valueOf("image/svg+xml");
            case "heic" -> MediaType.valueOf("image/heic");
            default -> MediaType.APPLICATION_OCTET_STREAM;
        };
    }

    public static void validateFile(MultipartFile file) {
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new IllegalArgumentException("Image must have a name.");
        }
    }

    @SneakyThrows
    public static void createBucketIfNotExists(MinioClient minioClient, String bucketName) {
        boolean isFound = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(bucketName)
                .build());
        if (!isFound) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(bucketName)
                    .build());
            log.info("MinIO Bucket | Successfully created bucket created: {}", bucketName);
        }
    }
}
