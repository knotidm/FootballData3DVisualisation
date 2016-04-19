import http.requests.GetRequest;
import processing.core.PApplet;
import processing.data.JSONObject;

public class Main extends PApplet {
    SoccerSeason Bundesliga;

    static JSONObject GetRequestToJSONObject(String link) {
        GetRequest getRequest = new GetRequest(link);
        getRequest.addHeader("X-Auth-Token", "324794156b594490a7c6244a6a10a034");
        getRequest.send();
        return JSONObject.parse(getRequest.getContent());
    }

    public void setup() {
        Bundesliga = new SoccerSeason(GetRequestToJSONObject("http://api.football-data.org/v1/soccerseasons/394"));
    }

    public void draw() {
        background(0);
        smooth();
        pushMatrix();
        textSize(32);

        int i = 1;

        for (Team team : Bundesliga.teams) {
            textSize(20);
            text(team.name, 100, i * 25);
            text(team.squadMarketValue, 400, i * 25);
            //text(player.nationality, 500, i *25);
            i++;
        }

//        for (Fixture fixture: BayernMunchen.fixtures) {
//            text(fixture.date, 100, i *30);
//            text(fixture.awayTeamName, 400, i *30);
//            text(fixture.homeTeamName, 500, i *30);
//            i++;
//        }
        popMatrix();
    }

    public void settings() {
        size(800, 600, P3D);
    }
    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "Main"};
        if (passedArgs != null) {
            PApplet.main(concat(appletArgs, passedArgs));
        } else {
            PApplet.main(appletArgs);
        }
    }
}