import processing.data.JSONObject;

class Player {
    String name;
    String position;
    Integer jerseyNumber;
    String dateOfBirth;
    String nationality;
    String contractUntil;
    String marketValue;

    Player(JSONObject player) {
        name = player.getString("name");
        position = player.getString("position");
        if (!player.isNull("jerseyNumber")) jerseyNumber = player.getInt("jerseyNumber");
        if (!player.isNull("dateOfBirth")) dateOfBirth = player.getString("dateOfBirth");
        nationality = player.getString("nationality");
        if (!player.isNull("contractUntil")) contractUntil = player.getString("contractUntil");
        if (!player.isNull("marketValue")) marketValue = player.getString("marketValue");
    }
}