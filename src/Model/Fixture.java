package Model;

import Util.Get;
import processing.data.JSONObject;

import java.util.Date;

public class Fixture {
    public Date date;
    public String status;
    public Integer matchday;
    public String homeTeamName;
    public String awayTeamName;
    public Result result;

    public Fixture(JSONObject fixture) {
        date = Get.getDetailedDate(fixture.getString("date"));
        status = fixture.getString("status");
        matchday = fixture.getInt("matchday");
        homeTeamName = fixture.getString("homeTeamName");
        awayTeamName = fixture.getString("awayTeamName");
        result = new Result(fixture.getJSONObject("result"));
    }
}