package Stats;

import Model.*;

import java.time.Year;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class TeamStats extends Stats {
    private Competition competition;

    public TeamStats(Competition competition, Integer modeIndex, Integer teamStatsIndex) {
        super();
        this.competition = competition;
        switchStats(teamStatsIndex, modeIndex);
    }

    public void switchStats(Integer teamStatsIndex, Integer modeIndex) {
        switch (teamStatsIndex) {
            case 0:
                values = playedGames(modeIndex);
                break;
            case 1:
                values = points(modeIndex);
                break;
            case 2:
                values = goals(modeIndex);
                break;
            case 3:
                values = goalsAgainst(modeIndex);
                break;
            case 4:
                values = goalDifference(modeIndex);
                break;
            case 5:
                values = wins(modeIndex);
                break;
            case 6:
                values = draws(modeIndex);
                break;
            case 7:
                values = losses(modeIndex);
                break;
            case 8:
                values = squadMarketValue(modeIndex);
                break;
            case 9:
                values = averageAge(modeIndex);
                break;
        }
    }

    private Collection<Integer> playedGames(Integer modeIndex) {
        name = "Played Games";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (modeIndex) {
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

    private Collection<Integer> points(Integer modeIndex) {
        name = "Points";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (modeIndex) {
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

    private Collection<Integer> goals(Integer modeIndex) {
        name = "Goals";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (modeIndex) {
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

    private Collection<Integer> goalsAgainst(Integer modeIndex) {
        name = "GoalsAgainst";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (modeIndex) {
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

    private Collection<Integer> goalDifference(Integer modeIndex) {
        name = "GoalDifference";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (modeIndex) {
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

    private Collection<Integer> wins(Integer modeIndex) {
        name = "Wins";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (modeIndex) {
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

    private Collection<Integer> draws(Integer modeIndex) {
        name = "Draws";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (modeIndex) {
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

    private Collection<Integer> losses(Integer modeIndex) {
        name = "Losses";
        values.clear();
        for (Standing standing : competition.standings) {
            switch (modeIndex) {
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

    private Collection<Integer> squadMarketValue(Integer modeIndex) {
        name = "Squad Market Value";
        values.clear();
        for (Team team : competition.teams) {
            values.add(team.squadMarketValue.intValue());
        }
        return values;
    }

    private Collection<Integer> averageAge(Integer modeIndex) {
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