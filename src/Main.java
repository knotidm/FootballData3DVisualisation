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
    Competition bundesliga;
    Filter bundesligaFilter;
    ArrayList<Vec3D> initializeRandomVectors;
    UserInterface userInterface;

    ArrayList<TeamObject3D> teamObjects3D;
    Grid grid;

    int grilleSize = 10; // rozmiar kratki w siatce
    int gridSize = 1000;

    Integer minDistance = 1000;

    @Override
    public void setup() {
        peasyCam = new PeasyCam(this, 120);

        bundesliga = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
        bundesligaFilter = new Filter(bundesliga);
        initializeRandomVectors = new ArrayList<>();

        bundesliga.standings.forEach((standing) -> initializeRandomVectors.add(new Vec3D(random(width), random(height), random(height))));

        userInterface = new UserInterface(this);

        grid = new Grid(this, gridSize, grilleSize);

        teamObjects3D = initialize(bundesliga, bundesligaFilter.points());
    }

    @Override
    public void draw() {
        background(0);
        lights();

        textSize(40);

        pushMatrix();
        Util.onFrontOfPeasyCam(this, peasyCam);
        fill(0, 102, 153);
        text(bundesliga.name, 0, -height);
        fill(255, 0, 0);
        text(bundesligaFilter.filterName, 0, -height + 40);
        popMatrix();

        translate(((-width / 2) + (-height / 2)) / 2, ((-width / 2) + (-height / 2)) / 2, 0);

        userInterface.onFrontOfPeasyCam(peasyCam);

        Interaction.switchMode(this, userInterface, peasyCam, grid, teamObjects3D);
        teamObjects3D = Interaction.switchFilter(userInterface.indexFilter, bundesliga, teamObjects3D, bundesligaFilter);

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
        }
    }

    private ArrayList<TeamObject3D> initialize(Competition competition, ArrayList<Integer> filteredValues) {
        teamObjects3D = new ArrayList<>();
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        for (Integer i = 0; i < competition.standings.size(); i++) {
            teamObjects3D.add(new TeamObject3D(this, i,
                    new Vec3D(initializeRandomVectors.get(i)),
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