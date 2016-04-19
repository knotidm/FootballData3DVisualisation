import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;

class LeagueTable {
    ArrayList<Standing> standings;

    LeagueTable(JSONObject leagueTable){
        standings = new ArrayList<Standing>();
        JSONArray leagueTableJSON = leagueTable.getJSONArray("standing");

        for (int i = 0; i < leagueTableJSON.size(); i++ )
        {
            standings.add(new Standing(leagueTableJSON.getJSONObject(i)));
        }
    }
}

class Standing {
    Integer position;
    String teamName;
    Integer playedGames;
    Integer points;
    Integer goals;
    Integer goalsAgainst;
    Integer goalDifference;
    Integer wins;
    Integer draws;
    Integer losses;
    Home home;
    Away away;

    Standing(JSONObject standing){
        position = standing.getInt("position");
        teamName = standing.getString("teamName");
        playedGames = standing.getInt("playedGames");
        points = standing.getInt("points");
        goals = standing.getInt("goals");
        goalsAgainst = standing.getInt("goalsAgainst");
        goalDifference = standing.getInt("goalDifference");
        wins = standing.getInt("wins");
        draws = standing.getInt("draws");
        losses = standing.getInt("losses");
        home = new Home(standing.getJSONObject("home"));
        away = new Away(standing.getJSONObject("away"));
    }
}

class Home {
    Integer goals;
    Integer goalsAgainst;
    Integer wins;
    Integer draws;
    Integer losses;

    Home(JSONObject home){
        goals = home.getInt("goals");
        goalsAgainst = home.getInt("goalsAgainst");
        wins = home.getInt("wins");
        draws = home.getInt("draws");
        losses = home.getInt("losses");
    }
}

class Away {
    Integer goals;
    Integer goalsAgainst;
    Integer wins;
    Integer draws;
    Integer losses;

    Away(JSONObject away){
        goals = away.getInt("goals");
        goalsAgainst = away.getInt("goalsAgainst");
        wins = away.getInt("wins");
        draws = away.getInt("draws");
        losses = away.getInt("losses");
    }
}