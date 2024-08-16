import com.fasterxml.jackson.databind.util.JSONPObject;
import config.VideoGameConfig;
import config.VideoGameEndpoints;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Test;
import org.hamcrest.Matchers.*;
import pojo.VideoGame;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class VideoGameTests extends VideoGameConfig {

    @Test
    public void getAllVideoGames() {
        given()
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then()
                .statusCode(200);
    }

    @Test
    public void createNewGameWithJSON() {
        JSONObject body = new JSONObject();
        body.put("category", "Platform");
        body.put("name", "Mario");
        body.put("rating", "Mature");
        body.put("releaseDate", "1985-09-13");
        body.put("reviewScore", "90");

        given()
                .body(body.toString())
                .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then()
                .statusCode(200);
    }

    @Test
    public void createNewGameWithXML() {
        String body = "<VideoGameRequest>"
                + "<category>Platform</category>"
                + "<name>Mario</name>"
                + "<rating>Mature</rating>"
                + "<releaseDate>1985-09-13</releaseDate>"
                + "<reviewScore>90</reviewScore>"
                + "</VideoGameRequest>";

        given()
                .body(body)
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then()
                .statusCode(200);
    }

    @Test
    public void updateGame() {
        JSONObject body = new JSONObject();
        body.put("category", "Platform");
        body.put("name", "Mario");
        body.put("rating", "Mature");
        body.put("releaseDate", "1985-09-13");
        body.put("reviewScore", "90");

        given().body(body.toString())
                .when()
                .put(VideoGameEndpoints.ALL_VIDEO_GAMES + "/3")
                .then()
                .statusCode(200).body("name", equalTo("Mario"));
    }

    @Test
    public void deleteGame() {
        given()
                .accept("text/plain")
                .when()
                .delete(VideoGameEndpoints.ALL_VIDEO_GAMES + "/3")
                .then()
                .statusCode(200);
    }

    @Test
    public void getSingleGame() {
        given().pathParam("id", 5)
                .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
                .then()
                .statusCode(200)
                .body("id", equalTo(5));
    }

    @Test
    public void testVideoGameSerializationByJSON() {
        VideoGame videoGame = new VideoGame("Platform", "Mario", "Mature", "1985-09-13", "90");

        given()
                .body(videoGame)
                .when()
                .post(VideoGameEndpoints.ALL_VIDEO_GAMES);
    }

    @Test
    public void testVideoGameSchemaXML() {
        given()
                .pathParam("id", 5)
                .accept(ContentType.XML)
                .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
                .then()
                .body(RestAssuredMatchers.matchesXsdInClasspath("VideoGameXSD.xsd"));
    }

    @Test
    public void testVideoGameSchemaJSON() {
        given()
                .   pathParam("id", 5)
                .when()
                    .get(VideoGameEndpoints.SINGLE_VIDEO_GAME)
                .then()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("VideoGameJsonSchema.json"));
    }

    @Test
    public void convertJsonToPojo() {
        Response response = given()
                .pathParam("id", 5)
                .when()
                .get(VideoGameEndpoints.SINGLE_VIDEO_GAME);

        VideoGame videoGame = response.getBody().as(VideoGame.class);
        System.out.println(videoGame);
    }

    @Test
    public void captureResponseTime() {
        long responseTime = given()
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .time();
        System.out.println("Response time in milliseconds: " + responseTime);
    }

    @Test
    public void assertResponseTime() {
        given()
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .then()
                .time(Matchers.lessThan(1123L));
    }
}
