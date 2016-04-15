import http.requests.GetRequest;
import processing.core.PShape;
import processing.data.JSONArray;
import processing.data.JSONObject;

public class Team extends Main {
    JSONObject fixtures;
    JSONObject players;
    String name;
    String squadMarketValue;
    PShape emblem;

    public Team(String link){
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        getRequest.send();

        JSONObject teamContent = JSONObject.parse(getRequest.getContent());
        fixtures = JSONObject.parse(teamContent.getJSONObject("_links").getJSONObject("fixtures").getString("href"));

        //players = teamContent.getJSONArray("players");
        name = teamContent.getString("name");
        squadMarketValue = teamContent.getString("squadMarketValue");
        //emblem = loadShape(teamContent.getString("crestUrl"));
        //emblem = loadShape("https://upload.wikimedia.org/wikipedia/commons/c/c5/Logo_FC_Bayern_M%C3%BCnchen.svg");
    }
}
