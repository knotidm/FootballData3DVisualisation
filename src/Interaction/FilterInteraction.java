package Interaction;

import Filter.PlayerFilter;
import Filter.TeamFilter;
import Model.Competition;
import Model.Team;
import Object3D.Object3D;
import UI.UserInterface;

import java.util.ArrayList;
import java.util.Collection;

public class FilterInteraction<T> {

    public ArrayList<Object3D<T>> switchTeamFilter(UserInterface userInterface, Competition competition, ArrayList<Object3D<T>> objects3D, TeamFilter filter, Integer indexFilter) {
        switch (indexFilter) {
            case 1:
                return setTeamFilter(competition, objects3D, filter.playedGames(userInterface));
            case 2:
                return setTeamFilter(competition, objects3D, filter.points(userInterface));
            case 3:
                return setTeamFilter(competition, objects3D, filter.goals(userInterface));
            case 4:
                return setTeamFilter(competition, objects3D, filter.goalsAgainst(userInterface));
            case 5:
                return setTeamFilter(competition, objects3D, filter.goalDifference(userInterface));
            case 6:
                return setTeamFilter(competition, objects3D, filter.wins(userInterface));
            case 7:
                return setTeamFilter(competition, objects3D, filter.draws(userInterface));
            case 8:
                return setTeamFilter(competition, objects3D, filter.losses(userInterface));
            case 9:
                return setTeamFilter(competition, objects3D, filter.squadMarketValue());
        }
        return null;
    }

    public ArrayList<Object3D<T>> switchPlayerFilter(Team team, ArrayList<Object3D<T>> objects3D, PlayerFilter filter, Integer indexFilter) {
        switch (indexFilter) {
            case 1:
                return setPlayerFilter(team, objects3D, filter.jerseyNumber());
            case 2:
                return setPlayerFilter(team, objects3D, filter.marketValue());
            case 3:
                return setPlayerFilter(team, objects3D, filter.yearsOld());
        }
        return null;
    }

    private ArrayList<Object3D<T>> setTeamFilter(Competition competition, ArrayList<Object3D<T>> objects3D, Collection<Integer> filteredValues) {
        for (Integer i = 0; i < competition.getStandings().size(); i++) {
            objects3D.get(i).filterValue = (Integer) new ArrayList(filteredValues).get(i);
        }
        return objects3D;
    }

    private ArrayList<Object3D<T>> setPlayerFilter(Team team, ArrayList<Object3D<T>> objects3D, Collection<Integer> filteredValues) {
        for (Integer i = 0; i < team.getPlayers().size(); i++) {
            objects3D.get(i).filterValue = (Integer) new ArrayList(filteredValues).get(i);
        }
        return objects3D;
    }
}