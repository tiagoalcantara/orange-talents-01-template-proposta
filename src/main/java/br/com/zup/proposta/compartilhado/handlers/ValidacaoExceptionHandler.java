package br.com.zup.proposta.compartilhado.handlers;

import br.com.zup.proposta.compartilhado.exceptions.ApiException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ValidacaoExceptionHandler {

    private MessageSource messageSource;

    public ValidacaoExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDeValidacaoResponse> handle(MethodArgumentNotValidException exception) {
        List<String> erros = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> {
            String errorMessage = String.format("Campo %s %s", fieldError.getField(),
                                                messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
            erros.add(errorMessage);
        });

        return ResponseEntity.badRequest().body(new ErroDeValidacaoResponse(erros));
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErroDeValidacaoResponse> handle(ResponseStatusException exception) {
        ErroDeValidacaoResponse erros = new ErroDeValidacaoResponse(exception.getReason());
        return ResponseEntity.status(exception.getStatus()).body(erros);
    }
}
