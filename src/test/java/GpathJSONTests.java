import config.FootbalConfig;
import config.FootbalEndpoints;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GpathJSONTests extends FootbalConfig {

    @Test
    public void extractMapOfElementsWithFind() {
        Response response = given()
                .pathParam("year", 2021)
                .when()
                .get(FootbalEndpoints.GET_COMPETITION_TEAMS);

        Map<String, ?> allTeamDataForSingleTeam = response.path("teams.find { it.name == 'Manchester United FC' }");

        System.out.println("Map of team data = " + allTeamDataForSingleTeam);

        // assert allTeamDataForSingleTeam is not null
        Assert.assertNotNull(allTeamDataForSingleTeam);
    }

    @Test
    public void extractSingleValueWithFind() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        String certainPlayer = response.path("squad.find { it.id == 8984 }.name");
        System.out.println("certainPlayer name = " + certainPlayer);
        Assert.assertNotNull(certainPlayer);
    }

    @Test
    public void extractLitOfValuesWithFindAll() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        List<String> playersName = response.path("squad.findAll { it.id >= 7784 }.name");
        System.out.println("certainPlayer name = " + playersName);
        Assert.assertNotNull(playersName);
    }

    @Test
    public void extractSingleValueWithHighestNumber() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        String playerName = response.path("squad.max { it.id }.name");
        System.out.println("certainPlayer name = " + playerName);
        Assert.assertNotNull(playerName);
    }

    @Test
    public void extractSingleValueWithLowestNumber() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        String playerName = response.path("squad.min { it.id }.name");
        System.out.println("certainPlayer name = " + playerName);
        Assert.assertNotNull(playerName);
    }

    @Test
    public void extractMultipleValuesAndSumThem() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        int sumOf = response.path("squad.collect { it.id }.sum()");
        System.out.println("sumOf = " + sumOf);
        Assert.assertNotNull(sumOf);
    }

    @Test
    public void extractMapWithFindAllWithParameters() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        String position = "Centre-Back";
        String nationality = "Brazil";

        Map<String, String> playerOfCertainPosition = response.path("squad.findAll { it.position == '%s'}.find{ it.nationality == '%s' }", position, nationality);
        System.out.println("playersOfCertainPosition = " + playerOfCertainPosition);
        Assert.assertNotNull(playerOfCertainPosition);
    }

    @Test
    public void extractListWithFindAllWithParameters() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        String position = "Right Winger";
        String nationality = "England";

        List<Map<String, String>> playersOfCertainPosition = response.path("squad.findAll { it.position == '%s'}.findAll{ it.nationality == '%s' }", position, nationality);
        System.out.println("playersOfCertainPosition = " + playersOfCertainPosition);
        Assert.assertNotNull(playersOfCertainPosition);
    }
}