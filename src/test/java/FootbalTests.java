import config.FootbalConfig;
import config.FootbalEndpoints;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class FootbalTests extends FootbalConfig {

    @Test
    public void getDetailsOfOneArea() {
        given()
                .queryParam("areas", 2076)
                .when()
                .get(FootbalEndpoints.ALL_AREAS);
    }

    @Test
    public void getDetailsOfMultipleAreas() {
        String areaIds = "2076,2077,2080";

        given()
                .urlEncodingEnabled(false) // Because we are using commas in the areaIds we need to disable the encoding for comma
                .queryParam("areas", areaIds)
                .when()
                .get(FootbalEndpoints.ALL_AREAS);
    }

    @Test
    public void getDateFounded() {
        given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM)
                .then()
                .body("founded", equalTo(1886));
    }

    @Test
    public void getCompetitionTeams(){
        given()
                .pathParam("year", 2021)
                .when()
                .get(FootbalEndpoints.GET_COMPETITION_TEAMS)
                .then()
                .body("teams.name[0]", equalTo("Arsenal FC"));
    }

    @Test
    public void getAllTeamData() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        System.out.println(response.asString());
    }

    @Test
    public void getAllTeamDataWithChecks() {
        Response response = given()
                    .pathParam("id", 57)
                .when()
                    .get(FootbalEndpoints.GET_TEAM)
                .then()
                    .contentType(ContentType.JSON)
                    .extract().response();

        System.out.println(response.asString());
    }

    @Test
    public void extractHeader() {
        Response response = given()
                .pathParam("id", 57)
                .when()
                .get(FootbalEndpoints.GET_TEAM);

        Headers headers = response.getHeaders();
        System.out.println("HEADERS: " + headers.asList());

        String contentTypeHeader = response.getHeader("Content-Type");
        System.out.println("contentTypeHeader: " + contentTypeHeader);

        String contentTypeHeaderDirectly = response.getContentType();
        System.out.println("contentTypeHeaderDirectly: " + contentTypeHeaderDirectly);

        String xApiVersionHeader = response.getHeader("X-API-Version");
        System.out.println("xApiVersionHeader: " + xApiVersionHeader);
    }

    @Test
    public void extractFirstTeamName(){
        String firstTeamName = given()
                .pathParam("year", 2021)
                .when()
                .get(FootbalEndpoints.GET_COMPETITION_TEAMS)
                .jsonPath().getString("teams.name[0]");
        System.out.println(firstTeamName);
    }

    @Test
    public void extractAllTeamNames() {
        Response response = given()
                .pathParam("year", 2021)
                .when()
                .get(FootbalEndpoints.GET_COMPETITION_TEAMS);

        List<String> teamNames = response.jsonPath().getList("teams.name");
        System.out.println(teamNames);

        List<String> teamNames2 = response.path("teams.name");
        System.out.println(teamNames2);
    }
}
