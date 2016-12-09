package Model;

import Util.Util;
import com.sun.istack.internal.NotNull;
import processing.data.JSONArray;
import processing.data.JSONObject;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Competition {
    @Id
    private Integer competitionId;
    @NotNull
    @OneToMany(mappedBy = "competition")
    private Collection<Team> teams;
    @NotNull
    @OneToMany(mappedBy = "competition")
    private Collection<Standing> standings;
    @NotNull
    private String name;
    @NotNull
    private String year;
    @NotNull
    private Integer currentMatchday;
    @NotNull
    private Integer numberOfMatchdays;
    @NotNull
    private Integer numberOfGames;

    public Competition() {
    }

    public Competition(JSONObject competition) {
        teams = getTeams(competition.getJSONObject("_links").getJSONObject("teams").getString("href"));
        standings = getStandings(competition.getJSONObject("_links").getJSONObject("leagueTable").getString("href"));
        name = competition.getString("caption");
        year = competition.getString("year");
        currentMatchday = competition.getInt("currentMatchday");
        numberOfMatchdays = competition.getInt("numberOfMatchdays");
        numberOfGames = competition.getInt("numberOfGames");
    }

    @Id
    public Integer getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Integer competitionId) {
        this.competitionId = competitionId;
    }

    @OneToMany
    public Collection<Team> getTeams() {
        return teams;
    }

    public void setTeams(Collection<Team> teams) {
        this.teams = teams;
    }

    @OneToMany
    public Collection<Standing> getStandings() {
        return standings;
    }

    public void setStandings(Collection<Standing> standings) {
        this.standings = standings;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Basic
    public Integer getCurrentMatchday() {
        return currentMatchday;
    }

    public void setCurrentMatchday(Integer currentMatchday) {
        this.currentMatchday = currentMatchday;
    }

    @Basic
    public Integer getNumberOfMatchdays() {
        return numberOfMatchdays;
    }

    public void setNumberOfMatchdays(Integer numberOfMatchdays) {
        this.numberOfMatchdays = numberOfMatchdays;
    }

    @Basic
    public Integer getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(Integer numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    private Collection<Team> getTeams(String link) {
        JSONArray teamsJSON = Util.getRequestToJSONObject(link).getJSONArray("teams");
        teams = new ArrayList<>();

        for (int i = 0; i < teamsJSON.size(); i++) {
            teams.add(new Team(teamsJSON.getJSONObject(i)));
        }
        return teams;
    }

    private Collection<Standing> getStandings(String link) {
        JSONArray standingsJSON = Util.getRequestToJSONObject(link).getJSONArray("standing");
        standings = new ArrayList<>();

        for (int i = 0; i < standingsJSON.size(); i++) {
            standings.add(new Standing(standingsJSON.getJSONObject(i)));
        }
        return standings;
    }
}