import Interaction.Interaction;
import Model.Competition;
import Model.Filter;
import Model.Team;
import Object3D.Grid;
import Object3D.Object3D;
import UI.UserInterface;
import Util.Util;
import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.Vec3D;

import java.util.ArrayList;

public class Main extends PApplet {
    PeasyCam peasyCam;
    ArrayList<Vec3D> randomVectors;

    Competition competition;
    Filter filter;
    UserInterface userInterface;

    ArrayList<Object3D<Team>> teamObjects3D;
    Grid grid;

    int grilleSize = 10; // rozmiar kratki w siatce
    int gridSize = 1000;

    Integer minDistance = 100;
    public static float x = 0;
    public static float y = 0;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;
    public boolean competitionLevel = true;
    public boolean teamLevel = false;

    @Override
    public void setup() {
        peasyCam = new PeasyCam(this, gridSize / 2);
        peasyCam.setResetOnDoubleClick(false);
        userInterface = new UserInterface(this);
        grid = new Grid(this, gridSize, grilleSize);

        competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
        filter = new Filter(competition);

        randomVectors = new ArrayList<>();
        competition.standings.forEach(standing -> randomVectors.add(new Vec3D(random(gridSize), random(gridSize), random(gridSize / 2))));

        teamObjects3D = initialize(competition, filter.points());
    }

    @Override
    public void draw() {
        background(0);
        lights();

        pushMatrix();
        textSize(40);
        Util.onFrontOfPeasyCam(this, peasyCam);
        fill(0, 102, 153);
        text(competition.name, 0, 0);
        fill(255, 0, 0);
        text(filter.name, 0, 40);
        popMatrix();

        rotateX(PI / 2);
        translate(-gridSize / 2, -gridSize / 2, 0);

        userInterface.onFrontOfPeasyCam(peasyCam);

        if (moveUp) {
            y++;
        }
        if (moveDown) {
            y--;
        }
        if (moveLeft) {
            x++;
        }
        if (moveRight) {
            x--;
        }

        translate(x, y, 0);

        Object3D<Object>[] objects3D = (Object3D<Object>[]) teamObjects3D.toArray();

        Interaction.switchMode(this, peasyCam, userInterface, grid, objects3D);
        teamObjects3D = Interaction.switchFilter(competition, teamObjects3D, filter, userInterface.indexFilter);

        if (competitionLevel) {
            grid.resetZ();

            for (Object3D<Team> object3D : teamObjects3D) {
                grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                object3D.draw(peasyCam);
                object3D.lineBetween(objects3D, minDistance);
            }
        }

        grid.draw();

    }

    @Override
    public void keyPressed() {
        switch (key) {
            case 'm':
                userInterface.indexMode++;
                if (userInterface.indexMode == 3) userInterface.indexMode = 0;
                break;
            case ',':
                if (userInterface.indexFilter != 1) {
                    userInterface.indexFilter--;
                }
                break;
            case '.':
                if (userInterface.indexFilter != 8) {
                    userInterface.indexFilter++;
                }
                break;
            case '1':
                competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
                filter = new Filter(competition);
                randomVectors.clear();
                competition.standings.forEach((standing) -> randomVectors.add(new Vec3D(random(width), random(height), random(height))));
                teamObjects3D = initialize(competition, filter.goals());
                break;
            case '2':
                competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/438"));
                filter = new Filter(competition);
                randomVectors.clear();
                competition.standings.forEach((standing) -> randomVectors.add(new Vec3D(random(width), random(height), random(height))));
                teamObjects3D = initialize(competition, filter.goals());
                break;
        }

        if (key == CODED) {
            if (keyCode == UP) {
                moveUp = true;
            }
            if (keyCode == DOWN) {
                moveDown = true;
            }
            if (keyCode == LEFT) {
                moveLeft = true;
            }
            if (keyCode == RIGHT) {
                moveRight = true;
            }
        }
    }

    @Override
    public void keyReleased() {
        if (key == CODED) {
            if (keyCode == UP) {
                moveUp = false;
            }
            if (keyCode == DOWN) {
                moveDown = false;
            }
            if (keyCode == LEFT) {
                moveLeft = false;
            }
            if (keyCode == RIGHT) {
                moveRight = false;
            }
        }
    }

    private ArrayList<Object3D<Team>> initialize(Competition competition, ArrayList<Integer> filteredValues) {
        teamObjects3D = new ArrayList<>();
        for (Integer i = 0; i < competition.standings.size(); i++) {
            teamObjects3D.add(new Object3D<Team>(this,
                    new Vec3D(randomVectors.get(i)),
                    filteredValues.get(i),
                    i,
                    Util.getTeamByCompareTeamName(competition, i)
            ));
        }
        return teamObjects3D;
    }

    @Override
    public void settings() {
        size(1280, 720, P3D);
        smooth();
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "Main"};
        PApplet.main(concat(appletArgs, passedArgs));
    }
}