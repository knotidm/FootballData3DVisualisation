import processing.data.JSONObject;

public class Player {
    String name;
    String position;
    Integer jerseyNumber;
    String dateOfBirth;
    String nationality;
    String contractUntil;
    String marketValue;

    public Player(JSONObject player) {
        name = player.getString("name");
        position = player.getString("position");
        jerseyNumber = player.getInt("jerseyNumber");
        dateOfBirth = player.getString("dateOfBirth");
        nationality = player.getString("nationality");
        contractUntil = player.getString("contractUntil");
        marketValue = player.getString("marketValue");
    }
}