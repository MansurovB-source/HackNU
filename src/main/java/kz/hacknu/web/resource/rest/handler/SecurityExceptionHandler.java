package kz.hacknu.web.resource.rest.handler;

import kz.hacknu.web.service.exception.TokenNotValidException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class SecurityExceptionHandler implements SecurityAdviceTrait {

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeni(AccessDeniedException ex, WebRequest request)
    {
        return null;
    }

    @ExceptionHandler(TokenNotValidException.class)
    protected ResponseEntity<Object> handleTokenNotValidException(TokenNotValidException ex, WebRequest request){
        return null;
    }


}
