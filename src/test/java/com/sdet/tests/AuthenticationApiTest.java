package com.sdet.tests;

import com.sdet.framework.models.LoginRequest;
import com.sdet.framework.models.LoginResponse;
import com.sdet.framework.specs.SpecificationFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class AuthenticationApiTest extends BaseTest {

    @Test(description = "Authenticate with valid credentials and deserialize the token")
    public void shouldLoginSuccessfully() {
        LoginResponse response = apiClient.post("/api/login",
                        new LoginRequest("eve.holt@reqres.in", "cityslicka"))
                .then()
                .spec(SpecificationFactory.jsonResponseSpecification())
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        Assert.assertNotNull(response.token(), "Authentication token should be returned");
        Assert.assertFalse(response.token().isBlank(), "Authentication token should not be blank");
    }

    @Test(description = "Reject authentication when the password is absent")
    public void shouldRejectLoginWithoutPassword() {
        apiClient.post("/api/login", new LoginRequest("peter@klaven", null))
                .then()
                .spec(SpecificationFactory.jsonResponseSpecification())
                .statusCode(400)
                .body("error", equalTo("Missing password"));
    }
}
