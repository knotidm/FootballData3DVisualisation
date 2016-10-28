import com.sun.istack.internal.NotNull;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

class Competition {
    @NotNull
    ArrayList<Team> teams;
    @NotNull
    ArrayList<Standing> standings;
    @NotNull
    String name;
    @NotNull
    String year;
    @NotNull
    Integer currentMatchday;
    @NotNull
    Integer numberOfMatchdays;
    @NotNull
    Integer numberOfGames;

    Competition(JSONObject competition) {
        teams = getTeams(competition.getJSONObject("_links").getJSONObject("teams").getString("href"));
        standings = getStandings(competition.getJSONObject("_links").getJSONObject("leagueTable").getString("href"));
        name = competition.getString("caption");
        year = competition.getString("year");
        currentMatchday = competition.getInt("currentMatchday");
        numberOfMatchdays = competition.getInt("numberOfMatchdays");
        numberOfGames = competition.getInt("numberOfGames");
    }

    private ArrayList<Team> getTeams(String link) {
        JSONArray teamsJSON = Main.getRequestToJSONObject(link).getJSONArray("teams");
        teams = new ArrayList<Team>();

        for (int i = 0; i < teamsJSON.size(); i++) {
            teams.add(new Team(teamsJSON.getJSONObject(i)));
        }
        return teams;
    }

    private ArrayList<Standing> getStandings(String link) {
        JSONArray standingsJSON = Main.getRequestToJSONObject(link).getJSONArray("standing");
        standings = new ArrayList<Standing>();

        for (int i = 0; i < standingsJSON.size(); i++) {
            standings.add(new Standing(standingsJSON.getJSONObject(i)));
        }
        return standings;
    }
}
