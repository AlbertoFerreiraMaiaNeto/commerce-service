package org.acme.exception;


import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.constants.MessageConstants;
import org.acme.persistence.dto.ErrorResponse;

import static org.acme.constants.MessageConstants.ERROR_PRODUCT_ALREADY_EXISTS_CODE;

public class ProductAlreadyExistsException extends WebApplicationException {

    public ProductAlreadyExistsException() {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(ErrorResponse.builder().code(ERROR_PRODUCT_ALREADY_EXISTS_CODE).mensagem(MessageConstants.ERROR_PRODUCT_ALREADY_EXISTS_MESSAGE).build())
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}