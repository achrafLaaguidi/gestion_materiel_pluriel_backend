package net.pluriel.gestionApp.Errors;

import org.springframework.http.HttpStatus;

import java.net.http.HttpRequest;

public class NotFoundException extends ApiBaseException{
    public NotFoundException(String message) {
        super(message);
    }
    @Override
    public HttpStatus getStatusCode(){
        return HttpStatus.NOT_FOUND;
    }
}
