package Filter;

import Model.Filter;
import Model.Player;
import Model.Team;

import java.util.ArrayList;

public class FilterPlayer extends Filter {
    Team team;

    public FilterPlayer(Team team) {
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
