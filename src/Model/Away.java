package Model;

import processing.data.JSONObject;

public class Away {
    public Integer goals;
    public Integer goalsAgainst;
    public Integer wins;
    public Integer draws;
    public Integer losses;

    Away(JSONObject away) {
        goals = away.getInt("goals");
        goalsAgainst = away.getInt("goalsAgainst");
        wins = away.getInt("wins");
        draws = away.getInt("draws");
        losses = away.getInt("losses");
    }
}