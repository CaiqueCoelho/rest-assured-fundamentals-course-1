import config.VideoGameConfig;
import config.VideoGameEndpoints;
import io.restassured.http.ContentType;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.*;

public class GPathXMLTests extends VideoGameConfig {

    @Test
    public void getFirstGameInList() {
        Response response =
                    given()
                        .accept(ContentType.XML)
                    .when()
                        .get(VideoGameEndpoints.ALL_VIDEO_GAMES);

        String name = response.path("List.item.name[0]");

        System.out.println("Name: " + name);
        Assert.assertNotNull(name);
    }

    @Test
    public void getAttribute() {
        Response response =
                given()
                    .accept(ContentType.XML)
                .when()
                    .get(VideoGameEndpoints.ALL_VIDEO_GAMES);

        String category = response.path("List.item[0].@category");
        System.out.println("Category: " + category);
        Assert.assertNotNull(category);
    }

    @Test
    public void getListOfXmlNodes() {
        String response = given()
                .accept(ContentType.XML)
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .asString();

        List<Node> allResults = XmlPath.from(response).get("List.item.findAll { element -> return element }");

        System.out.println("allResults item 2: " + allResults.get(2));
        System.out.println("allResults item 2 name: " + allResults.get(2).get("name"));
        Assert.assertNotNull(allResults);
    }

    @Test
    public void getListOfXmlNodesByFindAllOnAttribue() {
        String responseString = given().accept(ContentType.XML)
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .asString();

        List<Node> allDrivingGames = XmlPath.from(responseString).get("List.item.findAll { game -> def category = game.@category; category == 'Driving' }");
        System.out.println("allDrivingGames: " + allDrivingGames.get(0).get("name").toString());
        System.out.println("allDrivingGames: " + allDrivingGames);
        Assert.assertNotNull(allDrivingGames);
    }

    @Test
    public void getSingleNode(){
        String responseString = given().accept(ContentType.XML)
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .asString();

        Node videoGame = XmlPath.from(responseString).get("List.item.find { game -> def name = game.name; name == 'Tetris' }");
        System.out.println("videoGame: " + videoGame);
        System.out.println("videoGame name: " + videoGame.get("name"));
        Assert.assertNotNull(videoGame);
    }

    @Test
    public void getSingleElementDepthFirstSearch() {
        String responseString = given().accept(ContentType.XML)
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .asString();

        int reviewScore = XmlPath.from(responseString).getInt("**.find { it.name == 'Gran Turismo 3' }.reviewScore");
        System.out.println("reviewScore: " + reviewScore);
        Assert.assertNotNull(reviewScore);
    }

    @Test
    public void getOnlyGamesWithReviewScoreAbove90() {
        String responseString = given().accept(ContentType.XML)
                .when()
                .get(VideoGameEndpoints.ALL_VIDEO_GAMES)
                .asString();

        int reviewScoreThreshold = 90;

        List<Node> gamesWithReviewScoreAbove90 = XmlPath.from(responseString).get("List.item.findAll { it.reviewScore.toFloat() >= " + reviewScoreThreshold + " }");
        System.out.println("gamesWithReviewScoreAbove90: " + gamesWithReviewScoreAbove90);
        gamesWithReviewScoreAbove90.forEach(game -> System.out.println("game name: " + game.get("name") + " reviewScore: " + game.get("reviewScore")));
        Assert.assertNotNull(gamesWithReviewScoreAbove90);
    }
}
