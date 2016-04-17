import processing.data.JSONObject;

class Fixture {

    String date;
    String status;
    Integer matchday;
    String homeTeamName;
    String awayTeamName;
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

    Integer goalsHomeTeam;
    Integer goalsAwayTeam;

    Result(JSONObject result) {

        if (!result.isNull("goalsHomeTeam")) {
            goalsHomeTeam = result.getInt("goalsHomeTeam");
            goalsAwayTeam = result.getInt("goalsAwayTeam");
        }
    }
}