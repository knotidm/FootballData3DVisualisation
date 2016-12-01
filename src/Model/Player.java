package Model;

import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;
import Util.Util;

import java.math.BigDecimal;
import java.util.Date;

public class Player {
    @NotNull
    public String name;
    @NotNull
    public String position;
    @NotNull
    public Integer jerseyNumber;
    @NotNull
    public Date dateOfBirth;
    @NotNull
    public String nationality;
    @NotNull
    public Date contractUntil;
    @NotNull
    public BigDecimal marketValue;

    public Player(JSONObject player) {
        name = player.getString("name");
        position = player.getString("position");
        jerseyNumber = player.getInt("jerseyNumber", 0);
        dateOfBirth = Util.getDate(player.getString("dateOfBirth"));
        nationality = player.getString("nationality");
        contractUntil = Util.getDate(player.getString("contractUntil", ""));
        marketValue = Util.getBigDecimal(player.getString("marketValue", "").replaceAll("[^\\d]+", ""));
    }
}