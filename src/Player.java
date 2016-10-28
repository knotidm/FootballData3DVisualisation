import com.sun.istack.internal.NotNull;
import processing.data.JSONObject;

class Player {
    @NotNull
    String name;
    @NotNull
    String position;
    @NotNull
    Integer jerseyNumber;
    @NotNull
    String dateOfBirth;
    @NotNull
    String nationality;
    @NotNull
    String contractUntil;
    @NotNull
    Integer marketValue;

    Player(JSONObject player) {
        name = player.getString("name");
        position = player.getString("position");
        jerseyNumber = player.getInt("jerseyNumber", 0);
        dateOfBirth = player.getString("dateOfBirth");
        nationality = player.getString("nationality");
        contractUntil = player.getString("contractUntil", "");
        marketValue = getMarketValue(player);
    }

    private Integer getMarketValue(JSONObject player) {
        String marketValueString = player.getString("marketValue", "").replaceAll("[^\\d]+", "");
        if (!marketValueString.equals("")) {
            return Integer.parseInt(marketValueString);
        }
        return 0;
    }
}