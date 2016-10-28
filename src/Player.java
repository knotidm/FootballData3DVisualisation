import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

import java.util.Date;

class Player {
    @NotNull
    String name;
    @NotNull
    String position;
    @NotNull
    Integer jerseyNumber;
    @NotNull
    Date dateOfBirth;
    @NotNull
    String nationality;
    @NotNull
    Date contractUntil;
    @NotNull
    Integer marketValue;

    Player(JSONObject player) {
        name = player.getString("name");
        position = player.getString("position");
        jerseyNumber = player.getInt("jerseyNumber", 0);
        dateOfBirth = Util.getDate(player.getString("dateOfBirth"));
        nationality = player.getString("nationality");
        contractUntil = Util.getDate(player.getString("contractUntil", ""));
        marketValue = Util.getInteger(player.getString("marketValue", "").replaceAll("[^\\d]+", ""));
    }
}