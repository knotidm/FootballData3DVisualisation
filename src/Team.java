import http.requests.GetRequest;
import processing.core.PShape;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

public class Team extends Main {
    //JSONObject fixtures;
    ArrayList<Player> players;
    JSONArray playersJSON;
    String playerslink;
    String name;
    String squadMarketValue;
    PShape emblem;

    public Team(String link){
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        getRequest.send();

        JSONObject teamContent = JSONObject.parse(getRequest.getContent());
        //fixtures = JSONObject.parse(teamContent.getJSONObject("_links").getJSONObject("fixtures").getString("href"));

        playerslink = teamContent.getJSONObject("_links").getJSONObject("players").getString("href");
        GetPlayers(playerslink);
        name = teamContent.getString("name");
        squadMarketValue = teamContent.getString("squadMarketValue");
        //emblem = loadShape(teamContent.getString("crestUrl"));
        //emblem = loadShape("https://upload.wikimedia.org/wikipedia/commons/c/c5/Logo_FC_Bayern_M%C3%BCnchen.svg");
    }


    public void GetPlayers(String link){
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        getRequest.send();

        JSONObject playersContent = JSONObject.parse(getRequest.getContent());

        playersJSON = playersContent.getJSONArray("players");

        players = new ArrayList<Player>();

        for (int i = 0; i < playersJSON.size(); i++ )
        {
            players.add(new Player(playersJSON.getJSONObject(i)));
        }


    }
}
