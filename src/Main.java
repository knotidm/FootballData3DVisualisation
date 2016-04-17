import processing.core.PApplet;

public class Main extends PApplet {

    SoccerSeason Bundesliga;

    public void setup() {
        //Bundesliga = new SoccerSeason("http://api.football-data.org/v1/teams/5");
    }

    public void draw() {
        background(0);
        smooth();
        pushMatrix();
        textSize(32);

        int i = 1;
//        for (Player player: BayernMunchen.players) {
//            textSize(20);
//            text(player.name, 100, i *25);
//            text(player.jerseyNumber, 400, i *25);
//            text(player.nationality, 500, i *25);
//            i++;
//        }

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