package com.sdet.framework.specs;

import com.sdet.framework.config.ConfigManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public final class SpecificationFactory {
    private SpecificationFactory() {
    }

    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigManager.get("base.uri"))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("x-api-key", ConfigManager.get("api.key"))
                .build();
    }

    public static ResponseSpecification jsonResponseSpecification() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan((long) ConfigManager.getInt("response.timeout.ms")))
                .build();
    }
}
