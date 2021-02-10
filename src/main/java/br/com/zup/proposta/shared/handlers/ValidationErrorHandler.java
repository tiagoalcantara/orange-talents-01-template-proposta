package br.com.zup.proposta.shared.handlers;

import br.com.zup.proposta.shared.exceptions.ApiException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidationErrorHandler {

    private MessageSource messageSource;

    public ValidationErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<String> errors = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String errorMessage = String.format("Campo %s %s", fieldError.getField(),
                                                messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
            errors.add(errorMessage);
        });

        return ResponseEntity.badRequest().body(new ValidationErrorResponse(errors));
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ValidationErrorResponse> handle(ApiException exception) {
        ValidationErrorResponse errors = new ValidationErrorResponse(exception.getReason());
        return ResponseEntity.status(exception.getHttpStatus()).body(errors);
    }
}
