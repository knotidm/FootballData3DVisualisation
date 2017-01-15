package Util;

import Model.*;
import Object3D.Object3D;
import Stats.PlayerStats;
import Stats.TeamStats;
import com.sun.istack.internal.NotNull;
import http.requests.GetRequest;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Get {

    public static JSONObject getJSONObject(String link) {
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token", "324794156b594490a7c6244a6a10a034");
        getRequest.send();
        return JSONObject.parse(getRequest.getContent());
    }

    @NotNull
    public static Team getTeam(Competition competition, int index) {
        Team resultTeam = new Team();

        ArrayList<Standing> standings = new ArrayList<>(competition.standings);
        for (Team team : competition.teams) {

            if (team.name.equals(standings.get(index).teamName))
                resultTeam = team;
        }
        return resultTeam;
    }

    @NotNull
    public static BigDecimal getBigDecimal(String integerString) {
        if (!integerString.equals("")) {
            return new BigDecimal(integerString);
        }
        return new BigDecimal(0);
    }

    public static Date getDate(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
        if (!dateString.equals(""))
            try {
                return format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return null;
    }

    public static Date getDetailedDate(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.UK);
        if (!dateString.equals(""))
            try {
                return format.parse(dateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        return null;
    }

    public static String getString(BigDecimal bigDecimal) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        return numberFormat.format(bigDecimal);
    }

    public static ArrayList<Object3D<Team>> getTeamObjects3D(PApplet pApplet, Competition competition, TeamStats teamStats, Integer gridSize) {
        ArrayList<Object3D<Team>> teamObjects3D = new ArrayList<>();
        for (Integer i = 0; i < competition.standings.size(); i++) {
            teamObjects3D.add(new Object3D<Team>(pApplet,
                    new PVector(pApplet.random(gridSize), pApplet.random(gridSize), pApplet.random(gridSize / 4)),
                    i,
                    Get.getTeam(competition, i),
                    new ArrayList<>(teamStats.values).get(i)
            ));
        }
        return teamObjects3D;
    }

    public static ArrayList<Object3D<Player>> getPlayerObjects3D(PApplet pApplet, Team team, PlayerStats playerStats, Integer gridSize) {
        ArrayList<Object3D<Player>> playerObjects3D = new ArrayList<>();
        for (Integer i = 0; i < team.players.size(); i++) {
            playerObjects3D.add(new Object3D<Player>(pApplet,
                    new PVector(pApplet.random(gridSize), pApplet.random(gridSize), pApplet.random(gridSize / 4)),
                    i,
                    new ArrayList<>(team.players).get(i),
                    new ArrayList<>(playerStats.values).get(i)
            ));
        }
        return playerObjects3D;
    }

    public static ArrayList<Object3D<Fixture>> getFixtureObjects3D(PApplet pApplet, Team team, Integer gridSize) {
        ArrayList<Object3D<Fixture>> fixtureObjects3D = new ArrayList<>();
        for (Integer i = 0; i < team.fixtures.size(); i++) {
            fixtureObjects3D.add(new Object3D<Fixture>(pApplet,
                    new PVector(pApplet.random(gridSize), pApplet.random(gridSize), pApplet.random(gridSize / 4)),
                    i,
                    new ArrayList<>(team.fixtures).get(i),
                    i));
        }
        return fixtureObjects3D;
    }
}