package Filter;

import Model.Competition;
import Model.Filter;
import Model.Standing;
import Model.Team;

import java.util.Collection;

public class TeamFilter extends Filter {
    private Competition competition;

    public TeamFilter(Competition competition) {
        super();
        this.competition = competition;
    }

    public Collection<Integer> playedGames() {
        setName("Played Games");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            getValues().add(standing.getWins() + standing.getDraws() + standing.getLosses());
        }
        return getValues();
    }

    public Collection<Integer> points() {
        setName("Points");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            getValues().add(3 * standing.getWins() + standing.getDraws());
        }
        return getValues();
    }

    public Collection<Integer> goals() {
        setName("Goals");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            getValues().add(standing.getGoals());
        }
        return getValues();
    }

    public Collection<Integer> goalsAgainst() {
        setName("GoalsAgainst");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            getValues().add(standing.getGoalsAgainst());
        }
        return getValues();
    }

    public Collection<Integer> goalDifference() {
        setName("GoalDifference");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            getValues().add(standing.getGoals() - standing.getGoalsAgainst());
        }
        return getValues();
    }

    public Collection<Integer> wins() {
        setName("Wins");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            getValues().add(standing.getWins());
        }
        return getValues();
    }

    public Collection<Integer> draws() {
        setName("Draws");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            getValues().add(standing.getDraws());
        }
        return getValues();
    }

    public Collection<Integer> losses() {
        setName("Losses");
        getValues().clear();
        for (Standing standing : competition.getStandings()) {
            getValues().add(standing.getLosses());
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