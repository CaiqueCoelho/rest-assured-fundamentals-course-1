package testrunner;

import com.aventstack.extentreports.markuputils.MarkupHelper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import reporting.AssertionKeys;
import reporting.ExtentReportManager;
import reporting.Setup;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RestUtils {

//    private static void printRequestLogInReport(RequestSpecification requestSpecification, Object body) {
//        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
//        ExtentReportManager.logInfoDetails("Endpoint is " + queryableRequestSpecification.getBaseUri());
//        ExtentReportManager.logInfoDetails("Method is " + queryableRequestSpecification.getMethod());
//        ExtentReportManager.logInfoDetails("Headers is " + queryableRequestSpecification.getHeaders().asList().toString());
//        ExtentReportManager.logInfoDetails("Request body is " + body);
//    }
//
//    private static void printResponseLogInReport(Response response) {
//        ExtentReportManager.logInfoDetails("Response status is " + response.getStatusCode());
//        ExtentReportManager.logInfoDetails("Response Headers are " + response.getHeaders().asList().toString());
//        ExtentReportManager.logInfoDetails("Response body is " + response.getBody());
//    }

    public static Response performPost(String baseUrl, String endpoint, Object body, Map<String, String> headers) {
        Response response = given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .headers(headers)
                .body(body)
                .when()
                .post(endpoint);
//        printRequestLogInReport(RestAssured.requestSpecification, body);
//        printResponseLogInReport(response);
        return response;
    }

    public static void assertExpectedValuesWithJsonPath(Response response, Map<String, Object> expectedValuesMap) {
        List<AssertionKeys> actualValuesMap = new ArrayList<>();
        actualValuesMap.add(new AssertionKeys("JSON_PATH", "EXPECTED_VALUE", "ACTUAL_VALUE", "RESULT"));
        boolean allMatched = true;

        Set<String> jsonPaths = expectedValuesMap.keySet();
        for(String jsonPath: jsonPaths) {
            Optional<Object> actualValue = Optional.ofNullable(response.jsonPath().get(jsonPath));
            if(actualValue.isPresent()) {
                Object value = actualValue.get();
                if(value.equals(expectedValuesMap.get(jsonPath))) {
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "MATCHED"));
                } else {
                    allMatched = false;
                    actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), value, "NOT_MATCHED"));
                }
            } else {
                allMatched = false;
                actualValuesMap.add(new AssertionKeys(jsonPath, expectedValuesMap.get(jsonPath), "VALUE_NOT_FOUND", "NOT_MATCHED"));
            }
        }
        if(allMatched) {
            ExtentReportManager.logPassDetails("All assertions are passed!");
        } else {
            ExtentReportManager.logFailDetails("All assertions are not passed!");
        }

        String[][] finalAssertionsMap = actualValuesMap.stream()
                .map(assertions -> new String[]{
                        assertions.getJsonPath(),
                        String.valueOf(assertions.getExpectedValue()),
                        String.valueOf(assertions.getActualValue()),
                        String.valueOf(assertions.getResult())
                })
                .collect(Collectors.toList())
                .toArray(new String[0][]);
        Setup.extentTest.get().pass(MarkupHelper.createTable(finalAssertionsMap));
    }
}
