package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

public class Result {
    @NotNull
    public Integer goalsHomeTeam;
    @NotNull
    public Integer goalsAwayTeam;

    public Result(JSONObject result) {
        goalsHomeTeam = result.getInt("goalsHomeTeam", 0);
        goalsAwayTeam = result.getInt("goalsAwayTeam", 0);
    }
}
