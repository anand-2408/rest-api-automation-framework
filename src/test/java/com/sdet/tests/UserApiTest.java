package com.sdet.tests;

import com.sdet.framework.models.UserRequest;
import com.sdet.framework.specs.SpecificationFactory;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class UserApiTest extends BaseTest {

    @Test(description = "GET a paginated user list and validate its JSON schema")
    public void shouldGetUsersAndMatchSchema() {
        apiClient.get("/api/users", "page", 2)
                .then()
                .spec(SpecificationFactory.jsonResponseSpecification())
                .statusCode(200)
                .body("page", equalTo(2))
                .body("data", not(empty()))
                .body("data.email", everyItem(containsString("@")))
                .body(matchesJsonSchemaInClasspath("schemas/users-list-schema.json"));
    }

    @Test(description = "GET one user and validate nested response fields")
    public void shouldGetSingleUser() {
        apiClient.get("/api/users/2")
                .then()
                .spec(SpecificationFactory.jsonResponseSpecification())
                .statusCode(200)
                .body("data.id", equalTo(2))
                .body("data.email", not(blankOrNullString()))
                .body("data.first_name", not(blankOrNullString()));
    }

    @Test(description = "POST a new user using Jackson serialization")
    public void shouldCreateUser() {
        UserRequest request = new UserRequest("Aarav", "SDET");

        apiClient.post("/api/users", request)
                .then()
                .spec(SpecificationFactory.jsonResponseSpecification())
                .statusCode(201)
                .body("name", equalTo(request.name()))
                .body("job", equalTo(request.job()))
                .body("id", not(blankOrNullString()))
                .body("createdAt", not(blankOrNullString()));
    }

    @Test(description = "PUT an existing user")
    public void shouldUpdateUser() {
        UserRequest request = new UserRequest("Aarav", "Senior SDET");

        apiClient.put("/api/users/2", request)
                .then()
                .spec(SpecificationFactory.jsonResponseSpecification())
                .statusCode(200)
                .body("name", equalTo(request.name()))
                .body("job", equalTo(request.job()))
                .body("updatedAt", not(blankOrNullString()));
    }

    @Test(description = "DELETE a user and validate the empty response")
    public void shouldDeleteUser() {
        apiClient.delete("/api/users/2")
                .then()
                .statusCode(204)
                .body(emptyOrNullString());
    }

    @Test(description = "Validate the API response for a missing user")
    public void shouldReturnNotFoundForUnknownUser() {
        apiClient.get("/api/users/23")
                .then()
                .spec(SpecificationFactory.jsonResponseSpecification())
                .statusCode(404)
                .body("$", anEmptyMap());
    }
}
