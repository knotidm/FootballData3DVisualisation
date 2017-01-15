package Interaction;

import Stats.PlayerStats;
import Stats.TeamStats;
import Model.Competition;
import Model.Team;
import Object3D.Object3D;

import java.util.ArrayList;
import java.util.Collection;

public class StatsInteraction<T> {

    private ArrayList<Object3D<T>> setTeamStats(Competition competition, ArrayList<Object3D<T>> objects3D, Collection<Integer> integerCollection) {
        for (Integer i = 0; i < competition.standings.size(); i++) {
            objects3D.get(i).filterValue = new ArrayList<>(integerCollection).get(i);
        }
        return objects3D;
    }

    private ArrayList<Object3D<T>> setPlayerStats(Team team, ArrayList<Object3D<T>> objects3D, Collection<Integer> integerCollection) {
        for (Integer i = 0; i < team.players.size(); i++) {
            objects3D.get(i).filterValue = new ArrayList<>(integerCollection).get(i);
        }
        return objects3D;
    }

    public ArrayList<Object3D<T>> switchTeamStats(ArrayList<Object3D<T>> objects3D, TeamStats teamStats, Integer teamStatsIndex) {
        switch (teamStatsIndex) {
            case 0:
                return setTeamStats(teamStats.competition, objects3D, teamStats.playedGames());
            case 1:
                return setTeamStats(teamStats.competition, objects3D, teamStats.points());
            case 2:
                return setTeamStats(teamStats.competition, objects3D, teamStats.goals());
            case 3:
                return setTeamStats(teamStats.competition, objects3D, teamStats.goalsAgainst());
            case 4:
                return setTeamStats(teamStats.competition, objects3D, teamStats.goalDifference());
            case 5:
                return setTeamStats(teamStats.competition, objects3D, teamStats.wins());
            case 6:
                return setTeamStats(teamStats.competition, objects3D, teamStats.draws());
            case 7:
                return setTeamStats(teamStats.competition, objects3D, teamStats.losses());
            case 8:
                return setTeamStats(teamStats.competition, objects3D, teamStats.squadMarketValue());
            case 9:
                return setTeamStats(teamStats.competition, objects3D, teamStats.averageAge());
        }
        return null;
    }

    public ArrayList<Object3D<T>> switchPlayerStats(ArrayList<Object3D<T>> objects3D, PlayerStats playerStats, Integer playerStatsIndex) {
        switch (playerStatsIndex) {
            case 0:
                return setPlayerStats(playerStats.team, objects3D, playerStats.jerseyNumber());
            case 1:
                return setPlayerStats(playerStats.team, objects3D, playerStats.marketValue());
            case 2:
                return setPlayerStats(playerStats.team, objects3D, playerStats.age());
            case 3:
                return setPlayerStats(playerStats.team, objects3D, playerStats.yearsToEndContract());
        }
        return null;
    }
}