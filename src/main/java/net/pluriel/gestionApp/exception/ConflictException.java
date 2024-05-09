package net.pluriel.gestionApp.exception;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiBaseException{
    public ConflictException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getStatusCode() {
        return HttpStatus.CONFLICT;
    }
}
