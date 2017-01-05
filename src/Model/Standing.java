package Model;

import processing.data.JSONObject;

public class Standing {
    public String teamName;
    public Integer goals;
    public Integer goalsAgainst;
    public Integer wins;
    public Integer draws;
    public Integer losses;
    public Home home;
    public Away away;

    public Standing(JSONObject standing) {
        teamName = standing.getString("teamName");
        goals = standing.getInt("goals");
        goalsAgainst = standing.getInt("goalsAgainst");
        wins = standing.getInt("wins");
        draws = standing.getInt("draws");
        losses = standing.getInt("losses");
        home = new Home(standing.getJSONObject("home"));
        away = new Away(standing.getJSONObject("away"));
    }
}