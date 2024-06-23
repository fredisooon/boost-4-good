package fyodor.dev.coremicroservice.service.impl;

import fyodor.dev.coremicroservice.domain.exception.ResourceNotFoundException;
import fyodor.dev.coremicroservice.domain.user.Image;
import fyodor.dev.coremicroservice.repository.ImageRepository;
import fyodor.dev.coremicroservice.rest.dto.UserImageDto;
import fyodor.dev.coremicroservice.service.ImageService;
import fyodor.dev.coremicroservice.service.properties.MinioProperties;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final MinioClient minioClient;

    private final MinioProperties minioProperties;
    private final ImageRepository imageRepository;

    @Override
    public String upload(UserImageDto image) {
        try {
            createBucket();
        } catch (Exception e) {
            throw new RuntimeException("Image upload failed." + e.getMessage());
        }
        MultipartFile file = image.getFile();
        if (file.isEmpty() || file.getOriginalFilename() == null) {
            throw new RuntimeException("Image must have name.");
        }
        String fileName = generateFileName(file);
        InputStream inputStream;
        try {
            inputStream = file.getInputStream();
        } catch (Exception e) {
            throw new RuntimeException("Image upload failed." + e.getMessage());
        }
        saveImage(inputStream, fileName);
        try {
            inputStream.close();
        } catch (Exception e) {
            throw new RuntimeException("Closing input stream exception");
        }
        return fileName;
    }

    @Override
    @SneakyThrows
    public ResponseEntity<InputStreamResource> getImageByUserId(UUID userId) {
        Image image = imageRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found."));

        String imageLink = image.getImageLink();
        String extension = getFileExtension(imageLink); // Получаем расширение файла
        MediaType mediaType = getMediaTypeForExtension(extension); // Определяем MediaType по расширению

        try (InputStream stream = minioClient.getObject(GetObjectArgs
                .builder()
                .bucket(minioProperties.getBucket())
                .object(imageLink)
                .build());
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            buffer.flush();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);

            return ResponseEntity.ok().headers(headers).body(new InputStreamResource(new ByteArrayInputStream(buffer.toByteArray())));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }



    // Вспомогательные методы для определения расширения и MediaType
    private String getFileExtension(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }

    private MediaType getMediaTypeForExtension(String extension) {
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "svg":
                return MediaType.valueOf("image/svg+xml");
            case "heic":
                return MediaType.valueOf("image/heic");
            default:
                return MediaType.APPLICATION_OCTET_STREAM; // Неизвестный тип - отправляем как двоичные данные
        }
    }

    @SneakyThrows
    private void createBucket() {
        boolean isFound = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucket())
                .build());
        if (!isFound) {
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucket())
                    .build());
        }
    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }

    private String getExtension(MultipartFile file) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String fileName) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(fileName)
                .build());
    }
}
