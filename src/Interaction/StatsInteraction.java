package Interaction;

import Object3D.Object3D;
import Stats.PlayerStats;
import Stats.TeamStats;

import java.util.ArrayList;

public class StatsInteraction<T> {

    public ArrayList<Object3D<T>> setTeamObjects3DStats(ArrayList<Object3D<T>> objects3D, TeamStats teamStats) {
        for (Integer i = 0; i < objects3D.size(); i++) {
            objects3D.get(i).statsValue = new ArrayList<>(teamStats.values).get(i);
            objects3D.get(i).location.z = objects3D.get(i).size + new ArrayList<>(teamStats.values).get(i);
        }
        return objects3D;
    }

    public ArrayList<Object3D<T>> setPlayerObjects3DStats(ArrayList<Object3D<T>> objects3D, PlayerStats playerStats) {
        for (Integer i = 0; i < objects3D.size(); i++) {
            objects3D.get(i).statsValue = new ArrayList<>(playerStats.values).get(i);
            objects3D.get(i).location.z = objects3D.get(i).size + new ArrayList<>(playerStats.values).get(i);
        }
        return objects3D;
    }
}