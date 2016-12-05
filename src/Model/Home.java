package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

public class Home {
    @NotNull
    public Integer goals;
    @NotNull
    public Integer goalsAgainst;
    @NotNull
    public Integer wins;
    @NotNull
    public Integer draws;
    @NotNull
    public Integer losses;

    Home(JSONObject home) {
        goals = home.getInt("goals");
        goalsAgainst = home.getInt("goalsAgainst");
        wins = home.getInt("wins");
        draws = home.getInt("draws");
        losses = home.getInt("losses");
    }
}
