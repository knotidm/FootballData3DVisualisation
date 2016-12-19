package Filter;

import Model.Competition;
import Model.Filter;
import Model.Standing;
import Model.Team;
import UI.Event;
import UI.UserInterface;

import java.util.Collection;

public class TeamFilter extends Filter {
    private Competition competition;

    public TeamFilter(Competition competition) {
        super();
        this.competition = competition;
    }

    public Collection<Integer> playedGames(UserInterface userInterface) {
        setName("Played Games");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            switch ((int) Event.teamModeIndex) {
                case 0:
                    getValues().add(standing.getWins() + standing.getDraws() + standing.getLosses());
                    break;
                case 1:
                    getValues().add(standing.getHome().getWins() + standing.getHome().getDraws() + standing.getHome().getLosses());
                    break;
                case 2:
                    getValues().add(standing.getAway().getWins() + standing.getAway().getDraws() + standing.getAway().getLosses());
                    break;
            }
        }
        return getValues();
    }

    public Collection<Integer> points(UserInterface userInterface) {
        setName("Points");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            switch ((int) Event.teamModeIndex) {
                case 0:
                    getValues().add(3 * standing.getWins() + standing.getDraws());
                    break;
                case 1:
                    getValues().add(3 * standing.getHome().getWins() + standing.getHome().getDraws());
                    break;
                case 2:
                    getValues().add(3 * standing.getAway().getWins() + standing.getAway().getDraws());
                    break;
            }
        }
        return getValues();
    }

    public Collection<Integer> goals(UserInterface userInterface) {
        setName("Goals");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            switch ((int) Event.teamModeIndex) {
                case 0:
                    getValues().add(standing.getGoals());
                    break;
                case 1:
                    getValues().add(standing.getHome().getGoals());
                    break;
                case 2:
                    getValues().add(standing.getAway().getGoals());
                    break;
            }
        }
        return getValues();
    }

    public Collection<Integer> goalsAgainst(UserInterface userInterface) {
        setName("GoalsAgainst");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            switch ((int) Event.teamModeIndex) {
                case 0:
                    getValues().add(standing.getGoalsAgainst());
                    break;
                case 1:
                    getValues().add(standing.getHome().getGoalsAgainst());
                    break;
                case 2:
                    getValues().add(standing.getAway().getGoalsAgainst());
                    break;
            }
        }
        return getValues();
    }

    public Collection<Integer> goalDifference(UserInterface userInterface) {
        setName("GoalDifference");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            switch ((int) Event.teamModeIndex) {
                case 0:
                    getValues().add(standing.getGoals() - standing.getGoalsAgainst());
                    break;
                case 1:
                    getValues().add(standing.getHome().getGoals() - standing.getHome().getGoalsAgainst());
                    break;
                case 2:
                    getValues().add(standing.getAway().getGoals() - standing.getAway().getGoalsAgainst());
                    break;
            }
        }
        return getValues();
    }

    public Collection<Integer> wins(UserInterface userInterface) {
        setName("Wins");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            switch ((int) Event.teamModeIndex) {
                case 0:
                    getValues().add(standing.getWins());
                    break;
                case 1:
                    getValues().add(standing.getHome().getWins());
                    break;
                case 2:
                    getValues().add(standing.getAway().getWins());
                    break;
            }
        }
        return getValues();
    }

    public Collection<Integer> draws(UserInterface userInterface) {
        setName("Draws");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            switch ((int) Event.teamModeIndex) {
                case 0:
                    getValues().add(standing.getDraws());
                    break;
                case 1:
                    getValues().add(standing.getHome().getDraws());
                    break;
                case 2:
                    getValues().add(standing.getAway().getDraws());
                    break;
            }
        }
        return getValues();
    }

    public Collection<Integer> losses(UserInterface userInterface) {
        setName("Losses");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            switch ((int) Event.teamModeIndex) {
                case 0:
                    getValues().add(standing.getLosses());
                    break;
                case 1:
                    getValues().add(standing.getHome().getLosses());
                    break;
                case 2:
                    getValues().add(standing.getAway().getLosses());
                    break;
            }
        }
        return getValues();
    }

    public Collection<Integer> squadMarketValue() {
        setName("Squad Market Value");
        getValues().clear();
        for (Team team : competition.getTeams()) {
            getValues().add(team.getSquadMarketValue().intValue());
        }
        return getValues();
    }
}