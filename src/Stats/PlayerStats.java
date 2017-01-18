package Stats;

import Model.Stats;
import Model.Player;
import Model.Team;

import java.time.Year;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class PlayerStats extends Stats {
    public Team team;

    public PlayerStats(Team team, Integer playerStatsIndex) {
        super();
        this.team = team;
        super.name = "";
        switchStats(playerStatsIndex);
    }

    public void switchStats(Integer playerStatsIndex) {
        switch (playerStatsIndex) {
            case 0:
                values = jerseyNumber();
                break;
            case 1:
                values = marketValue();
                break;
            case 2:
                values = age();
                break;
            case 3:
                values = yearsToEndContract();
                break;
        }
    }

    private Collection<Integer> jerseyNumber() {
        name = "Jersey Number";
        values.clear();
        for (Player player : team.players) {
            values.add(player.jerseyNumber);
        }
        return values;
    }

    private Collection<Integer> marketValue() {
        name = "Market Value";
        values.clear();
        for (Player player : team.players) {
            values.add(player.marketValue.intValue());
        }
        return values;
    }

    private Collection<Integer> age() {
        name = "Age";
        values.clear();
        for (Player player : team.players) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(player.dateOfBirth);
            values.add(Year.now().getValue() - calendar.get(Calendar.YEAR));
        }
        return values;
    }

    private Collection<Integer> yearsToEndContract() {
        name = "Years To End Contract";
        values.clear();
        for (Player player : team.players) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(player.contractUntil);
            values.add(calendar.get(Calendar.YEAR) - Year.now().getValue());
        }
        return values;
    }
}