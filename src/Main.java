import Model.Competition;
import Model.Filter;
import Model.Standing;
import Util.Util;
import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.Vec3D;

import java.util.ArrayList;

public class Main extends PApplet {
    PeasyCam peasyCam;
    ArrayList<Particle> particles;
    Competition bundesliga;
    Filter bundesligaFilter;
    ArrayList<Vec3D> initializeRandomVectors;
    Integer view = 1;

    float separate;
    float cohesion;
    float align;
    Integer minDistance = 1000;
    float speedX, speedY, speedZ, accelerationX, accelerationY, accelerationZ, gravityX, gravityY, gravityZ;

    public void setup() {
        peasyCam = new PeasyCam(this, 120);

        bundesliga = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
        bundesligaFilter = new Filter(bundesliga);
        initializeRandomVectors = new ArrayList<Vec3D>();

        for (Standing standing : bundesliga.standings) {
            initializeRandomVectors.add(new Vec3D(random(width), random(height), random(height)));
        }

        view = 1;

        changeView();
        particles = changeFilter(bundesliga, bundesligaFilter.points());
    }

    public void draw() {
        background(0);
        lights();

        textSize(40);

        pushMatrix();
        rotateX(peasyCam.getRotations()[0]);
        rotateY(peasyCam.getRotations()[1]);
        rotateZ(peasyCam.getRotations()[2]);
        fill(0, 102, 153);
        text(bundesliga.name, 0, -height);
        fill(255, 0, 0);
        text(bundesligaFilter.filterName, 0, -height + 40);
        popMatrix();
//        for (Model.Team team : bundesliga.teams) {
//            textSize(20);
//            text(team.name, 100, i * 25);
//            text(team.squadMarketValue, 400, i * 25);
//            //text(player.nationality, 500, i *25);
//            i++;
//        }

//        for (Model.Fixture fixture: BayernMunchen.fixtures) {
//            text(fixture.date, 100, i *30);
//            text(fixture.awayTeamName, 400, i *30);
//            text(fixture.homeTeamName, 500, i *30);
//            i++;
//        }

        translate(-width / 2, -height / 2, -height / 2);
        for (Particle particle : particles) {
            particle.display(peasyCam);
//            particles.get(i).move();
//            particles.get(i).bounce();
//            particles.get(i).gravity();
//            particles.get(i).separate(separate, minDistance);
//            particles.get(i).cohesion(cohesion, minDistance);
//            particles.get(i).align(align, minDistance);
            particle.lineBetween(particles, minDistance);
//            particles.get(i).shapeBetween(minDistance);
        }
    }


    private void changeView() {
        switch (view) {
            case 1:
                particles = changeFilter(bundesliga, bundesligaFilter.position());
                break;
            case 2:
                particles = changeFilter(bundesliga, bundesligaFilter.points());
                break;
            case 3:
                particles = changeFilter(bundesliga, bundesligaFilter.goals());
                break;
            case 4:
                particles = changeFilter(bundesliga, bundesligaFilter.goalsAgainst());
                break;
            case 5:
                particles = changeFilter(bundesliga, bundesligaFilter.goalDifference());
                break;
            case 6:
                particles = changeFilter(bundesliga, bundesligaFilter.wins());
                break;
            case 7:
                particles = changeFilter(bundesliga, bundesligaFilter.draws());
                break;
            case 8:
                particles = changeFilter(bundesliga, bundesligaFilter.losses());
                break;
        }
    }

    public void keyPressed() {
        switch (key) {
            case ',':
                if (view != 1) {
                    view--;
                    changeView();
                }
                break;
            case '.':
                if (view != 8) {
                    view++;
                    changeView();
                }
                break;
        }
    }

    private ArrayList<Particle> changeFilter(Competition competition, ArrayList<Integer> filteredValues) {
        particles = new ArrayList<Particle>();
        for (Integer i = 0; i < competition.standings.size(); i++) {
            String squadMarketValueString = "";
            if (Util.getTeamByCompareStandingTeamName(competition, i).squadMarketValue != 0) {
                squadMarketValueString = Util.getTeamByCompareStandingTeamName(competition, i).squadMarketValue.toString() + " €";
            }
            particles.add(new Particle(this,
                    new Vec3D(initializeRandomVectors.get(i)),
                    filteredValues.get(i),
                    competition.standings.get(i).teamName,
                    squadMarketValueString
            ));
        }
        return particles;
    }

    public void settings() {
        size(800, 600, P3D);
        smooth();
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "Main"};
        PApplet.main(concat(appletArgs, passedArgs));
    }
}