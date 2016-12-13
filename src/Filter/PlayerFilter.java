package Filter;

import Model.Filter;
import Model.Player;
import Model.Team;

import java.util.Collection;

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
}