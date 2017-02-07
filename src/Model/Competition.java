package Model;

import Util.Get;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class Competition {
    public Collection<Team> teams;
    public Collection<Standing> standings;
    public String name;
    public String year;
    public Integer currentMatchday;
    public Integer numberOfMatchdays;
    public Integer numberOfGames;

    public Competition() {
        teams = new ArrayList<>();
        standings = new ArrayList<>();
        name = "Custom";
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


    private Collection<Team> getTeams(String link) {
        JSONArray teamsJSON = Get.getJSONObject(link).getJSONArray("teams");
        teams = new ArrayList<>();

        for (int i = 0; i < teamsJSON.size(); i++) {
            teams.add(new Team(teamsJSON.getJSONObject(i)));
        }
        return teams;
    }

    private Collection<Standing> getStandings(String link) {
        JSONArray standingsJSON = Get.getJSONObject(link).getJSONArray("standing");
        standings = new ArrayList<>();

        for (int i = 0; i < standingsJSON.size(); i++) {
            standings.add(new Standing(standingsJSON.getJSONObject(i)));
        }
        return standings;
    }
}