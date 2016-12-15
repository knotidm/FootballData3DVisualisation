package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

import javax.persistence.*;

@Entity
public class Standing {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer standingId;
    @NotNull
    private String teamName;
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
    @NotNull
    @OneToOne
    private Home home;
    @NotNull
    @OneToOne
    private Away away;
    @ManyToOne
    private Competition competition;

    public Standing() {
    }

    Standing(JSONObject standing) {
        teamName = standing.getString("teamName");
        goals = standing.getInt("goals");
        goalsAgainst = standing.getInt("goalsAgainst");
        wins = standing.getInt("wins");
        draws = standing.getInt("draws");
        losses = standing.getInt("losses");
        home = new Home(standing.getJSONObject("home"));
        away = new Away(standing.getJSONObject("away"));
    }

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getStandingId() {
        return standingId;
    }

    public void setStandingId(Integer standingId) {
        this.standingId = standingId;
    }

    @Basic
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
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

    @OneToOne
    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    @OneToOne
    public Away getAway() {
        return away;
    }

    public void setAway(Away away) {
        this.away = away;
    }

    @ManyToOne
    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}