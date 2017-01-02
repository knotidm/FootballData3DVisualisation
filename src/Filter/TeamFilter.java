package Filter;

import Model.*;
import UI.Event;

import java.time.Year;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class TeamFilter extends Filter {
    private Competition competition;

    public TeamFilter(Competition competition) {
        super();
        this.competition = competition;
    }

    public Collection<Integer> playedGames() {
        name = "Played Games";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (Event.teamFilterModeIndex) {
                case 0:
                    values.add(standing.wins + standing.draws + standing.losses);
                    break;
                case 1:
                    values.add(standing.home.wins + standing.home.draws + standing.home.losses);
                    break;
                case 2:
                    values.add(standing.away.wins + standing.away.draws + standing.away.losses);
                    break;
            }
        }
        return values;
    }

    public Collection<Integer> points() {
        name = "Points";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (Event.teamFilterModeIndex) {
                case 0:
                    values.add(3 * standing.wins + standing.draws);
                    break;
                case 1:
                    values.add(3 * standing.home.wins + standing.home.draws);
                    break;
                case 2:
                    values.add(3 * standing.away.wins + standing.away.draws);
                    break;
            }
        }
        return values;
    }

    public Collection<Integer> goals() {
        name = "Goals";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (Event.teamFilterModeIndex) {
                case 0:
                    values.add(standing.goals);
                    break;
                case 1:
                    values.add(standing.home.goals);
                    break;
                case 2:
                    values.add(standing.away.goals);
                    break;
            }
        }
        return values;
    }

    public Collection<Integer> goalsAgainst() {
        name = "GoalsAgainst";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (Event.teamFilterModeIndex) {
                case 0:
                    values.add(standing.goalsAgainst);
                    break;
                case 1:
                    values.add(standing.home.goalsAgainst);
                    break;
                case 2:
                    values.add(standing.away.goalsAgainst);
                    break;
            }
        }
        return values;
    }

    public Collection<Integer> goalDifference() {
        name = "GoalDifference";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (Event.teamFilterModeIndex) {
                case 0:
                    values.add(standing.goals - standing.goalsAgainst);
                    break;
                case 1:
                    values.add(standing.home.goals - standing.home.goalsAgainst);
                    break;
                case 2:
                    values.add(standing.away.goals - standing.away.goalsAgainst);
                    break;
            }
        }
        return values;
    }

    public Collection<Integer> wins() {
        name = "Wins";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (Event.teamFilterModeIndex) {
                case 0:
                    values.add(standing.wins);
                    break;
                case 1:
                    values.add(standing.home.wins);
                    break;
                case 2:
                    values.add(standing.away.wins);
                    break;
            }
        }
        return values;
    }

    public Collection<Integer> draws() {
        name = "Draws";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (Event.teamFilterModeIndex) {
                case 0:
                    values.add(standing.draws);
                    break;
                case 1:
                    values.add(standing.home.draws);
                    break;
                case 2:
                    values.add(standing.away.draws);
                    break;
            }
        }
        return values;
    }

    public Collection<Integer> losses() {
        name = "Losses";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (Event.teamFilterModeIndex) {
                case 0:
                    values.add(standing.losses);
                    break;
                case 1:
                    values.add(standing.home.losses);
                    break;
                case 2:
                    values.add(standing.away.losses);
                    break;
            }
        }
        return values;
    }

    public Collection<Integer> squadMarketValue() {
        name = "Squad Market Value";
        values.clear();
        for (Team team : competition.teams) {
            values.add(team.squadMarketValue.intValue());
        }
        return values;
    }

    public Collection<Integer> averageAge() {
        name = "Average Age";
        values.clear();
        for (Team team : competition.teams) {
            Integer combinedAge = 0;
            for (Player player : team.players) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(player.dateOfBirth);
                combinedAge += (Year.now().getValue() - calendar.get(Calendar.YEAR));
            }
            values.add(combinedAge / team.players.size());
        }
        return values;
    }
}