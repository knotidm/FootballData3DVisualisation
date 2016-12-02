package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONArray;
import processing.data.JSONObject;
import Util.Util;

import java.util.ArrayList;

public class Competition {
    @NotNull
    public ArrayList<Team> teams;
    @NotNull
    public ArrayList<Standing> standings;
    @NotNull
    public String name;
    @NotNull
    public String year;
    @NotNull
    public Integer currentMatchday;
    @NotNull
    public Integer numberOfMatchdays;
    @NotNull
    public Integer numberOfGames;

    public Competition(JSONObject competition) {
        teams = getTeams(competition.getJSONObject("_links").getJSONObject("teams").getString("href"));
        standings = getStandings(competition.getJSONObject("_links").getJSONObject("leagueTable").getString("href"));
        name = competition.getString("caption");
        year = competition.getString("year");
        currentMatchday = competition.getInt("currentMatchday");
        numberOfMatchdays = competition.getInt("numberOfMatchdays");
        numberOfGames = competition.getInt("numberOfGames");
    }

    private ArrayList<Team> getTeams(String link) {
        JSONArray teamsJSON = Util.getRequestToJSONObject(link).getJSONArray("teams");
        teams = new ArrayList<>();

        for (int i = 0; i < teamsJSON.size(); i++) {
            teams.add(new Team(teamsJSON.getJSONObject(i)));
        }
        return teams;
    }

    private ArrayList<Standing> getStandings(String link) {
        JSONArray standingsJSON = Util.getRequestToJSONObject(link).getJSONArray("standing");
        standings = new ArrayList<>();

        for (int i = 0; i < standingsJSON.size(); i++) {
            standings.add(new Standing(standingsJSON.getJSONObject(i)));
        }
        return standings;
    }
}