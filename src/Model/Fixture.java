package Model;

import Util.Util;
import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

import java.util.Date;

public class Fixture {
    @NotNull
    public Date date;
    @NotNull
    public String status;
    @NotNull
    public Integer matchday;
    @NotNull
    public String homeTeamName;
    @NotNull
    public String awayTeamName;
    @NotNull
    public Result result;

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
    public Integer goalsHomeTeam;
    @NotNull
    public Integer goalsAwayTeam;

    public Result(JSONObject result) {
        goalsHomeTeam = result.getInt("goalsHomeTeam", 0);
        goalsAwayTeam = result.getInt("goalsAwayTeam", 0);
    }
}