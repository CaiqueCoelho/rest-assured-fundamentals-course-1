package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.junit.Before;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.util.Map;

import static org.hamcrest.Matchers.lessThan;

public class InstantWebToolsConfig {

    @BeforeMethod
    public void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api.instantwebtools.net")
                .setBasePath("/v1")
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter()) // will log everything in the request
                .addFilter(new ResponseLoggingFilter()) // will log everything in the response
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectResponseTime(lessThan(3000L)).build();
    }

    public static Map<String, Object> dataFromJsonFile;
    static {
        String env = System.getProperty("env") == null ? "qa" : System.getProperty("env");
        try {
            dataFromJsonFile = JsonUtils.getJsonDataAsMap(env + "/airlinesApiData.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
