package org.acme.controller;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.flow.order.CreateOrderFlow;
import org.acme.persistence.dto.OrderDTO;
import org.acme.persistence.dto.OrderResponseDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.Valid;

@Path("/order")
public class OrderController {

    private final CreateOrderFlow createOrderFlow;


    public OrderController(CreateOrderFlow createOrderFlow) {
        this.createOrderFlow = createOrderFlow;
    }

    @POST
    @Tag(name = "Order")
    @Operation(summary = "Create a Order",
            description = "Endpoint for create a order")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found. / Product not found."),
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public OrderResponseDTO createOrder(@Valid OrderDTO orderDTO) {
        return this.createOrderFlow.createOrder(orderDTO);
    }
}