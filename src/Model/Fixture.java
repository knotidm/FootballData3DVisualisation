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

    public Fixture() {
    }

    public Fixture(JSONObject fixture) {
        date = Util.getDate(fixture.getString("date"));
        status = fixture.getString("status");
        matchday = fixture.getInt("matchday");
        homeTeamName = fixture.getString("homeTeamName");
        awayTeamName = fixture.getString("awayTeamName");
        result = new Result(fixture.getJSONObject("result"));
    }
}

