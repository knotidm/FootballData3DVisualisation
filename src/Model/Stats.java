package Model;

import java.util.ArrayList;
import java.util.Collection;

public class Stats {
    public String name;
    public Collection<Integer> values;

    public Stats() {
        values = new ArrayList<>();
    }
}