package letsit_backend.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

//@Getter
//@AllArgsConstructor
public class CustomException extends RuntimeException {

    // private final ErrorCode errorCode;
    public CustomException(String message) {
        super(message);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
    /*

     */
}

