import Database.Service;
import Filter.PlayerFilter;
import Filter.TeamFilter;
import Interaction.FilterInteraction;
import Interaction.ModeInteraction;
import Model.Competition;
import Model.Fixture;
import Model.Player;
import Model.Team;
import Object3D.Grid;
import Object3D.Object3D;
import UI.Event;
import UI.UserInterface;
import Util.Get;
import Util.Misc;
import peasy.PeasyCam;
import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    private PeasyCam peasyCam;
    private UserInterface userInterface;

    public Competition competition;

    private Service service;

    private ArrayList<Object3D<Team>> teamObjects3D;
    private TeamFilter teamFilter;
    private ModeInteraction<Team> teamModeInteraction;
    private FilterInteraction<Team> teamFilterInteraction;
    private Team resultTeam;

    private ArrayList<Object3D<Fixture>> fixtureObjects3D;
    private ModeInteraction<Fixture> fixtureModeInteraction;
    private Fixture resultFixture;

    private ArrayList<Object3D<Player>> playerObjects3D;
    private PlayerFilter playerFilter;
    private ModeInteraction<Player> playerModeInteraction;
    private FilterInteraction<Player> playerFilterInteraction;
    private Player resultPlayer;

    private Integer minDistance = 100;

    private Grid grid;
    private Integer grilleSize = 10;
    private Integer gridSize = 1000;

    private Float x = 0f;
    private Float y = 0f;
    private Boolean moveUp = false;
    private Boolean moveDown = false;
    private Boolean moveLeft = false;
    private Boolean moveRight = false;

    @Override
    public void settings() {
        size(1280, 720, P3D);
        smooth();
    }

    @Override
    public void setup() {
        peasyCam = new PeasyCam(this, gridSize / 2);
        peasyCam.setResetOnDoubleClick(false);
        userInterface = new UserInterface(this);

        competition = new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/430"));
//        service = new Service(competition);

        teamObjects3D = new ArrayList<>();
        teamFilter = new TeamFilter(competition);
        teamObjects3D = Get.getTeamObjects3D(this, competition, teamFilter.points(), gridSize);
        teamModeInteraction = new ModeInteraction<>();
        teamFilterInteraction = new FilterInteraction<>();

        playerObjects3D = new ArrayList<>();
        playerModeInteraction = new ModeInteraction<>();
        playerFilterInteraction = new FilterInteraction<>();

        fixtureObjects3D = new ArrayList<>();
        fixtureModeInteraction = new ModeInteraction<>();

        grid = new Grid(this, gridSize, grilleSize);
    }

    @Override
    public void draw() {
        background(0);
        lights();

        pushMatrix();
        textSize(40);
        Misc.onFrontOfPeasyCam(this, peasyCam);
        if (Event.levelIndex == 0) {
            fill(0, 102, 153);
            text(competition.getName(), 0, 0);
            fill(255, 0, 0);
            text(teamFilter.getName(), 0, 40);
        } else if (Event.levelIndex == 1 && Event.teamFieldIndex == 0) {
            fill(0, 102, 153);
            text(resultTeam.getName(), 0, 0);
        } else if (Event.levelIndex == 1 && Event.teamFieldIndex == 1) {
            fill(0, 102, 153);
            text(resultTeam.getName(), 0, 0);
            fill(255, 0, 0);
            text(playerFilter.getName(), 0, 40);
        } else if (Event.levelIndex == 2 && Event.teamFieldIndex == 0) {
            fill(0, 102, 153);
            text(String.format("%s vs %s", resultFixture.getHomeTeamName(), resultFixture.getAwayTeamName()), 0, 0);
            fill(255, 0, 0);
            text(String.format("Date: %s", String.valueOf(resultFixture.getDate())), 0, 40);
            fill(0, 102, 153);
            text(String.format("Matchday: %s - Status: %s", resultFixture.getMatchday(), resultFixture.getStatus()), 0, 80);
            fill(255, 0, 0);
            text(String.format("Result: %s - %s", resultFixture.getResult().getGoalsHomeTeam(), resultFixture.getResult().getGoalsAwayTeam()), 0, 120);
        } else if (Event.levelIndex == 2 && Event.teamFieldIndex == 1) {
            fill(0, 102, 153);
            text(resultPlayer.getName(), 0, 0);
            fill(255, 0, 0);
            text(String.format("Date Of Birth: %s %s", String.valueOf(resultPlayer.getDateOfBirth()).substring(0, 10), String.valueOf(resultPlayer.getDateOfBirth()).substring(24)), 0, 40);
            fill(0, 102, 153);
            text(String.format("Nationality: %s", resultPlayer.getNationality()), 0, 80);
            fill(255, 0, 0);
            text(String.format("Position: %s - Jersey Number: %s", resultPlayer.getPosition(), resultPlayer.getJerseyNumber()), 0, 120);
            fill(0, 102, 153);
            text(String.format("Market Value: %s", Get.getString(resultPlayer.getMarketValue())), 0, 160);
            fill(255, 0, 0);
            text(String.format("Contract Until: %s %s", String.valueOf(resultPlayer.getContractUntil()).substring(0, 10), String.valueOf(resultPlayer.getDateOfBirth()).substring(24)), 0, 200);
        }
        popMatrix();

        rotateX(PI / 2);
        translate(-gridSize / 2, -gridSize / 2, 0);
        userInterface.onFrontOfPeasyCam(peasyCam);
        move();
        translate(x, y, 0);

        if (Event.levelIndex == 0) {
            teamModeInteraction.switchMode(this, peasyCam, userInterface, grid, teamObjects3D);
            teamObjects3D = teamFilterInteraction.switchTeamFilter(competition, teamObjects3D, teamFilter, Event.filterIndex);

            grid.resetZ();
            for (Object3D<Team> object3D : teamObjects3D) {
                grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                object3D.draw(peasyCam);
                object3D.lineBetween(new ArrayList<>(teamObjects3D), minDistance);
            }
            grid.draw();
        }

        if (Event.levelIndex == 1 && Event.teamFieldIndex == 0 && Event.clickedObjects3D != 2) {
            fixtureModeInteraction.switchMode(this, peasyCam, userInterface, grid, fixtureObjects3D);

            grid.resetZ();
            for (Object3D<Fixture> object3D : fixtureObjects3D) {
                grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                object3D.draw(peasyCam);
                object3D.lineBetween(new ArrayList<>(fixtureObjects3D), minDistance);
            }
            grid.draw();
        }

        if (Event.levelIndex == 1 && Event.teamFieldIndex == 1 && Event.clickedObjects3D != 2) {
            playerModeInteraction.switchMode(this, peasyCam, userInterface, grid, playerObjects3D);
            playerObjects3D = playerFilterInteraction.switchPlayerFilter(resultTeam, playerObjects3D, playerFilter, Event.filterIndex);

            grid.resetZ();
            for (Object3D<Player> object3D : playerObjects3D) {
                grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                object3D.draw(peasyCam);
                object3D.lineBetween(new ArrayList<>(playerObjects3D), minDistance);
            }
            grid.draw();
        }
    }

    @Override
    public void mousePressed() {
        if (Event.modeIndex == 2 && !userInterface.controlP5Foreground.isMouseOver() && Event.clickedObjects3D != 2) {
            if (mouseButton == LEFT) {
                switch (Event.levelIndex) {
                    case 0:
                        teamModeInteraction.resetAllObjects3DStates(teamObjects3D);
                        resultTeam = teamObjects3D.get(ModeInteraction.indexObject3D).type;
                        playerFilter = new PlayerFilter(resultTeam);
                        playerObjects3D = Get.getPlayerObjects3D(this, resultTeam, playerFilter.jerseyNumber(), gridSize);
                        fixtureObjects3D = Get.getFixtureObjects3D(this, resultTeam, gridSize);
                        userInterface.teamFieldForeground.show().setOpen(false);
                        userInterface.teamFieldForeground.setLabel(resultTeam.getName());
                        userInterface.teamFieldWindow.show().setOpen(false);
                        userInterface.teamFieldWindow.setLabel(resultTeam.getName());
                        break;
                    case 1:
                        playerModeInteraction.resetAllObjects3DStates(playerObjects3D);
                        if (Event.teamFieldIndex == 0) {
                            resultFixture = fixtureObjects3D.get(ModeInteraction.indexObject3D).type;
                        }
                        if (Event.teamFieldIndex == 1) {
                            resultPlayer = playerObjects3D.get(ModeInteraction.indexObject3D).type;
                        }
                        Event.levelIndex = 2;
                        Event.filterIndex = 0;
                        break;
                }
            }
            if (mouseButton == RIGHT) {
                switch (Event.levelIndex) {
                    case 0:
                        Object3D<Team> teamObject3D = teamObjects3D.get(ModeInteraction.indexObject3D);
                        teamObject3D.isClicked = true;
                        break;
                    case 1:
                        Object3D<Player> playerObject3D = playerObjects3D.get(ModeInteraction.indexObject3D);
                        playerObject3D.isClicked = true;
                        break;
                }
            }
        }
    }

    private void move() {
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
    }

    //region SHORTCUTS
    @Override
    public void keyPressed() {
        switch (key) {
            case 'm':
                Event.modeIndex++;
                if (Event.modeIndex == 3) Event.modeIndex = 0;
                break;
            case 'n':
                userInterface.newWindow = !userInterface.newWindow;
                userInterface.setMode();
                break;
            case ',':
                if (Event.filterIndex != 1) {
                    Event.filterIndex--;
                }
                break;
            case '.':
                if (Event.levelIndex == 0) {
                    if (Event.filterIndex < 9) Event.filterIndex++;
                } else if (Event.levelIndex == 1) {
                    if (Event.filterIndex < 4) Event.filterIndex++;
                }
                break;
            case '1':
                competition = new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/430"));
                teamFilter = new TeamFilter(competition);
                teamObjects3D = Get.getTeamObjects3D(this, competition, teamFilter.goals(), gridSize);
                break;
            case '2':
                competition = new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/438"));
                teamFilter = new TeamFilter(competition);
                teamObjects3D = Get.getTeamObjects3D(this, competition, teamFilter.goals(), gridSize);
                break;
        }

        if (key == CODED) {
            switch (keyCode) {
                case UP:
                    moveUp = true;
                    break;
                case DOWN:
                    moveDown = true;
                    break;
                case LEFT:
                    moveLeft = true;
                    break;
                case RIGHT:
                    moveRight = true;
                    break;
            }
        }
    }

    @Override
    public void keyReleased() {
        if (key == CODED) {
            switch (keyCode) {
                case UP:
                    moveUp = false;
                    break;
                case DOWN:
                    moveDown = false;
                    break;
                case LEFT:
                    moveLeft = false;
                    break;
                case RIGHT:
                    moveRight = false;
                    break;
            }
        }
    }
    //endregion

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "Main"};
        PApplet.main(concat(appletArgs, passedArgs));

//        final Session session = getSession();
//        try {
//            System.out.println("querying all the managed entities...");
//            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
//            for (EntityType<?> entityType : metamodel.getEntities()) {
//                final String entityName = entityType.getName();
//                final Query query = session.createQuery("from " + entityName);
//                System.out.println("executing: " + query.getQueryString());
//                for (Object o : query.list()) {
//                    System.out.println("  " + o);
//                }
//            }
//        } finally {
//            session.close();
//        }
    }
}