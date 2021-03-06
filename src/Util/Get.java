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
    static Float x;
    static Float y;
    static Integer amount;
    static Integer distance;
    static Integer modifier;

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
        x = 0f;
        y = 0f;
        amount = competition.standings.size();
        distance = gridSize / amount;
        for (Integer i = 0; i < amount; i++) {
            if (i % 5 == 0) {
                x = 0f;
                y++;
            }
            teamObjects3D.add(new Object3D<Team>(pApplet,
                    new PVector(distance * 4 * x, distance * 4 * y, new ArrayList<>(teamStats.values).get(i)),
                    i,
                    Get.getTeam(competition, i),
                    new ArrayList<>(teamStats.values).get(i)
            ));
            x++;
        }
        return teamObjects3D;
    }

    public static ArrayList<Object3D<Fixture>> getFixtureObjects3D(PApplet pApplet, Team team, Integer gridSize) {
        ArrayList<Object3D<Fixture>> fixtureObjects3D = new ArrayList<>();
        x = 0f;
        y = 0f;
        amount = team.fixtures.size();
        distance = gridSize / amount;
        modifier = 3;
        for (Integer i = 0; i < amount; i++) {
            if (i % 7 == 0) {
                x = 0f;
                y++;
            }
            fixtureObjects3D.add(new Object3D<Fixture>(pApplet,
                    new PVector(distance * 6 * x, distance * 6 * y, i),
                    i,
                    new ArrayList<>(team.fixtures).get(i),
                    i
            ));
            x++;
        }
        return fixtureObjects3D;
    }

    public static ArrayList<Object3D<Player>> getPlayerObjects3D(PApplet pApplet, Team team, PlayerStats playerStats, Integer gridSize) {
        ArrayList<Object3D<Player>> playerObjects3D = new ArrayList<>();
        x = 0f;
        y = 0f;
        amount = team.players.size();
        distance = gridSize / amount;
        for (Integer i = 0; i < amount; i++) {
            if (i % 6 == 0) {
                x = 0f;
                y++;
            }
            playerObjects3D.add(new Object3D<Player>(pApplet,
                    new PVector(distance * 5 * x, distance * 5 * y, new ArrayList<>(playerStats.values).get(i)),
                    i,
                    new ArrayList<>(team.players).get(i),
                    new ArrayList<>(playerStats.values).get(i)
            ));
            x++;
        }
        return playerObjects3D;
    }
}