package Model;

import Util.Get;
import processing.data.JSONObject;

import java.math.BigDecimal;
import java.util.Date;

public class Player {
    public String name;
    public String position;
    public Integer jerseyNumber;
    public Date dateOfBirth;
    public String nationality;
    public Date contractUntil;
    public BigDecimal marketValue;

    public Player(JSONObject player) {
        name = player.getString("name");
        position = player.getString("position");
        jerseyNumber = player.getInt("jerseyNumber", 0);
        dateOfBirth = Get.getDate(player.getString("dateOfBirth"));
        nationality = player.getString("nationality");
        contractUntil = Get.getDate(player.getString("contractUntil", ""));
        marketValue = Get.getBigDecimal(player.getString("marketValue", "").replaceAll("[^\\d]+", ""));
    }
}