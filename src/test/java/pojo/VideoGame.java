package pojo;

import lombok.Data;

@Data
public class VideoGame {

    private int id;
    private String category;
    private String name;
    private String rating;
    private String releaseDate;
    private String reviewScore;

    public VideoGame(String category, String name, String rating, String releaseDate, String reviewScore) {
        this.category = category;
        this.name = name;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.reviewScore = reviewScore;
    }

    public VideoGame() {}
}
