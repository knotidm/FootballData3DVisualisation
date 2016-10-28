import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

class Fixture {
    @NotNull
    String date;
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

    Fixture(JSONObject fixture) {
        date = fixture.getString("date");
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

    Result(JSONObject result) {
        goalsHomeTeam = result.getInt("goalsHomeTeam", 0);
        goalsAwayTeam = result.getInt("goalsAwayTeam", 0);
    }
}