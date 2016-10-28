import java.util.ArrayList;

class Filter {
    private Competition competition;
    private ArrayList<Integer> values = new ArrayList<Integer>();
    String filterName;

    Filter(Competition competition) {
        this.competition = competition;
    }

    ArrayList<Integer> position() {
        filterName = "Position";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.position);
        }
        return values;
    }

    ArrayList<Integer> points() {
        filterName = "Points";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.points);
        }
        return values;
    }

    ArrayList<Integer> goals() {
        filterName = "Goals";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goals);
        }
        return values;
    }

    ArrayList<Integer> goalsAgainst() {
        filterName = "GoalsAgainst";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goalsAgainst);
        }
        return values;
    }

    ArrayList<Integer> goalDifference() {
        filterName = "GoalDifference";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.goalDifference);
        }
        return values;
    }

    ArrayList<Integer> wins() {
        filterName = "Wins";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.wins);
        }
        return values;
    }

    ArrayList<Integer> draws() {
        filterName = "Draws";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.draws);
        }
        return values;
    }

    ArrayList<Integer> losses() {
        filterName = "Losses";
        values.clear();
        for (Standing standing : competition.standings) {
            values.add(standing.losses);
        }
        return values;
    }
}