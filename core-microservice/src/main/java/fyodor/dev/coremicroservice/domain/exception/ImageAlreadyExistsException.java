package fyodor.dev.coremicroservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Image already set")
public class ImageAlreadyExistsException extends RuntimeException{
    public ImageAlreadyExistsException(String message) {
        super(message);
    }
}
