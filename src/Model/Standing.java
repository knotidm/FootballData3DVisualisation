package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

public class Standing {
    @NotNull
    public Integer position;
    @NotNull
    public String teamName;
    @NotNull
    public Integer playedGames;
    @NotNull
    public Integer points;
    @NotNull
    public Integer goals;
    @NotNull
    public Integer goalsAgainst;
    @NotNull
    public Integer goalDifference;
    @NotNull
    public Integer wins;
    @NotNull
    public Integer draws;
    @NotNull
    public Integer losses;
    @NotNull
    public Home home;
    @NotNull
    public Away away;

    Standing(JSONObject standing) {
        position = standing.getInt("position");
        teamName = standing.getString("teamName");
        playedGames = standing.getInt("playedGames");
        points = standing.getInt("points");
        goals = standing.getInt("goals");
        goalsAgainst = standing.getInt("goalsAgainst");
        goalDifference = standing.getInt("goalDifference");
        wins = standing.getInt("wins");
        draws = standing.getInt("draws");
        losses = standing.getInt("losses");
        home = new Home(standing.getJSONObject("home"));
        away = new Away(standing.getJSONObject("away"));
    }
}

