package Model;

import java.util.ArrayList;

public class Filter {
    private Competition competition;
    private ArrayList<Integer> values;
    public String filterName;

    public Filter(Competition competition) {
        this.competition = competition;
        values = new ArrayList<>();
    }

    public ArrayList<Integer> position() {
        filterName = "Position";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.position);
        }
        return values;
    }

    public ArrayList<Integer> points() {
        filterName = "Points";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.points);
        }
        return values;
    }

    public ArrayList<Integer> goals() {
        filterName = "Goals";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goals);
        }
        return values;
    }

    public ArrayList<Integer> goalsAgainst() {
        filterName = "GoalsAgainst";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goalsAgainst);
        }
        return values;
    }

    public ArrayList<Integer> goalDifference() {
        filterName = "GoalDifference";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goalDifference);
        }
        return values;
    }

    public ArrayList<Integer> wins() {
        filterName = "Wins";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.wins);
        }
        return values;
    }

    public ArrayList<Integer> draws() {
        filterName = "Draws";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.draws);
        }
        return values;
    }

    public ArrayList<Integer> losses() {
        filterName = "Losses";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.losses);
        }
        return values;
    }
}