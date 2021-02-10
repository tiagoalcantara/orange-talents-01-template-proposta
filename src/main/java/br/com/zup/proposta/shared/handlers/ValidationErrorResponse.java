package br.com.zup.proposta.shared.handlers;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {
    private final List<String> errors;

    public ValidationErrorResponse(List<String> errors) {
        this.errors = errors;
    }

    public ValidationErrorResponse(String error) {
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    public List<String> getErrors() {
        return errors;
    }
}
