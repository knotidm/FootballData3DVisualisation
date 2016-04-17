import http.requests.GetRequest;
import processing.core.PShape;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

class Team {

    ArrayList<Fixture> fixtures;
    ArrayList<Player> players;

    String name;
    String squadMarketValue;
    PShape emblem;

    Team(JSONObject team){

        GetFixtures(team.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
        GetPlayers(team.getJSONObject("_links").getJSONObject("players").getString("href"));

        name = team.getString("name");
        if (!team.isNull("squadMarketValue")) squadMarketValue = team.getString("squadMarketValue");
        //emblem = loadShape(teamContent.getString("crestUrl"));
        //emblem = loadShape("https://upload.wikimedia.org/wikipedia/commons/c/c5/Logo_FC_Bayern_M%C3%BCnchen.svg");
    }

    private void GetFixtures(String link){
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

    private void GetPlayers(String link){
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        getRequest.send();

        JSONObject playersContent = JSONObject.parse(getRequest.getContent());
        JSONArray playersJSON = playersContent.getJSONArray("players");

        players = new ArrayList<Player>();

        for (int i = 0; i < playersJSON.size(); i++ )
        {
            players.add(new Player(playersJSON.getJSONObject(i)));
        }
    }
}
