package com.sdet.tests;

import com.sdet.framework.client.ApiClient;
import com.sdet.framework.config.ConfigManager;
import com.sdet.framework.specs.SpecificationFactory;
import io.restassured.RestAssured;
import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

public abstract class BaseTest {
    protected static ApiClient apiClient;

    @BeforeSuite(alwaysRun = true)
    public void configureFramework() {
        if ("YOUR_REQRES_API_KEY".equals(ConfigManager.get("api.key"))) {
            throw new SkipException("Set REQRES_API_KEY or pass -Dapi.key with a current ReqRes key");
        }
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        apiClient = new ApiClient(SpecificationFactory.requestSpecification());
    }
}
