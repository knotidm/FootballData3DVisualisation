import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

class SoccerSeason {
    ArrayList<Team> teams;
    ArrayList<Fixture> fixtures;
    LeagueTable leagueTable;
    Integer id;
    String leagueName;
    String year;
    Integer currentMatchday;
    Integer numberOfMatchdays;
    Integer numberOfTeams;
    Integer numberOfGames;

    SoccerSeason(JSONObject soccerSeason){
        GetTeams(soccerSeason.getJSONObject("_links").getJSONObject("teams").getString("href"));
        GetFixtures(soccerSeason.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
        GetLeagueTable(soccerSeason.getJSONObject("_links").getJSONObject("leagueTable").getString("href"));
        id = soccerSeason.getInt("id");
        leagueName = soccerSeason.getString("caption");
        year = soccerSeason.getString("year");
        currentMatchday = soccerSeason.getInt("currentMatchday");
        numberOfMatchdays = soccerSeason.getInt("numberOfMatchdays");
        numberOfTeams = soccerSeason.getInt("numberOfTeams");
        numberOfGames = soccerSeason.getInt("numberOfGames");
    }

    private void GetFixtures(String link) {
        JSONArray fixturesJSON = Main.GetRequestToJSONObject(link).getJSONArray("fixtures");
        fixtures = new ArrayList<Fixture>();

        for (int i = 0; i < fixturesJSON.size(); i++ )
        {
            fixtures.add(new Fixture(fixturesJSON.getJSONObject(i)));
        }
    }

    private void GetTeams(String link){
        JSONArray teamsJSON = Main.GetRequestToJSONObject(link).getJSONArray("teams");
        teams = new ArrayList<Team>();

        for (int i = 0; i < teamsJSON.size(); i++ )
        {
            teams.add(new Team(teamsJSON.getJSONObject(i)));
        }
    }

    private void GetLeagueTable(String link) {
        leagueTable = new LeagueTable(Main.GetRequestToJSONObject(link));
    }
}
