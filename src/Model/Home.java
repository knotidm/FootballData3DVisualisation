package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

import javax.persistence.*;

@Entity
public class Home {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer homeId;
    @NotNull
    private Integer goals;
    @NotNull
    private Integer goalsAgainst;
    @NotNull
    private Integer wins;
    @NotNull
    private Integer draws;
    @NotNull
    private Integer losses;

    public Home() {
    }

    Home(JSONObject home) {
        goals = home.getInt("goals");
        goalsAgainst = home.getInt("goalsAgainst");
        wins = home.getInt("wins");
        draws = home.getInt("draws");
        losses = home.getInt("losses");
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

    @Basic
    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    @Basic
    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    @Basic
    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    @Basic
    public Integer getDraws() {
        return draws;
    }

    public void setDraws(Integer draws) {
        this.draws = draws;
    }

    @Basic
    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }
}
