package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Result {
    @Id
    private Integer resultId;
    @NotNull
    private Integer goalsHomeTeam;
    @NotNull
    private Integer goalsAwayTeam;

    public Result() {
    }

    public Result(JSONObject result) {
        goalsHomeTeam = result.getInt("goalsHomeTeam", 0);
        goalsAwayTeam = result.getInt("goalsAwayTeam", 0);
    }

    @Id
    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    @Basic
    public Integer getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public void setGoalsHomeTeam(Integer goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
    }

    @Basic
    public Integer getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public void setGoalsAwayTeam(Integer goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
    }
}
