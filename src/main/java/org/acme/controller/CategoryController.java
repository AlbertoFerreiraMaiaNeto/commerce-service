package org.acme.controller;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.acme.flow.category.CreateCategoryFlow;
import org.acme.flow.category.GetCategoryFlow;
import org.acme.flow.category.ListAllCategoriesFlow;
import org.acme.flow.category.UpdateCategoryFlow;
import org.acme.persistence.model.Category;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/category")
public class CategoryController {

    private final CreateCategoryFlow createCategoryFlow;
    private final UpdateCategoryFlow updateCategoryFlow;
    private final GetCategoryFlow getCategoryFlow;
    private final ListAllCategoriesFlow listAllCategoriesFlow;

    @Inject
    public CategoryController(CreateCategoryFlow createCategoryFlow, UpdateCategoryFlow updateCategoryFlow,
                              GetCategoryFlow getCategoryFlow, ListAllCategoriesFlow listAllCategoriesFlow) {
        this.createCategoryFlow = createCategoryFlow;
        this.updateCategoryFlow = updateCategoryFlow;
        this.getCategoryFlow = getCategoryFlow;
        this.listAllCategoriesFlow = listAllCategoriesFlow;
    }

    @POST
    @Tag(name = "Category")
    @Operation(summary = "Create a Category",
            description = "Endpoint for create a category")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "400", description = "Category already exists.")
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Category createCategory(Category category) {
        return this.createCategoryFlow.createCategory(category);
    }

    @PUT()
    @Tag(name = "Category")
    @Operation(summary = "Update a Category",
            description = "Endpoint for update a category")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found.")
    })
    @Path("/{categoryName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Category updateCategory(Category category, String categoryName) {
        return this.updateCategoryFlow.updateCategory(category, categoryName);
    }

    @GET
    @Tag(name = "Category")
    @Operation(summary = "Get a Category",
            description = "Endpoint for get a category by name")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation"),
            @APIResponse(responseCode = "404", description = "Category not found.")
    })
    @Tag(name = "Category")
    @Path("/{categoryName}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Category getCategory(String categoryName) {
        return this.getCategoryFlow.getCategory(categoryName);
    }

    @GET
    @Tag(name = "Category")
    @Operation(summary = "List All Category",
            description = "Endpoint for List All Categories")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Successful operation")
    })
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Category> listAllCategories() {
        return this.listAllCategoriesFlow.listAllCategories();
    }
}