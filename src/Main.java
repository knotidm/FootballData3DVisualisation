import Interaction.Interaction;
import Model.Competition;
import Model.Filter;
import Object3D.Grid;
import Object3D.TeamObject3D;
import UI.UserInterface;
import Util.Util;
import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.Vec3D;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Main extends PApplet {
    PeasyCam peasyCam;
    ArrayList<Vec3D> randomVectors;

    Competition competition;
    Filter filter;
    UserInterface userInterface;

    ArrayList<TeamObject3D> teamObjects3D;
    Grid grid;

    int grilleSize = 10; // rozmiar kratki w siatce
    int gridSize = 1000;

    Integer minDistance = 10;

    @Override
    public void setup() {
        peasyCam = new PeasyCam(this, gridSize / 2);
        userInterface = new UserInterface(this);
        grid = new Grid(this, gridSize, grilleSize);

        competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
        filter = new Filter(competition);

        randomVectors = new ArrayList<>();
        competition.standings.forEach((standing) -> randomVectors.add(new Vec3D(random(gridSize), random(gridSize), random(gridSize / 2))));

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

        Interaction.switchMode(this, peasyCam, userInterface, grid, teamObjects3D);
        teamObjects3D = Interaction.switchFilter(competition, teamObjects3D, filter, userInterface.indexFilter);

        grid.resetZ();

        for (TeamObject3D teamObject3D : teamObjects3D) {
            grid.setZ(teamObject3D.location.x, teamObject3D.location.y, teamObject3D.size - teamObject3D.location.z * 0.01f);
        }

        grid.draw();

        for (TeamObject3D teamObject3D : teamObjects3D) {
            teamObject3D.draw(peasyCam);
            teamObject3D.lineBetween(teamObjects3D, minDistance);
        }
    }

    @Override
    public void keyPressed() {
        switch (key) {
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
            case 'm':
                userInterface.indexMode++;
                if (userInterface.indexMode == 3) userInterface.indexMode = 0;
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
    }

    private ArrayList<TeamObject3D> initialize(Competition competition, ArrayList<Integer> filteredValues) {
        teamObjects3D = new ArrayList<>();
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        for (Integer i = 0; i < competition.standings.size(); i++) {
            teamObjects3D.add(new TeamObject3D(this, i,
                    new Vec3D(randomVectors.get(i)),
                    filteredValues.get(i),
                    competition.standings.get(i).teamName,
                    numberFormat.format(Util.getTeamByCompareTeamName(competition, i).squadMarketValue)
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