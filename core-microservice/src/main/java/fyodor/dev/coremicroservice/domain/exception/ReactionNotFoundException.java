package fyodor.dev.coremicroservice.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Reaction not found")
public class ReactionNotFoundException extends RuntimeException {

    public ReactionNotFoundException(String message) {
        super(message);
    }
}
