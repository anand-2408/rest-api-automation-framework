package com.sdet.framework.client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private final RequestSpecification specification;

    public ApiClient(RequestSpecification specification) {
        this.specification = specification;
    }

    public Response get(String path) {
        return given().spec(specification).when().get(path);
    }

    public Response get(String path, String parameter, Object value) {
        return given().spec(specification).queryParam(parameter, value).when().get(path);
    }

    public Response post(String path, Object body) {
        return given().spec(specification).body(body).when().post(path);
    }

    public Response put(String path, Object body) {
        return given().spec(specification).body(body).when().put(path);
    }

    public Response delete(String path) {
        return given().spec(specification).when().delete(path);
    }
}
