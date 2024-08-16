package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static org.hamcrest.Matchers.lessThan;

public class VideoGameConfig {

    @BeforeMethod
    public void setup() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://videogamedb.uk/")
                .setBasePath("api/v2/")
                .setContentType("application/json")
                .addHeader("Accept", "application/json")
                .addFilter(new RequestLoggingFilter()) // will log everything in the request
                .addFilter(new ResponseLoggingFilter()) // will log everything in the response
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                    .expectStatusCode(200)
                .expectResponseTime(lessThan(3000L))
                .build();
    }
}
