package Model;

import java.util.ArrayList;
import java.util.Collection;

public class Filter {
    public String name;
    public Collection<Integer> values;

    public Filter() {
        values = new ArrayList<>();
    }
}