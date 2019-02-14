package ro.quickorder.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityEx extends RuntimeException {
    public UnprocessableEntityEx(String message) {
        super(message);
    }
}
