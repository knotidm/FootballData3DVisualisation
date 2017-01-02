package Model;

import processing.data.JSONObject;

public class Result {
    public Integer goalsHomeTeam;
    public Integer goalsAwayTeam;

    Result(JSONObject result) {
        goalsHomeTeam = result.getInt("goalsHomeTeam", 0);
        goalsAwayTeam = result.getInt("goalsAwayTeam", 0);
    }
}