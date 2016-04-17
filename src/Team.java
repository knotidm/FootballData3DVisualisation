import http.requests.GetRequest;
import processing.core.PShape;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

public class Team {
    //JSONObject fixtures;
    ArrayList<Player> players;
    ArrayList<Fixture> fixtures;
    String name;
    String squadMarketValue;
    PShape emblem;

    public Team(String link){
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        getRequest.send();

        JSONObject teamContent = JSONObject.parse(getRequest.getContent());

        GetPlayers(teamContent.getJSONObject("_links").getJSONObject("players").getString("href"));
        GetFixtures(teamContent.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
        name = teamContent.getString("name");
        //squadMarketValue = teamContent.getString("squadMarketValue");
        //emblem = loadShape(teamContent.getString("crestUrl"));
        //emblem = loadShape("https://upload.wikimedia.org/wikipedia/commons/c/c5/Logo_FC_Bayern_M%C3%BCnchen.svg");
    }


    public void GetPlayers(String link){
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

    public void GetFixtures(String link){
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
}
