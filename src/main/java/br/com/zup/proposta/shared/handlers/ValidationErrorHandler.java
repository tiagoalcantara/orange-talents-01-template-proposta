package br.com.zup.proposta.shared.handlers;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationErrorHandler {

    private MessageSource messageSource;

    public ValidationErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ValidationErrorResponse> handle(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return buildValidationErrorResponse(fieldErrors);
    }

    private List<ValidationErrorResponse> buildValidationErrorResponse(List<FieldError> fieldErrors) {
        return fieldErrors.stream().map(error -> new ValidationErrorResponse(error.getField(), getMessage(error))).collect(Collectors.toList());
    }

    private String getMessage(FieldError error) {
        return messageSource.getMessage(error, LocaleContextHolder.getLocale());
    }

}
