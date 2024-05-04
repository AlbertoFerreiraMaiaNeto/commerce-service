package org.acme.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.constants.MessageConstants;
import org.acme.persistence.dto.ErrorResponse;

import static org.acme.constants.MessageConstants.ERROR_ORDER_NOT_FOUND_CODE;

public class OrderNotFoundException extends WebApplicationException {

    public OrderNotFoundException() {
        super(Response.status(Response.Status.NOT_FOUND)
                .entity(ErrorResponse.builder().code(ERROR_ORDER_NOT_FOUND_CODE).mensagem(MessageConstants.ERROR_ORDER_NOT_FOUND_MESSAGE).build())
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}