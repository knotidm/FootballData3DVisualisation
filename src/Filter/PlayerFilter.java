package Filter;

import Model.Filter;
import Model.Player;
import Model.Team;

import java.time.Year;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

public class PlayerFilter extends Filter {
    private Team team;

    public PlayerFilter(Team team) {
        super();
        this.team = team;
    }

    public Collection<Integer> jerseyNumber() {
        setName("Jersey Number");
        getValues().clear();
        for (Player player : team.getPlayers()) {
            getValues().add(player.getJerseyNumber());
        }
        return getValues();
    }

    public Collection<Integer> marketValue() {
        setName("Market Value");
        getValues().clear();
        for (Player player : team.getPlayers()) {
            getValues().add(player.getMarketValue().intValue());
        }
        return getValues();
    }

    public Collection<Integer> age() {
        setName("Age");
        getValues().clear();
        for (Player player : team.getPlayers()) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(player.getDateOfBirth());
            getValues().add(Year.now().getValue() - calendar.get(Calendar.YEAR));
        }
        return getValues();
    }

    public Collection<Integer> yearsToEndContract() {
        setName("Years To End Contract");
        getValues().clear();
        for (Player player : team.getPlayers()) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(player.getContractUntil());
            getValues().add(calendar.get(Calendar.YEAR) - Year.now().getValue());
        }
        return getValues();
    }
}