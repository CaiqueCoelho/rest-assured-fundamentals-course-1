package config;

public interface FootbalEndpoints {

    String ALL_AREAS = "/areas";
    String ALL_TEAMS = "/teams";
    String GET_TEAM = "/teams/{id}";
    String GET_COMPETITION = "/competitions/{year}";
    String GET_COMPETITION_TEAMS = "/competitions/{year}/teams";
}
