package config;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;

public class FootbalConfig {

    @BeforeClass
    public static void setup(){
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api.football-data.org")
                .setBasePath("/v4")
                .setContentType("application/json")
                .addHeader("X-Auth-Token", "2466d59035994759b14ffd9316bbb700")
                .addHeader("X-Response-Control", "minified")
                .addFilter(new RequestLoggingFilter()) // will log everything in the request
                .addFilter(new ResponseLoggingFilter()) // will log everything in the response
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).build();
    }
}
