package Model;

import Util.Get;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class Team {
    public Collection<Fixture> fixtures;
    public Collection<Player> players;
    public String name;
    public BigDecimal squadMarketValue;

    public Team(JSONObject team) {
        fixtures = getFixtures(team.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
        players = getPlayers(team.getJSONObject("_links").getJSONObject("players").getString("href"));
        name = team.getString("name");
        squadMarketValue = Get.getBigDecimal(team.getString("squadMarketValue", "").replaceAll("[^\\d]+", ""));
    }

    public Team() {

    }

    private Collection<Fixture> getFixtures(String link) {
        JSONArray fixturesJSON = Get.getJSONObject(link).getJSONArray("fixtures");
        fixtures = new ArrayList<>();

        for (int i = 0; i < fixturesJSON.size(); i++) {
            fixtures.add(new Fixture(fixturesJSON.getJSONObject(i)));
        }
        return fixtures;
    }

    private Collection<Player> getPlayers(String link) {
        JSONArray playersJSON = Get.getJSONObject(link).getJSONArray("players");
        players = new ArrayList<>();

        for (int i = 0; i < playersJSON.size(); i++) {
            players.add(new Player(playersJSON.getJSONObject(i)));
        }
        return players;
    }
}