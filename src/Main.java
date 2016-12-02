import Filter.FilterPlayer;
import Filter.FilterTeam;
import Interaction.Interaction;
import Model.Competition;
import Model.Player;
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
    private PeasyCam peasyCam;
    private UserInterface userInterface;
    private Competition competition;
    private ArrayList<Vec3D> randomVectors;

    private ArrayList<Object3D<Team>> teamObjects3D;
    private FilterTeam filterTeam;
    private Interaction<Team> teamInteraction;

    private ArrayList<Object3D<Player>> playerObjects3D;
    private FilterPlayer filterPlayer;
    private Interaction<Player> playerInteraction;

    private Grid grid;

    int grilleSize = 10; // rozmiar kratki w siatce
    int gridSize = 1000;

    Integer minDistance = 100;
    public static float x = 0;
    public static float y = 0;
    private boolean moveUp = false;
    private boolean moveDown = false;
    private boolean moveLeft = false;
    private boolean moveRight = false;

    @Override
    public void setup() {
        peasyCam = new PeasyCam(this, gridSize / 2);
        peasyCam.setResetOnDoubleClick(false);
        userInterface = new UserInterface(this);
        competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
        randomVectors = new ArrayList<>();
        competition.standings.forEach(standing -> randomVectors.add(new Vec3D(random(gridSize), random(gridSize), random(gridSize / 2))));

        teamObjects3D = new ArrayList<>();
        filterTeam = new FilterTeam(competition);
        teamObjects3D = initialize(competition, filterTeam.points());
        teamInteraction = new Interaction<Team>();

        playerObjects3D = new ArrayList<>();
        for (Object3D<Team> teamObject3D : teamObjects3D) {
            playerObjects3D = initialize(teamObject3D.type);
        }
        playerInteraction = new Interaction<Player>();

        grid = new Grid(this, gridSize, grilleSize);
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
        text(filterTeam.name, 0, 40);
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

        if (userInterface.competitionLevel) {

            teamInteraction.switchMode(this, peasyCam, userInterface, grid, teamObjects3D);
            teamObjects3D = teamInteraction.switchTeamFilter(competition, teamObjects3D, filterTeam, userInterface.indexFilter);

            grid.resetZ();

            for (Object3D<Team> object3D : teamObjects3D) {
                grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                object3D.draw(peasyCam);
                //object3D.lineBetween(teamObjects3D, minDistance);
            }
        }

        if (userInterface.teamLevel) {

            playerInteraction.switchMode(this, peasyCam, userInterface, grid, playerObjects3D);

            for (Object3D<Team> object3D : teamObjects3D) {
                if (object3D.isClicked)
                    playerObjects3D = initialize(object3D.type);
            }

            grid.resetZ();

            for (Object3D<Player> object3D : playerObjects3D) {
                grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                object3D.draw(peasyCam);
                //object3D.lineBetween(playerObjects3D, minDistance);
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
                filterTeam = new FilterTeam(competition);
                randomVectors.clear();
                competition.standings.forEach((standing) -> randomVectors.add(new Vec3D(random(width), random(height), random(height))));
                teamObjects3D = initialize(competition, filterTeam.goals());
                break;
            case '2':
                competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/438"));
                filterTeam = new FilterTeam(competition);
                randomVectors.clear();
                competition.standings.forEach((standing) -> randomVectors.add(new Vec3D(random(width), random(height), random(height))));
                teamObjects3D = initialize(competition, filterTeam.goals());
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

//    public void mousePressed()
//    {
//        if (teamObjects3D.get(Interaction.indexObject3D).type.getClass() == Team.class) {
//            userInterface.competitionLevel = false;
//            userInterface.teamLevel = true;
//        }
//        if (playerObjects3D.get(Interaction.indexObject3D).type.getClass() == Player.class) {
//            playerObjects3D.get(Interaction.indexObject3D).isClicked = true;
//            userInterface.competitionLevel = true;
//            userInterface.teamLevel = false;
//        }
//    }

    private ArrayList<Object3D<Team>> initialize(Competition competition, ArrayList<Integer> filteredValues) {
        teamObjects3D.clear();
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

    private ArrayList<Object3D<Player>> initialize(Team team) {
        filterPlayer = new FilterPlayer(team);
        playerObjects3D.clear();
        randomVectors.clear();
        team.players.forEach((player) -> randomVectors.add(new Vec3D(random(width), random(height), random(height))));
        for (Integer i = 0; i < team.players.size(); i++) {
            playerObjects3D.add(new Object3D<Player>(this,
                    new Vec3D(randomVectors.get(i)),
                    filterPlayer.jerseyNumber().get(i),
                    i,
                    team.players.get(i)
            ));
        }
        return playerObjects3D;
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