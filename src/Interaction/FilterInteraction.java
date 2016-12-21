package Interaction;

import Filter.PlayerFilter;
import Filter.TeamFilter;
import Model.Competition;
import Model.Team;
import Object3D.Object3D;

import java.util.ArrayList;
import java.util.Collection;

public class FilterInteraction<T> {

    public ArrayList<Object3D<T>> switchTeamFilter(Competition competition, ArrayList<Object3D<T>> objects3D, TeamFilter filter, Integer indexFilter) {
        switch (indexFilter) {
            case 0:
                return setTeamFilter(competition, objects3D, filter.playedGames());
            case 1:
                return setTeamFilter(competition, objects3D, filter.points());
            case 2:
                return setTeamFilter(competition, objects3D, filter.goals());
            case 3:
                return setTeamFilter(competition, objects3D, filter.goalsAgainst());
            case 4:
                return setTeamFilter(competition, objects3D, filter.goalDifference());
            case 5:
                return setTeamFilter(competition, objects3D, filter.wins());
            case 6:
                return setTeamFilter(competition, objects3D, filter.draws());
            case 7:
                return setTeamFilter(competition, objects3D, filter.losses());
            case 8:
                return setTeamFilter(competition, objects3D, filter.squadMarketValue());
        }
        return null;
    }

    public ArrayList<Object3D<T>> switchPlayerFilter(Team team, ArrayList<Object3D<T>> objects3D, PlayerFilter filter, Integer indexFilter) {
        switch (indexFilter) {
            case 0:
                return setPlayerFilter(team, objects3D, filter.jerseyNumber());
            case 1:
                return setPlayerFilter(team, objects3D, filter.marketValue());
            case 2:
                return setPlayerFilter(team, objects3D, filter.age());
            case 3:
                return setPlayerFilter(team, objects3D, filter.yearsToEndContract());
        }
        return null;
    }

    private ArrayList<Object3D<T>> setTeamFilter(Competition competition, ArrayList<Object3D<T>> objects3D, Collection<Integer> filteredValues) {
        for (Integer i = 0; i < competition.getStandings().size(); i++) {
            objects3D.get(i).filterValue = new ArrayList<>(filteredValues).get(i);
        }
        return objects3D;
    }

    private ArrayList<Object3D<T>> setPlayerFilter(Team team, ArrayList<Object3D<T>> objects3D, Collection<Integer> filteredValues) {
        for (Integer i = 0; i < team.getPlayers().size(); i++) {
            objects3D.get(i).filterValue = new ArrayList<>(filteredValues).get(i);
        }
        return objects3D;
    }
}