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

import java.util.ArrayList;

public class Main extends PApplet {
    PeasyCam peasyCam;
    Competition bundesliga;
    Filter bundesligaFilter;
    ArrayList<Vec3D> initializeRandomVectors;
    Integer view = 1;
    UserInterface userInterface;

    ArrayList<TeamObject3D> teamObjects3D; // ilosc pol grawitacyjnych
    Grid grid;

    boolean mouseStillPressed = false;
    int indexGF = 0;
    int indexGrid = 0;
    int grilleSize = 10; // rozmiar kratki w siatce
    int gridSize = 1000;

    Integer minDistance = 1000;

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

        changeView();

        grid = new Grid(this, gridSize, grilleSize);

        teamObjects3D = changeFilter(bundesliga, bundesligaFilter.points());
    }

    public void draw() {
        background(0);
        lights();

        textSize(40);

        pushMatrix();
        Util.alwaysOnFrontOfPeasyCam(this, peasyCam);
        fill(0, 102, 153);
        text(bundesliga.name, 0, -height);
        fill(255, 0, 0);
        text(bundesligaFilter.filterName, 0, -height + 40);
        popMatrix();

        translate(-width / 2, -height / 2, -height / 2);

        userInterface.enableUserInterfaceOnTopOfPeasyCam(peasyCam);

        dragObject();

        grid.resetZ();

        for (TeamObject3D teamObject3D : teamObjects3D)
            grid.setZ(teamObject3D.location.x, teamObject3D.location.y, teamObject3D.size);

        grid.draw();

        for (TeamObject3D teamObject3D : teamObjects3D) {
            teamObject3D.draw(peasyCam);
            teamObject3D.lineBetween(teamObjects3D, minDistance);
        }
    }

    private void changeView() {
        switch (view) {
            case 1:
                teamObjects3D = changeFilter(bundesliga, bundesligaFilter.position());
                break;
            case 2:
                teamObjects3D = changeFilter(bundesliga, bundesligaFilter.points());
                break;
            case 3:
                teamObjects3D = changeFilter(bundesliga, bundesligaFilter.goals());
                break;
            case 4:
                teamObjects3D = changeFilter(bundesliga, bundesligaFilter.goalsAgainst());
                break;
            case 5:
                teamObjects3D = changeFilter(bundesliga, bundesligaFilter.goalDifference());
                break;
            case 6:
                teamObjects3D = changeFilter(bundesliga, bundesligaFilter.wins());
                break;
            case 7:
                teamObjects3D = changeFilter(bundesliga, bundesligaFilter.draws());
                break;
            case 8:
                teamObjects3D = changeFilter(bundesliga, bundesligaFilter.losses());
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

    private ArrayList<TeamObject3D> changeFilter(Competition competition, ArrayList<Integer> filteredValues) {
        teamObjects3D = new ArrayList<TeamObject3D>();
        for (Integer i = 0; i < competition.standings.size(); i++) {
            String squadMarketValueString = "";
            if (Util.getTeamByCompareStandingTeamName(competition, i).squadMarketValue != 0) {
                squadMarketValueString = Util.getTeamByCompareStandingTeamName(competition, i).squadMarketValue.toString() + " €";
            }
            teamObjects3D.add(new TeamObject3D(this, i,
                    new Vec3D(initializeRandomVectors.get(i)),
                    filteredValues.get(i),
                    competition.standings.get(i).teamName,
                    squadMarketValueString
            ));
        }
        return teamObjects3D;
    }

    private void dragObject() {
        for (TeamObject3D teamObject3D : teamObjects3D)
            teamObject3D.isSelected(false); // stan niezaznaczenia dla pola grawitacyjnego
        if (keyPressed) {
            if (key == 'm' || key == 'M') {
                peasyCam.setActive(false); // wylacza peasyCam
                if (!mouseStillPressed) {
                    float mouseShortestDistance = sqrt(sq(mouseX - screenX(teamObjects3D.get(indexGF).location.x, teamObjects3D.get(indexGF).location.y, 0)) + sq(mouseY - screenY(teamObjects3D.get(indexGF).location.x, teamObjects3D.get(indexGF).location.y, 0))); // najmniejszy dopuszczalny dystans myszki jesli myszka nie wcisnieta
                    for (TeamObject3D teamObject3D : teamObjects3D) {
                        float mouseDistance = sqrt(sq(mouseX - screenX(teamObject3D.location.x, teamObject3D.location.y, 0)) + sq(mouseY - screenY(teamObject3D.location.x, teamObject3D.location.y, 0))); // aktualny dystans myszki jesli myszka nie wcisnieta
                        if (mouseDistance <= mouseShortestDistance) {
                            indexGF = teamObject3D.index;
                            mouseShortestDistance = mouseDistance;
                        }
                    }
                }
                teamObjects3D.get(indexGF).isSelected(true); // stan zaznaczenia dla pola grawitacyjnego
                float mouseShortestDistance = sqrt(sq(mouseX - screenX(grid.x[indexGrid], grid.y[indexGrid], 0)) + sq(mouseY - screenY(grid.x[indexGrid], grid.y[indexGrid], 0))); // najmniejszy dopuszczalny dystans myszki jesli myszka wcisnieta
                for (int i = 0; i < grid.x.length; i++) {
                    float mouseDistance = sqrt(sq(mouseX - screenX(grid.x[i], grid.y[i], 0)) + sq(mouseY - screenY(grid.x[i], grid.y[i], 0))); // aktualny dystans myszki jesli myszka wcisnieta
                    if (mouseDistance <= mouseShortestDistance) {
                        indexGrid = i;
                        mouseShortestDistance = mouseDistance;
                    }
                }
                if (mousePressed) {
                    mouseStillPressed = true;
                    if (mouseButton == LEFT) {
                        teamObjects3D.get(indexGF).location.x += (grid.x[indexGrid] - teamObjects3D.get(indexGF).location.x) / 6; // translacja w osi x (ostatnia zmienna spowolnienie)
                        teamObjects3D.get(indexGF).location.y += (grid.y[indexGrid] - teamObjects3D.get(indexGF).location.y) / 6; // translacja w osi y (ostatnia zmienna spowolnienie)
                    }
                } else {
                    mouseStillPressed = false;
                }
            }
        } else {
            peasyCam.setActive(true); // wlacza peasyCam
        }
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