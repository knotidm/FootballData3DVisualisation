import Model.Competition;
import Model.Filter;
import Model.Standing;
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
    Integer view = 1;
    UserInterface userInterface;

    ArrayList<TeamObject3D> teamObjects3D; // ilosc pol grawitacyjnych
    Grid grid;

    int indexGF = 0;
    int indexGrid = 0;
    float mouseShortestDistance;
    int grilleSize = 10; // rozmiar kratki w siatce
    int gridSize = 1000;

    Integer minDistance = 1000;

    @Override
    public void setup() {
        peasyCam = new PeasyCam(this, 120);

        bundesliga = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
        bundesligaFilter = new Filter(bundesliga);
        initializeRandomVectors = new ArrayList<Vec3D>();

        for (Standing standing : bundesliga.standings) {
            initializeRandomVectors.add(new Vec3D(random(width), random(height), random(height)));
        }

        view = 1;
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
        switchView();

        switchMode();

        grid.resetZ();

        for (TeamObject3D teamObject3D : teamObjects3D)
            grid.setZ(teamObject3D.location.x, teamObject3D.location.y, teamObject3D.size);

        grid.draw();

        for (TeamObject3D teamObject3D : teamObjects3D) {
            teamObject3D.draw(peasyCam);
            teamObject3D.lineBetween(teamObjects3D, minDistance);
        }
    }

    private void switchView() {
        switch (view) {
            case 1:
                teamObjects3D = setFilter(bundesliga, bundesligaFilter.position());
                break;
            case 2:
                teamObjects3D = setFilter(bundesliga, bundesligaFilter.points());
                break;
            case 3:
                teamObjects3D = setFilter(bundesliga, bundesligaFilter.goals());
                break;
            case 4:
                teamObjects3D = setFilter(bundesliga, bundesligaFilter.goalsAgainst());
                break;
            case 5:
                teamObjects3D = setFilter(bundesliga, bundesligaFilter.goalDifference());
                break;
            case 6:
                teamObjects3D = setFilter(bundesliga, bundesligaFilter.wins());
                break;
            case 7:
                teamObjects3D = setFilter(bundesliga, bundesligaFilter.draws());
                break;
            case 8:
                teamObjects3D = setFilter(bundesliga, bundesligaFilter.losses());
                break;
        }
    }

    @Override
    public void keyPressed() {
        switch (key) {
            case ',':
                if (view != 1) {
                    view--;
                    switchView();
                }
                break;
            case '.':
                if (view != 8) {
                    view++;
                    switchView();
                }
                break;
        }
    }

    private ArrayList<TeamObject3D> initialize(Competition competition, ArrayList<Integer> filteredValues) {
        teamObjects3D = new ArrayList<TeamObject3D>();
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

    private ArrayList<TeamObject3D> setFilter(Competition competition, ArrayList<Integer> filteredValues) {
        for (Integer i = 0; i < competition.standings.size(); i++) {
            teamObjects3D.get(i).size = filteredValues.get(i);
        }
        return teamObjects3D;
    }

    private void switchMode() {
        switch (userInterface.modeValue) {
            case 0:
                peasyCam.setActive(true);
                resetAllTeamObjects3DStates();
                break;
            case 1:
                peasyCam.setActive(false);
                dragObject();
                break;
            case 2:
                peasyCam.setActive(false);
                clickObject();
                break;
        }
    }

    private void dragObject() {
        resetAllTeamObjects3DStates();
        teamObjects3D.get(indexGF).isSelected = true;
        positionInRelationToGrid();

        if (mousePressed) {
            if (mouseButton == LEFT) {
                teamObjects3D.get(indexGF).location.x += (grid.x[indexGrid] - teamObjects3D.get(indexGF).location.x) / 6;
                teamObjects3D.get(indexGF).location.y += (grid.y[indexGrid] - teamObjects3D.get(indexGF).location.y) / 6;
            }
            if (mouseButton == RIGHT) {
                teamObjects3D.get(indexGF).location.z += pmouseY - mouseY;
            }
        } else {
            closestObjectInRelationToPosition();
        }
    }

    private void clickObject() {
        resetAllTeamObjects3DStates();
        teamObjects3D.get(indexGF).isSelected = true;
        positionInRelationToGrid();

        if (mousePressed) {
            if (mouseButton == LEFT) {
                teamObjects3D.get(indexGF).isClicked = true;
            }
        } else {
            closestObjectInRelationToPosition();
        }
    }

    private void resetAllTeamObjects3DStates() {
        for (TeamObject3D teamObject3D : teamObjects3D) {
            teamObject3D.isSelected = false;
            teamObject3D.isClicked = false;
        }
    }

    private void positionInRelationToGrid() {
        mouseShortestDistance = sqrt(sq(mouseX - screenX(grid.x[indexGrid], grid.y[indexGrid], 0)) + sq(mouseY - screenY(grid.x[indexGrid], grid.y[indexGrid], 0)));
        for (int i = 0; i < grid.x.length; i++) {
            float mouseDistance = sqrt(sq(mouseX - screenX(grid.x[i], grid.y[i], 0)) + sq(mouseY - screenY(grid.x[i], grid.y[i], 0)));
            if (mouseDistance <= mouseShortestDistance) {
                indexGrid = i;
                mouseShortestDistance = mouseDistance;
            }
        }
    }

    private void closestObjectInRelationToPosition() {
        mouseShortestDistance = sqrt(sq(mouseX - screenX(teamObjects3D.get(indexGF).location.x, teamObjects3D.get(indexGF).location.y, 0)) + sq(mouseY - screenY(teamObjects3D.get(indexGF).location.x, teamObjects3D.get(indexGF).location.y, 0)));
        for (TeamObject3D teamObject3D : teamObjects3D) {
            float mouseDistance = sqrt(sq(mouseX - screenX(teamObject3D.location.x, teamObject3D.location.y, 0)) + sq(mouseY - screenY(teamObject3D.location.x, teamObject3D.location.y, 0)));
            if (mouseDistance <= mouseShortestDistance) {
                indexGF = teamObject3D.index;
                mouseShortestDistance = mouseDistance;
            }
        }
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