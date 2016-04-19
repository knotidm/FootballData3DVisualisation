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
        JSONArray fixturesJSON = Main.GetRequestToJSONObject(link).getJSONArray("fixtures");
        fixtures = new ArrayList<Fixture>();

        for (int i = 0; i < fixturesJSON.size(); i++ )
        {
            fixtures.add(new Fixture(fixturesJSON.getJSONObject(i)));
        }
    }

    private void GetPlayers(String link){
        JSONArray playersJSON = Main.GetRequestToJSONObject(link).getJSONArray("players");
        players = new ArrayList<Player>();

        for (int i = 0; i < playersJSON.size(); i++ )
        {
            players.add(new Player(playersJSON.getJSONObject(i)));
        }
    }
}
