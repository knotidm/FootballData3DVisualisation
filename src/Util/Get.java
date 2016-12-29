package Util;

import Model.*;
import Object3D.Object3D;
import Visualisation.Chart2D;
import com.sun.istack.internal.NotNull;
import hivis.common.HV;
import hivis.data.DataSeries;
import hivis.data.DataTable;
import http.requests.GetRequest;
import processing.core.PApplet;
import processing.data.JSONObject;
import toxi.geom.Vec3D;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

public class Get {

    public static JSONObject getJSONObject(String link) {
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token", "b95ca7f69f22429d9e82720ea977198e");
        getRequest.send();
        return JSONObject.parse(getRequest.getContent());
    }

    @NotNull
    public static Team getTeam(Competition competition, int index) {
        Team resultTeam = new Team();

        ArrayList<Standing> standings = new ArrayList(competition.getStandings());
        for (Team team : competition.getTeams()) {

            if (team.getName().equals(standings.get(index).getTeamName()))
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

    public static ArrayList<Object3D<Team>> getTeamObjects3D(PApplet pApplet, Competition competition, Collection<Integer> filteredValues, Integer gridSize) {
        ArrayList<Object3D<Team>> teamObjects3D = new ArrayList<>();
        for (Integer i = 0; i < competition.getStandings().size(); i++) {
            teamObjects3D.add(new Object3D<Team>(pApplet,
                    new Vec3D(pApplet.random(gridSize), pApplet.random(gridSize), pApplet.random(gridSize / 4)),
                    i,
                    Get.getTeam(competition, i),
                    new ArrayList<>(filteredValues).get(i)
            ));
        }
        return teamObjects3D;
    }

    public static ArrayList<Object3D<Player>> getPlayerObjects3D(PApplet pApplet, Team team, Collection<Integer> filteredValues, Integer gridSize) {
        ArrayList<Object3D<Player>> playerObjects3D = new ArrayList<>();
        for (Integer i = 0; i < team.getPlayers().size(); i++) {
            playerObjects3D.add(new Object3D<Player>(pApplet,
                    new Vec3D(pApplet.random(gridSize), pApplet.random(gridSize), pApplet.random(gridSize / 4)),
                    i,
                    new ArrayList<>(team.getPlayers()).get(i),
                    new ArrayList<>(filteredValues).get(i)
            ));
        }
        return playerObjects3D;
    }

    public static ArrayList<Object3D<Fixture>> getFixtureObjects3D(PApplet pApplet, Team team, Integer gridSize) {
        ArrayList<Object3D<Fixture>> fixtureObjects3D = new ArrayList<>();
        for (Integer i = 0; i < team.getFixtures().size(); i++) {
            fixtureObjects3D.add(new Object3D<Fixture>(pApplet,
                    new Vec3D(pApplet.random(gridSize), pApplet.random(gridSize), pApplet.random(gridSize / 4)),
                    i,
                    new ArrayList<>(team.getFixtures()).get(i),
                    i));
        }
        return fixtureObjects3D;
    }

    public static Chart2D getTeamChart2D(PApplet pApplet, ArrayList<Object3D<Team>> teamObjects3D, Filter teamFilter){
        DataSeries<String> teamNameDataSeries = HV.newSeries();
        DataSeries<Integer> teamFilterDataSeries = HV.newIntegerSeries();

        for (Object3D<Team> teamObject3D : teamObjects3D) {
            teamNameDataSeries.append(teamObject3D.type.getName());
            teamFilterDataSeries.append(teamObject3D.filterValue);
        }
        DataTable teamDataTable = HV.newTable()
                .addSeries("team name", teamNameDataSeries)
                .addSeries(teamFilter.getName(), teamFilterDataSeries);
        return new Chart2D(pApplet, teamDataTable, 1);
    }

    public static Chart2D getPlayerChart2D(PApplet pApplet, ArrayList<Object3D<Player>> playerObjects3D, Filter playerFilter){
        DataSeries<String> playerNameDataSeries = HV.newSeries();
        DataSeries<Integer> playerFilterDataSeries = HV.newIntegerSeries();

        for (Object3D<Player> playerObject3D : playerObjects3D) {
            playerNameDataSeries.append(playerObject3D.type.getName());
            playerFilterDataSeries.append(playerObject3D.filterValue);
        }
        DataTable playerDataTable = HV.newTable()
                .addSeries("team name", playerNameDataSeries)
                .addSeries(playerFilter.getName(), playerFilterDataSeries);
        return new Chart2D(pApplet, playerDataTable, 1);
    }
}