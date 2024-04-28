package org.acme;

import io.quarkus.runtime.Quarkus;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "commerce-service",
                version = "1.0",
                description = "Service for creating, reading, updating, and deleting products in the catalog, as well as processing orders.",
                contact = @Contact(url = "https://github.com/AlbertoFerreiraMaiaNeto/", name = "Alberto", email = "albertoferreiramaianeto@gmail.com")
        )
)
public class Application {

    public static void main(String[] args) {Quarkus.run(args);}
}