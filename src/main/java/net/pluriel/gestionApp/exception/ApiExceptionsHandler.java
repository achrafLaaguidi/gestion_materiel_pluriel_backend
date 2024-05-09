package net.pluriel.gestionApp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ApiExceptionsHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ValidationError validationError = new ValidationError();
        validationError.setUri(request.getDescription(false));

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            validationError.addError(fieldError.getDefaultMessage());
        }
        return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiBaseException.class)
    public ResponseEntity<ErrorDetails> handlerApiException(ApiBaseException e , WebRequest wr){
    ErrorDetails details=new ErrorDetails(e.getMessage(),wr.getDescription(false));
    return new ResponseEntity<>(details,e.getStatusCode());


}
}
