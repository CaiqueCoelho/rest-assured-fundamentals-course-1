package testrunner;

import config.AirlineEndpoints;
import config.InstantWebToolsConfig;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import pojo.Airline;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static config.InstantWebToolsConfig.dataFromJsonFile;

public class AirlineNewtests extends InstantWebToolsConfig {

    @Test
    public void createAirline() {
        String environmentBaseUrl = (String) dataFromJsonFile.get("createAirLineEndpoint");

        // Airline airline = new Airline();
        Airline airline = new Airline().toBuilder().name("Caíque").build();
        Response response = RestUtils.performPost(environmentBaseUrl, AirlineEndpoints.ALL_AIRLINES, airline, new HashMap<>());
        Assert.assertEquals(response.jsonPath().getString("name"), airline.getName());

        Airline createdAirline = response.getBody().as(Airline.class);
        Map<String, Object> expectedValuesMap = getStringObjectMap(airline);

        RestUtils.assertExpectedValuesWithJsonPath(response, expectedValuesMap);
    }

    private static Map<String, Object> getStringObjectMap(Airline airline) {
        Map<String, Object> expectedValuesMap = new HashMap<>();
        expectedValuesMap.put("id", airline.getId());
        expectedValuesMap.put("name", airline.getName());
        expectedValuesMap.put("country", airline.getCountry());
        expectedValuesMap.put("logo", airline.getLogo());
        expectedValuesMap.put("slogan", airline.getSlogan());
        expectedValuesMap.put("head_quaters", airline.getHeadQuaters());
        expectedValuesMap.put("website", airline.getWebsite());
        expectedValuesMap.put("established", airline.getEstablished());
        return expectedValuesMap;
    }
}
