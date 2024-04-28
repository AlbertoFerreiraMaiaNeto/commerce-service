package org.acme.controller;

import jakarta.annotation.Nullable;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.flow.product.*;
import org.acme.persistence.dto.ProductDTO;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.validation.Valid;
import java.util.List;

@Path("/product")
public class ProductController {

    private final CreateProductFlow createProductFlow;
    private final UpdateProductFlow updateProductFlow;
    private final GetProductFlow getProductFlow;
    private final ListAllProductsFlow listAllProductsFlow;
    private final DeleteProductFlow deleteProductFlow;


    public ProductController(CreateProductFlow createProductFlow, UpdateProductFlow updateProductFlow,
                             GetProductFlow getProductFlow, ListAllProductsFlow listAllProductsFlow,
                             DeleteProductFlow deleteProductFlow) {
        this.createProductFlow = createProductFlow;
        this.updateProductFlow = updateProductFlow;
        this.getProductFlow = getProductFlow;
        this.listAllProductsFlow = listAllProductsFlow;
        this.deleteProductFlow = deleteProductFlow;
    }

    @POST
    @Tag(name = "Product")
    @Operation(summary = "Create a Product",
            description = "Endpoint for create a product")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found. / Product not found."),
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ProductDTO createProduct(@Valid ProductDTO product) {
        return createProductFlow.createProduct(product);
    }

    @PUT
    @Tag(name = "Product")
    @Operation(summary = "Update a Product",
            description = "Endpoint for update a product")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found. / Product not found.")
    })
    @Path("/{categoryName}/{productName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ProductDTO updateProduct(@Valid ProductDTO product, String categoryName, String productName) {
        return this.updateProductFlow.updateProduct(product, categoryName, productName);
    }

    @GET
    @Tag(name = "Product")
    @Operation(summary = "Get a Product",
            description = "Endpoint for get a product by name")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found. / Product not found.")
    })
    @Path("/{categoryName}/{productName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public ProductDTO getProduct(String categoryName, String productName) {
        return this.getProductFlow.getProduct(categoryName, productName);
    }

    @GET
    @Tag(name = "Product")
    @Operation(summary = "List all Products",
            description = "Endpoint for list all products")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found.")
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<ProductDTO> listAllProducts(@HeaderParam("x-category") @Nullable String xCategory) {
        return this.listAllProductsFlow.listAllProducts(xCategory);
    }

    @DELETE
    @Tag(name = "Product")
    @Operation(summary = "Delete a Product",
            description = "Endpoint for Delete a product")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found. / Product not found."),
    })
    @Path("/{categoryName}/{productName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteProduct(String categoryName, String productName) {
        deleteProductFlow.deleteProduct(categoryName, productName);
    }
}