package Model;

import Util.Util;
import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

import java.util.Date;

public class Fixture {
    @NotNull
    Date date;
    @NotNull
    String status;
    @NotNull
    Integer matchday;
    @NotNull
    String homeTeamName;
    @NotNull
    String awayTeamName;
    @NotNull
    Result result;

    public Fixture(JSONObject fixture) {
        date = Util.getDate(fixture.getString("date"));
        status = fixture.getString("status");
        matchday = fixture.getInt("matchday");
        homeTeamName = fixture.getString("homeTeamName");
        awayTeamName = fixture.getString("awayTeamName");
        result = new Result(fixture.getJSONObject("result"));
    }
}

class Result {
    @NotNull
    Integer goalsHomeTeam;
    @NotNull
    Integer goalsAwayTeam;

    public Result(JSONObject result) {
        goalsHomeTeam = result.getInt("goalsHomeTeam", 0);
        goalsAwayTeam = result.getInt("goalsAwayTeam", 0);
    }
}