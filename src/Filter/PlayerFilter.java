package Filter;

import Model.Filter;
import Model.Player;
import Model.Team;

import java.util.ArrayList;

public class PlayerFilter extends Filter {
    private Team team;

    public PlayerFilter(Team team) {
        super();
        this.team = team;
    }

    public ArrayList<Integer> jerseyNumber() {
        name = "Jersey Number";
        values.clear();
        for (Player player : team.players) {
            values.add(player.jerseyNumber);
        }
        return values;
    }
}