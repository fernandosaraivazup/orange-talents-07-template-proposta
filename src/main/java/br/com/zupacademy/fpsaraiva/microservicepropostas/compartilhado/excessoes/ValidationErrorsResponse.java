package br.com.zupacademy.fpsaraiva.microservicepropostas.compartilhado.excessoes;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorsResponse {

    private List<String> globalErrorMessages = new ArrayList<>();
    private List<FieldErrorResponse> fieldErrors = new ArrayList<>();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorResponse fieldError = new FieldErrorResponse(field, message);
        fieldErrors.add(fieldError);
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<FieldErrorResponse> getErrors() {
        return fieldErrors;
    }

}
