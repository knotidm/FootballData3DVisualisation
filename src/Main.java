import http.requests.GetRequest;
import processing.core.PApplet;
import processing.core.PShape;
import processing.data.JSONObject;

public class Main extends PApplet {

    GetRequest BayernMunchenGetRequest;
    JSONObject BayernMunchenJSONObject;
    String name;
    String emblem;
    PShape BayernMunchenEmblem;

    public void setup() {
        BayernMunchenGetRequest = new GetRequest("http://api.football-data.org/v1/teams/5");
        BayernMunchenGetRequest.addHeader("X-Auth-Token","324794156b594490a7c6244a6a10a034");
        BayernMunchenGetRequest.send();

        BayernMunchenJSONObject = new JSONObject();
        BayernMunchenJSONObject = JSONObject.parse(BayernMunchenGetRequest.getContent());
        name = BayernMunchenJSONObject.getString("name");
        emblem = BayernMunchenJSONObject.getString("crestUrl");
        BayernMunchenEmblem = loadShape("https://upload.wikimedia.org/wikipedia/commons/c/c5/Logo_FC_Bayern_M%C3%BCnchen.svg");

        println(emblem);
    }

    public void draw() {
        background(0);
        smooth();
        pushMatrix();
        shape(BayernMunchenEmblem);
        textSize(32);
        text(name, 20, 20);
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