package testrunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import config.AirlineEndpoints;
import config.InstantWebToolsConfig;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.junit.Assert;
import org.testng.annotations.Test;
import pojo.Airline;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AirlinesTests extends InstantWebToolsConfig {

    @Test
    public void createAirline() throws IOException {
        String environmentBaseUrl = (String) dataFromJsonFile.get("createAirLineEndpoint");

        Map<String, Object> payload = new HashMap<>();
        payload.put("id", 12120L);
        payload.put("name", "Quatar Airways");
        payload.put("country", "Quatar");
        payload.put("logo", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/Qatar_Airways_Logo.png/800px-Qatar_Airways_Logo.png");
        payload.put("slogan", "Going Places");
        payload.put("head_quaters", "Some place");
        payload.put("website", "https://www.qatarairways.com/");
        payload.put("established", "2000");


        Airline airline = new Airline(
                12120L,
                "Quatar Airways",
                "Quatar",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c2/Qatar_Airways_Logo.png/800px-Qatar_Airways_Logo.png",
                "Going Places",
                "Some place",
                "https://www.qatarairways.com/",
                "2000"
        );
        Response response = RestUtils.performPost(environmentBaseUrl, AirlineEndpoints.ALL_AIRLINES, airline, new HashMap<>());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void createAirlineWithDataFaker() throws IOException {
        Faker faker = new Faker();
        String environmentBaseUrl = (String) dataFromJsonFile.get("createAirLineEndpoint");

        Airline airline = new Airline(
                Long.parseLong(faker.number().digits(10)),
                faker.name().firstName(),
                faker.address().country(),
                faker.internet().url(),
                faker.funnyName().toString(),
                faker.address().cityName(),
                faker.internet().url(),
                String.valueOf(faker.number().numberBetween(1900, 2020))
        );
        Response response = RestUtils.performPost(environmentBaseUrl, AirlineEndpoints.ALL_AIRLINES, airline, new HashMap<>());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void createAirlineWithDataFakerAndENUM() throws IOException {
        String environmentBaseUrl = (String) dataFromJsonFile.get("createAirLineEndpoint");

        Airline airline = new Airline(
                RandomDataGenerator.getRandomNumber(10),
                RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME),
                RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.COUNTRY),
                RandomDataGenerator.getRandomUrl(),
                RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME),
                RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.CITYNAME),
                RandomDataGenerator.getRandomUrl(),
                RandomDataGenerator.getRandomYearBetween(1900, 2020)
        );

        // or
        Airline airline2 = Airline.builder()
                .id(RandomDataGenerator.getRandomNumber(10))
                .name(RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME))
                .country(RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.COUNTRY))
                .logo(RandomDataGenerator.getRandomUrl())
                .slogan(RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.FIRSTNAME))
                .headQuaters(RandomDataGenerator.getRandomDataFor(RandomDataTypeNames.CITYNAME))
                .website(RandomDataGenerator.getRandomUrl())
                .established(RandomDataGenerator.getRandomYearBetween(1900, 2020))
                .build();

        Response response = RestUtils.performPost(environmentBaseUrl, AirlineEndpoints.ALL_AIRLINES, airline, new HashMap<>());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void createAirlineWithDefaultData() throws IOException {
        String environmentBaseUrl = (String) dataFromJsonFile.get("createAirLineEndpoint");

        // Airline airline = new Airline();
        Airline airline = new Airline().toBuilder().name("Caíque").build();
        Response response = RestUtils.performPost(environmentBaseUrl, AirlineEndpoints.ALL_AIRLINES, airline, new HashMap<>());
        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void createAirlineAndVerifyResponse() throws IOException {
        String environmentBaseUrl = (String) dataFromJsonFile.get("createAirLineEndpoint");

        // Airline airline = new Airline();
        Airline airline = new Airline().toBuilder().name("Caíque").build();
        Response response = RestUtils.performPost(environmentBaseUrl, AirlineEndpoints.ALL_AIRLINES, airline, new HashMap<>());
        Assert.assertEquals(response.jsonPath().getString("name"), airline.getName());

        Airline createdAirline = response.getBody().as(Airline.class);
        Assert.assertEquals(createdAirline, airline);
    }
}
