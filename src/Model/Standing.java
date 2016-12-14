package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

import javax.persistence.*;

@Entity
public class Standing {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer standingId;
    @NotNull
    private Integer position;
    @NotNull
    private String teamName;
    @NotNull
    private Integer playedGames;
    @NotNull
    private Integer points;
    @NotNull
    private Integer goals;
    @NotNull
    private Integer goalsAgainst;
    @NotNull
    private Integer goalDifference;
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
        position = standing.getInt("position");
        teamName = standing.getString("teamName");
        playedGames = standing.getInt("playedGames");
        points = standing.getInt("points");
        goals = standing.getInt("goals");
        goalsAgainst = standing.getInt("goalsAgainst");
        goalDifference = standing.getInt("goalDifference");
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
    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Basic
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Basic
    public Integer getPlayedGames() {
        return playedGames;
    }

    public void setPlayedGames(Integer playedGames) {
        this.playedGames = playedGames;
    }

    @Basic
    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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
    public Integer getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(Integer goalDifference) {
        this.goalDifference = goalDifference;
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