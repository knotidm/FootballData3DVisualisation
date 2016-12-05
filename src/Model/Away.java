package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

public class Away {
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

    Away(JSONObject away) {
        goals = away.getInt("goals");
        goalsAgainst = away.getInt("goalsAgainst");
        wins = away.getInt("wins");
        draws = away.getInt("draws");
        losses = away.getInt("losses");
    }
}
