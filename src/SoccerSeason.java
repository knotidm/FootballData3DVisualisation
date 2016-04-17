import http.requests.GetRequest;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

public class SoccerSeason extends Main {

    ArrayList<Team> teams;
    ArrayList<Fixture> fixtures;
    LeagueTable leagueTable;

    Integer id;
    String leagueName;
    String year;
    Integer currentMachday;
    Integer numberOfMatchdays;
    Integer numberOfTeams;
    Integer numberOfGames;

    public SoccerSeason(JSONObject soccerSeason){

        GetTeams(soccerSeason.getJSONObject("_links").getJSONObject("teams").getString("href"));
        GetFixtures(soccerSeason.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
        GetLeagueTable(soccerSeason.getJSONObject("_links").getJSONObject("leagueTable").getString("href"));

        id = soccerSeason.getInt("id");
        leagueName = soccerSeason.getString("leagueName");
        year = soccerSeason.getString("year");
        currentMachday = soccerSeason.getInt("currentMachday");
        numberOfMatchdays = soccerSeason.getInt("numberOfMatchdays");
        numberOfTeams = soccerSeason.getInt("numberOfTeams");
        numberOfGames = soccerSeason.getInt("numberOfGames");
    }

    private void GetFixtures(String link) {
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        getRequest.send();

        JSONObject fixturesContent = JSONObject.parse(getRequest.getContent());
        JSONArray fixturesJSON = fixturesContent.getJSONArray("fixtures");

        fixtures = new ArrayList<Fixture>();

        for (int i = 0; i < fixturesJSON.size(); i++ )
        {
            fixtures.add(new Fixture(fixturesJSON.getJSONObject(i)));
        }
    }

    private void GetTeams(String link){
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        getRequest.send();

        JSONObject teamsContent = JSONObject.parse(getRequest.getContent());
        JSONArray teamsJSON = teamsContent.getJSONArray("teams");

        teams = new ArrayList<Team>();

        for (int i = 0; i < teamsJSON.size(); i++ )
        {
            teams.add(new Team(teamsJSON.getJSONObject(i)));
        }
    }

    private void GetLeagueTable(String link) {
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        getRequest.send();

        JSONObject leagueTableContent = JSONObject.parse(getRequest.getContent());
        leagueTable = new LeagueTable(leagueTableContent);
    }
}
