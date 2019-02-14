package ro.quickorder.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenEx extends RuntimeException {
    public ForbiddenEx(String message) {
        super(message);
    }
}
