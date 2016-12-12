package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;
import Util.Get;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Player {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer playerId;
    @NotNull
    private String name;
    @NotNull
    private String position;
    @NotNull
    private Integer jerseyNumber;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private String nationality;
    @NotNull
    private Date contractUntil;
    @NotNull
    private BigDecimal marketValue;
    @ManyToOne
    private Team team;

    public Player() {
    }

    public Player(JSONObject player) {
        name = player.getString("name");
        position = player.getString("position");
        jerseyNumber = player.getInt("jerseyNumber", 0);
        dateOfBirth = Get.getDate(player.getString("dateOfBirth"));
        nationality = player.getString("nationality");
        contractUntil = Get.getDate(player.getString("contractUntil", ""));
        marketValue = Get.getBigDecimal(player.getString("marketValue", "").replaceAll("[^\\d]+", ""));
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    public Integer getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(Integer jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    @Basic
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Basic
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Basic
    public Date getContractUntil() {
        return contractUntil;
    }

    public void setContractUntil(Date contractUntil) {
        this.contractUntil = contractUntil;
    }

    @Basic
    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    @ManyToOne
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}