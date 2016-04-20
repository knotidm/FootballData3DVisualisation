import java.util.ArrayList;

class Filter {
    private SoccerSeason soccerSeason;
    private ArrayList<Integer> values = new ArrayList<Integer>();
    String filterName;

    Filter(SoccerSeason soccerSeason) {
        this.soccerSeason = soccerSeason;
    }

    ArrayList<Integer> Position() {
        filterName = "Position";
        values.clear();
        for (Standing standing : soccerSeason.leagueTable.standings) {
            values.add(standing.position);
        }
        return values;
    }

    ArrayList<Integer> Points() {
        filterName = "Points";
        values.clear();
        for (Standing standing : soccerSeason.leagueTable.standings) {
            values.add(standing.points);
        }
        return values;
    }

    ArrayList<Integer> Goals() {
        filterName = "Goals";
        values.clear();
        for (Standing standing : soccerSeason.leagueTable.standings) {
            values.add(standing.goals);
        }
        return values;
    }

    ArrayList<Integer> GoalsAgainst() {
        filterName = "GoalsAgainst";
        values.clear();
        for (Standing standing : soccerSeason.leagueTable.standings) {
            values.add(standing.goalsAgainst);
        }
        return values;
    }

    ArrayList<Integer> GoalDifference() {
        filterName = "GoalDifference";
        values.clear();
        for (Standing standing : soccerSeason.leagueTable.standings) {
            values.add(standing.goalDifference);
        }
        return values;
    }

    ArrayList<Integer> Wins() {
        filterName = "Wins";
        values.clear();
        for (Standing standing : soccerSeason.leagueTable.standings) {
            values.add(standing.wins);
        }
        return values;
    }

    ArrayList<Integer> Draws() {
        filterName = "Draws";
        values.clear();
        for (Standing standing : soccerSeason.leagueTable.standings) {
            values.add(standing.draws);
        }
        return values;
    }

    ArrayList<Integer> Losses() {
        filterName = "Losses";
        values.clear();
        for (Standing standing : soccerSeason.leagueTable.standings) {
            values.add(standing.losses);
        }
        return values;
    }
}