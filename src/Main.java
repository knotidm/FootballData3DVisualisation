import processing.core.PApplet;

public class Main extends PApplet {

    Team BayernMunchen;

    public void setup() {
        BayernMunchen = new Team("http://api.football-data.org/v1/teams/5");
        println(BayernMunchen.fixtures);
    }

    public void draw() {
        background(0);
        smooth();
        pushMatrix();
        //shape(BayernMunchen.emblem);
        textSize(32);
        text(BayernMunchen.name, 100, 100);
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