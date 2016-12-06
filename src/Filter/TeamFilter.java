package Filter;

import Model.Competition;
import Model.Filter;
import Model.Standing;

import java.util.ArrayList;

public class TeamFilter extends Filter {
    private Competition competition;

    public TeamFilter(Competition competition) {
        super();
        this.competition = competition;
    }

    public ArrayList<Integer> position() {
        name = "Position";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.position);
        }
        return values;
    }

    public ArrayList<Integer> points() {
        name = "Points";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.points);
        }
        return values;
    }

    public ArrayList<Integer> goals() {
        name = "Goals";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goals);
        }
        return values;
    }

    public ArrayList<Integer> goalsAgainst() {
        name = "GoalsAgainst";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goalsAgainst);
        }
        return values;
    }

    public ArrayList<Integer> goalDifference() {
        name = "GoalDifference";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goalDifference);
        }
        return values;
    }

    public ArrayList<Integer> wins() {
        name = "Wins";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.wins);
        }
        return values;
    }

    public ArrayList<Integer> draws() {
        name = "Draws";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.draws);
        }
        return values;
    }

    public ArrayList<Integer> losses() {
        name = "Losses";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.losses);
        }
        return values;
    }
}