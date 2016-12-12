package Model;

import com.sun.istack.internal.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Filter {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filterId;
    @NotNull
    private String name;
    @NotNull
    @ElementCollection
    private Collection<Integer> values;

    public Filter() {
        values = new ArrayList<>();
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getFilterId() {
        return filterId;
    }

    public void setFilterId(Integer filterId) {
        this.filterId = filterId;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ElementCollection
    public Collection<Integer> getValues() {
        return values;
    }

    public void setValues(Collection<Integer> values) {
        this.values = values;
    }
}