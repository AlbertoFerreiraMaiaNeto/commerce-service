package org.acme.persistence.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@Getter
@Setter
public class ErrorResponse {

    private String code;
    private String mensagem;

    public ErrorResponse(String code, String error) {
        this.code = code;
        this.mensagem = error;
    }
}