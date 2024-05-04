package org.acme.controller;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.flow.order.CreateOrderFlow;
import org.acme.flow.order.UpdateOrderStatusAndStockFlow;
import org.acme.persistence.dto.OrderDTO;
import org.acme.persistence.dto.OrderResponseDTO;
import org.acme.persistence.dto.StatusOrderAndStockDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.Valid;

@Path("/order")
public class OrderController {

    private final CreateOrderFlow createOrderFlow;
    private final UpdateOrderStatusAndStockFlow updateOrderStatusAndStockFlow;


    public OrderController(CreateOrderFlow createOrderFlow,
                           UpdateOrderStatusAndStockFlow updateOrderStatusAndStockFlow) {
        this.createOrderFlow = createOrderFlow;
        this.updateOrderStatusAndStockFlow = updateOrderStatusAndStockFlow;
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

    @PATCH()
    @Tag(name = "Order")
    @Operation(summary = "Update a Order Status and Stock of products",
            description = "Endpoint for update a category")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found.")
    })
    @Path("/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateStatusOrderAndStock(Long orderId, @Valid StatusOrderAndStockDTO statusOrderAndStockDTO) {
         this.updateOrderStatusAndStockFlow.updateOrderStatusAndStock(orderId, statusOrderAndStockDTO);
    }
}