package Model;

import Util.Get;
import com.sun.istack.internal.NotNull;
import org.hibernate.annotations.Type;
import processing.data.JSONObject;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Fixture {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer fixtureId;
    @NotNull
    @Type(type = "date")
    private Date date;
    @NotNull
    private String status;
    @NotNull
    private Integer matchday;
    @NotNull
    private String homeTeamName;
    @NotNull
    private String awayTeamName;
    @NotNull
    @OneToOne
    private Result result;
    @ManyToOne
    private Team team;

    public Fixture() {
    }

    Fixture(JSONObject fixture) {
        date = Get.getDetailedDate(fixture.getString("date"));
        status = fixture.getString("status");
        matchday = fixture.getInt("matchday");
        homeTeamName = fixture.getString("homeTeamName");
        awayTeamName = fixture.getString("awayTeamName");
        result = new Result(fixture.getJSONObject("result"));
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(Integer fixtureId) {
        this.fixtureId = fixtureId;
    }

    @Basic
    @Type(type = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    public Integer getMatchday() {
        return matchday;
    }

    public void setMatchday(Integer matchday) {
        this.matchday = matchday;
    }

    @Basic
    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    @Basic
    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    @OneToOne
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    @ManyToOne
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}

