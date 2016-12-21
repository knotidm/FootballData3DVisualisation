package Filter;

import Model.Filter;
import Model.Player;
import Model.Team;

import java.time.Year;
import java.util.Collection;
import java.util.Date;

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
            getValues().add(Year.now().getValue() - player.getDateOfBirth().getYear());
        }
        return getValues();
    }

    public Collection<Integer> yearsToEndContract() {
        setName("Years To End Contract");
        getValues().clear();
        for (Player player : team.getPlayers()) {
            Date endContractDate = player.getContractUntil();
            Integer endContractYear = endContractDate.getYear();
            Integer yearNow = Year.now().getValue();
            getValues().add(endContractYear - yearNow);
        }
        return getValues();
    }
}