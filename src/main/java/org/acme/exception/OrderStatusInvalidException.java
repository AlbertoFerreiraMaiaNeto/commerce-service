package org.acme.exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.constants.MessageConstants;
import org.acme.persistence.dto.ErrorResponse;

import static org.acme.constants.MessageConstants.ERROR_ORDER_STATUS_IS_INVALID_CODE;

public class OrderStatusInvalidException extends WebApplicationException {

    public OrderStatusInvalidException() {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponse.builder().code(ERROR_ORDER_STATUS_IS_INVALID_CODE).mensagem(MessageConstants.ERROR_ORDER_STATUS_IS_INVALID_MESSAGE).build())
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}