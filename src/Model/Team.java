package Model;
import Util.Util;
import com.sun.istack.internal.NotNull;
import processing.core.PShape;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Team {
    @NotNull
    ArrayList<Fixture> fixtures;
    @NotNull
    ArrayList<Player> players;
    @NotNull
    public String name;
    @NotNull
    public BigDecimal squadMarketValue;
    @NotNull
    PShape emblem;

    public Team(JSONObject team) {
        fixtures = getFixtures(team.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
        players = getPlayers(team.getJSONObject("_links").getJSONObject("players").getString("href"));
        name = team.getString("name");
        squadMarketValue = Util.getBigDecimal(team.getString("squadMarketValue", "").replaceAll("[^\\d]+", ""));
        //emblem = loadShape(teamContent.getString("crestUrl"));
        //emblem = loadShape("https://upload.wikimedia.org/wikipedia/commons/c/c5/Logo_FC_Bayern_M%C3%BCnchen.svg");
    }

    private ArrayList<Fixture> getFixtures(String link) {
        JSONArray fixturesJSON = Util.getRequestToJSONObject(link).getJSONArray("fixtures");
        fixtures = new ArrayList<Fixture>();

        for (int i = 0; i < fixturesJSON.size(); i++) {
            fixtures.add(new Fixture(fixturesJSON.getJSONObject(i)));
        }
        return fixtures;
    }

    private ArrayList<Player> getPlayers(String link) {
        JSONArray playersJSON = Util.getRequestToJSONObject(link).getJSONArray("players");
        players = new ArrayList<Player>();

        for (int i = 0; i < playersJSON.size(); i++) {
            players.add(new Player(playersJSON.getJSONObject(i)));
        }
        return players;
    }
}