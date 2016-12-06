package Model;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;

public class Filter {
    @NotNull
    public String name;
    @NotNull
    public ArrayList<Integer> values;

    public Filter() {
        values = new ArrayList<>();
    }
}