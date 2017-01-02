package Model;

import processing.data.JSONObject;

public class Home {
    public Integer goals;
    public Integer goalsAgainst;
    public Integer wins;
    public Integer draws;
    public Integer losses;

    Home(JSONObject home) {
        goals = home.getInt("goals");
        goalsAgainst = home.getInt("goalsAgainst");
        wins = home.getInt("wins");
        draws = home.getInt("draws");
        losses = home.getInt("losses");
    }
}