package Model;

import Util.Get;
import com.sun.istack.internal.NotNull;
import processing.data.JSONArray;
import processing.data.JSONObject;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Team {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer teamId;
    @NotNull
    @OneToMany(mappedBy = "team")
    private Collection<Fixture> fixtures;
    @NotNull
    @OneToMany(mappedBy = "team")
    private Collection<Player> players;
    @NotNull
    private String name;
    @NotNull
    private BigDecimal squadMarketValue;
    //    @NotNull
//    @Basic
//    public PShape emblem;
    @ManyToOne
    private Competition competition;

    public Team() {
    }

    public Team(JSONObject team) {
        fixtures = getFixtures(team.getJSONObject("_links").getJSONObject("fixtures").getString("href"));
        players = getPlayers(team.getJSONObject("_links").getJSONObject("players").getString("href"));
        name = team.getString("name");
        squadMarketValue = Get.getBigDecimal(team.getString("squadMarketValue", "").replaceAll("[^\\d]+", ""));
        //emblem = loadShape(teamContent.getString("crestUrl"));
        //emblem = loadShape("https://upload.wikimedia.org/wikipedia/commons/c/c5/Logo_FC_Bayern_M%C3%BCnchen.svg");
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    @OneToMany
    public Collection<Fixture> getFixtures() {
        return fixtures;
    }

    public void setFixtures(Collection<Fixture> fixtures) {
        this.fixtures = fixtures;
    }

    @OneToMany
    public Collection<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Collection<Player> players) {
        this.players = players;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public BigDecimal getSquadMarketValue() {
        return squadMarketValue;
    }

    public void setSquadMarketValue(BigDecimal squadMarketValue) {
        this.squadMarketValue = squadMarketValue;
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

    @ManyToOne
    public Competition getCompetition() {
        return competition;
    }

    public void setCompetition(Competition competition) {
        this.competition = competition;
    }
}