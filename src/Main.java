import Model.*;
import Stats.PlayerStats;
import Stats.TeamStats;
import Interaction.ChartInteraction;
import Interaction.StatsInteraction;
import Interaction.ModeInteraction;
import Object3D.Grid;
import Object3D.Object3D;
import UI.Event;
import UI.UserInterface;
import Util.Get;
import Util.Initial;
import Visualisation.Chart2D;
import Visualisation.Chart3D;
import peasy.PeasyCam;
import processing.core.PApplet;

import java.util.ArrayList;

public class Main extends PApplet {
    private PeasyCam peasyCam;
    private UserInterface userInterface;
    private Initial initial;
    private Competition competition;
    private Competition custom;

    private TeamStats teamStats1;
    private TeamStats teamStats2;
    private TeamStats teamStats3;
    private TeamStats teamStats4;
    private TeamStats teamStats5;
    private TeamStats teamStats6;
    private ArrayList<Object3D<Team>> teamObjects3D1;
    private ArrayList<Object3D<Team>> teamObjects3D2;
    private ModeInteraction<Team> teamModeInteraction;
    private StatsInteraction<Team> teamStatsInteraction;
    private ChartInteraction<Team> teamChartInteraction;
    private Chart2D teamChart2D;
    private Team resultTeam;
    private Team customTeam;

    private ArrayList<Object3D<Fixture>> fixtureObjects3D;
    private ModeInteraction<Fixture> fixtureModeInteraction;
    private Fixture resultFixture;

    private PlayerStats playerStats1;
    private PlayerStats playerStats2;
    private PlayerStats playerStats3;
    private PlayerStats playerStats4;
    private PlayerStats playerStats5;
    private PlayerStats playerStats6;
    private ArrayList<Object3D<Player>> playerObjects3D1;
    private ArrayList<Object3D<Player>> playerObjects3D2;
    private ModeInteraction<Player> playerModeInteraction;
    private StatsInteraction<Player> playerStatsInteraction;
    private ChartInteraction<Player> playerChartInteraction;
    private Chart2D playerChart2D;
    private Player resultPlayer;

    private Grid grid;
    private Integer grilleSize = 10;
    private Integer gridSize = 1000;

    private Float x = 0f;
    private Float y = 0f;
    private Boolean moveUp = false;
    private Boolean moveDown = false;
    private Boolean moveLeft = false;
    private Boolean moveRight = false;
    private Chart3D teamChart3D;
    private Chart3D playerChart3D;

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "Main"};
        PApplet.main(concat(appletArgs, passedArgs));
    }

    @Override
    public void settings() {
        size(1200, 800, P3D);
        smooth();
    }

    @Override
    public void setup() {
        peasyCam = new PeasyCam(this, gridSize / 2);
        peasyCam.setResetOnDoubleClick(false);
        userInterface = new UserInterface(this);
        initial = new Initial(userInterface);
        initial.start();

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        competition = initial.competitions.get(Event.competitionIndex);

        teamStats1 = new TeamStats(competition, Event.teamStats1ModeIndex, Event.stats1Index);
        teamStats2 = new TeamStats(competition, Event.teamStats2ModeIndex, Event.stats2Index);
        teamStats3 = new TeamStats(competition, Event.teamStats3ModeIndex, Event.stats3Index);
        teamObjects3D1 = Get.getTeamObjects3D(this, competition, teamStats1, gridSize);
        teamModeInteraction = new ModeInteraction<>();
        teamStatsInteraction = new StatsInteraction<>();
        teamChartInteraction = new ChartInteraction<>();

        custom = new Competition();
        teamStats4 = new TeamStats(custom, Event.teamStats1ModeIndex, Event.stats1Index);
        teamStats5 = new TeamStats(custom, Event.teamStats2ModeIndex, Event.stats2Index);
        teamStats6 = new TeamStats(custom, Event.teamStats3ModeIndex, Event.stats3Index);
        teamObjects3D2 = new ArrayList<>();

        fixtureObjects3D = new ArrayList<>();
        fixtureModeInteraction = new ModeInteraction<>();

        playerObjects3D2 = new ArrayList<>();
        playerModeInteraction = new ModeInteraction<>();
        playerStatsInteraction = new StatsInteraction<>();
        playerChartInteraction = new ChartInteraction<>();

        customTeam = new Team();
        playerStats4 = new PlayerStats(customTeam, Event.stats1Index);
        playerStats5 = new PlayerStats(customTeam, Event.stats2Index);
        playerStats6 = new PlayerStats(customTeam, Event.stats3Index);
        playerObjects3D2 = new ArrayList<>();

        grid = new Grid(this, gridSize, grilleSize);
    }

    @Override
    public void mousePressed() {
        if (!competition.equals(initial.competitions.get(Event.competitionIndex))) {
            competition = initial.competitions.get(Event.competitionIndex);

            teamStats1 = new TeamStats(competition, Event.teamStats1ModeIndex, Event.stats1Index);
            teamStats2 = new TeamStats(competition, Event.teamStats2ModeIndex, Event.stats2Index);
            teamStats3 = new TeamStats(competition, Event.teamStats3ModeIndex, Event.stats3Index);

            teamObjects3D1 = Get.getTeamObjects3D(this, competition, teamStats1, gridSize);
        }

        if (Event.modeIndex == 2 && !Event.chartView) {
            if (mouseButton == LEFT) {
                switch (Event.levelIndex) {
                    case 0:
                        teamModeInteraction.resetAllObjects3DStates(teamObjects3D1);
                        resultTeam = teamObjects3D1.get(ModeInteraction.indexObject3D).type;

                        playerStats1 = new PlayerStats(resultTeam, Event.stats1Index);
                        playerStats2 = new PlayerStats(resultTeam, Event.stats2Index);
                        playerStats3 = new PlayerStats(resultTeam, Event.stats3Index);

                        playerObjects3D1 = Get.getPlayerObjects3D(this, resultTeam, playerStats1, gridSize);

                        fixtureObjects3D = Get.getFixtureObjects3D(this, resultTeam, gridSize);

                        userInterface.teamField.show().setOpen(false);
                        userInterface.teamField.setLabel(resultTeam.name);
                        break;
                    case 1:
                        playerModeInteraction.resetAllObjects3DStates(playerObjects3D1);
                        if (Event.teamFieldIndex == 0) {
                            resultFixture = fixtureObjects3D.get(ModeInteraction.indexObject3D).type;
                        }
                        if (Event.teamFieldIndex == 1) {
                            resultPlayer = playerObjects3D1.get(ModeInteraction.indexObject3D).type;
                        }
                        Event.levelIndex = 2;
                        Event.stats1Index = 0;
                        break;
                }
            }
            if (mouseButton == RIGHT) {
                switch (Event.levelIndex) {
                    case 0:
                        Object3D<Team> teamObject3D = teamObjects3D1.get(ModeInteraction.indexObject3D);
                        teamObject3D.isClicked = true;
                        custom.teams.add(teamObject3D.type);
                        for (Standing standing : competition.standings) {
                            if (standing.teamName.equals(teamObject3D.type.name))
                                custom.standings.add(standing);
                        }

                        teamStats4 = new TeamStats(custom, Event.teamStats1ModeIndex, Event.stats1Index);
                        teamStats5 = new TeamStats(custom, Event.teamStats2ModeIndex, Event.stats2Index);
                        teamStats6 = new TeamStats(custom, Event.teamStats3ModeIndex, Event.stats3Index);

                        teamObjects3D2 = Get.getTeamObjects3D(this, custom, teamStats3, gridSize);
                        break;
                    case 1:
                        Object3D<Player> playerObject3D = playerObjects3D1.get(ModeInteraction.indexObject3D);
                        playerObject3D.isClicked = true;
                        customTeam.players.add(playerObject3D.type);

                        playerStats4 = new PlayerStats(customTeam, Event.stats1Index);
                        playerStats5 = new PlayerStats(customTeam, Event.stats2Index);
                        playerStats6 = new PlayerStats(customTeam, Event.stats3Index);

                        playerObjects3D2 = Get.getPlayerObjects3D(this, customTeam, playerStats3, gridSize);
                        break;
                }
            }
        }
    }

    @Override
    public void draw() {
        background(0);
        lights();

        textSize(20);

        peasyCam.beginHUD();
        translate(width / 2, 20);

        if (Event.levelIndex == 0) {
            if (!Event.customList) {
                fill(0, 102, 153);
                text(competition.name, 0, 0);
                fill(255, 0, 0);
                text(teamStats1.name, 0, 25);
            } else if (Event.customList) {
                fill(0, 102, 153);
                text(custom.name, 0, 0);
                fill(255, 0, 0);
                text(teamStats3.name, 0, 25);
            }
        } else if (Event.levelIndex == 1 && Event.teamFieldIndex == 0) {
            fill(0, 102, 153);
            text(resultTeam.name, 0, 0);
        } else if (Event.levelIndex == 1 && Event.teamFieldIndex == 1) {
            fill(0, 102, 153);
            text(resultTeam.name, 0, 0);
            fill(255, 0, 0);
            text(playerStats1.name, 0, 25);
        }

        peasyCam.endHUD();

        if (!Event.chartView) {
            peasyCam.beginHUD();
            translate(width / 2, height / 2);
            if (Event.levelIndex == 2 && Event.teamFieldIndex == 0) {
                fill(0, 102, 153);
                text(String.format("%s vs %s", resultFixture.homeTeamName, resultFixture.awayTeamName), 0, 0);
                fill(255, 0, 0);
                text(String.format("Date: %s", String.valueOf(resultFixture.date)), 0, 40);
                fill(0, 102, 153);
                text(String.format("Matchday: %s - Status: %s", resultFixture.matchday, resultFixture.status), 0, 80);
                fill(255, 0, 0);
                text(String.format("Result: %s - %s", resultFixture.result.goalsHomeTeam, resultFixture.result.goalsAwayTeam), 0, 120);
            } else if (Event.levelIndex == 2 && Event.teamFieldIndex == 1) {
                fill(0, 102, 153);
                text(resultPlayer.name, 0, 0);
                fill(255, 0, 0);
                text(String.format("Date Of Birth: %s %s", String.valueOf(resultPlayer.dateOfBirth).substring(0, 10), String.valueOf(resultPlayer.dateOfBirth).substring(24)), 0, 40);
                fill(0, 102, 153);
                text(String.format("Nationality: %s", resultPlayer.nationality), 0, 80);
                fill(255, 0, 0);
                text(String.format("Position: %s - Jersey Number: %s", resultPlayer.position, resultPlayer.jerseyNumber), 0, 120);
                fill(0, 102, 153);
                text(String.format("Market Value: %s", Get.getString(resultPlayer.marketValue)), 0, 160);
                fill(255, 0, 0);
                text(String.format("Contract Until: %s %s", String.valueOf(resultPlayer.contractUntil).substring(0, 10), String.valueOf(resultPlayer.dateOfBirth).substring(24)), 0, 200);
            }
            peasyCam.endHUD();

            rotateX(PI / 2);
            translate(-gridSize / 2, -gridSize / 2, 0);
            move();
            translate(x, y, 0);

            if (Event.levelIndex == 0) {
                if (!Event.customList) {
                    drawTeamLevelScene(teamObjects3D1, teamStats1, teamStats2, teamStats3);
                } else if (Event.customList) {
                    drawTeamLevelScene(teamObjects3D2, teamStats4, teamStats5, teamStats6);
                }
            }

            if (Event.levelIndex == 1 && Event.teamFieldIndex == 0 && Event.clickedObjects3D != 2) {
                fixtureModeInteraction.switchMode(this, peasyCam, grid, fixtureObjects3D);

                grid.resetZ();
                for (int i = 0; i < fixtureObjects3D.size(); i++) {
                    Object3D<Fixture> object3D = fixtureObjects3D.get(i);
                    grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                    object3D.draw(peasyCam, this.color(255, 0, 0));
                }
                grid.draw();
            }

            if (Event.levelIndex == 1 && Event.teamFieldIndex == 1 && Event.clickedObjects3D != 2) {
                if (!Event.customList) {
                    drawPlayerLevelScene(playerObjects3D1, playerStats1, playerStats2, playerStats3);
                } else if (Event.customList) {
                    drawPlayerLevelScene(playerObjects3D2, playerStats4, playerStats5, playerStats6);
                }
            }
        }

        if (Event.chartView) {
            peasyCam.setActive(false);
            peasyCam.beginHUD();

            if (Event.levelIndex == 0) {
                if (!Event.customList) {
                    drawTeamLevelChart(teamObjects3D1, teamStats1, teamStats2, teamStats3);
                } else if (Event.customList) {
                    drawTeamLevelChart(teamObjects3D2, teamStats4, teamStats5, teamStats6);
                }
            }
            if (Event.levelIndex == 1 && Event.teamFieldIndex == 1) {
                if (!Event.customList) {
                    drawPlayerLevelChart(playerObjects3D1, playerStats1, playerStats2, playerStats3);
                } else if (Event.customList) {
                    drawPlayerLevelChart(playerObjects3D2, playerStats4, playerStats5, playerStats6);
                }
            }
            peasyCam.endHUD();

            if (Event.chartTypeIndex == 3) {
                peasyCam.setActive(true);
                rotateX(PI / 2);

                if (Event.levelIndex == 0) {
                    teamChart3D.draw(width, height);
                }

                if (Event.levelIndex == 1 && Event.teamFieldIndex == 1) {
                    playerChart3D.draw(width, height);
                }

//                translate(-gridSize / 2, -gridSize / 2, 0);
//                grid.resetZ();
//                grid.draw();
            }


            userInterface.onFrontOfPeasyCam(peasyCam);
        }
    }

    private void drawTeamLevelScene(ArrayList<Object3D<Team>> teamObjects3D, TeamStats teamStats1, TeamStats teamStats2, TeamStats teamStats3) {
        teamModeInteraction.switchMode(this, peasyCam, grid, teamObjects3D);
        teamStats1.switchStats(Event.stats1Index, Event.teamStats1ModeIndex);
        teamStats2.switchStats(Event.stats2Index, Event.teamStats2ModeIndex);
        teamStats3.switchStats(Event.stats3Index, Event.teamStats3ModeIndex);
        teamObjects3D = teamStatsInteraction.setTeamObjects3DStats(teamObjects3D, teamStats1);
        teamChart2D = teamChartInteraction.getChart2D(this, userInterface, teamObjects3D, teamStats1, teamStats2, Event.chartTypeIndex);
        teamChart3D = teamChartInteraction.getChart3D(this, userInterface, teamStats1, teamStats2, teamStats3, Event.chartTypeIndex);

        grid.resetZ();
        for (int i = 0; i < teamObjects3D.size(); i++) {
            Object3D<Team> object3D = teamObjects3D.get(i);
            grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
            object3D.draw(peasyCam, ChartInteraction.colours[i]);
        }
        grid.draw();
    }

    private void drawPlayerLevelScene(ArrayList<Object3D<Player>> playerObjects3D, PlayerStats playerStats1, PlayerStats playerStats2, PlayerStats playerStats3) {
        playerModeInteraction.switchMode(this, peasyCam, grid, playerObjects3D);
        playerStats1.switchStats(Event.stats1Index);
        playerStats2.switchStats(Event.stats2Index);
        playerStats3.switchStats(Event.stats3Index);
        playerObjects3D = playerStatsInteraction.setPlayerObjects3DStats(playerObjects3D, playerStats1);
        playerChart2D = playerChartInteraction.getChart2D(this, userInterface, playerObjects3D, playerStats1, playerStats2, Event.chartTypeIndex);
        playerChart3D = playerChartInteraction.getChart3D(this, userInterface, playerStats1, playerStats2, playerStats3, Event.chartTypeIndex);

        grid.resetZ();
        for (int i = 0; i < playerObjects3D.size(); i++) {
            Object3D<Player> object3D = playerObjects3D.get(i);
            grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
            object3D.draw(peasyCam, ChartInteraction.colours[i]);
        }
        grid.draw();
    }

    private void drawTeamLevelChart(ArrayList<Object3D<Team>> teamObjects3D, TeamStats teamStats1, TeamStats teamStats2, TeamStats teamStats3) {
        teamStats1.switchStats(Event.stats1Index, Event.teamStats1ModeIndex);
        teamStats2.switchStats(Event.stats2Index, Event.teamStats2ModeIndex);
        teamStats3.switchStats(Event.stats3Index, Event.teamStats3ModeIndex);
        teamObjects3D = teamStatsInteraction.setTeamObjects3DStats(teamObjects3D, teamStats1);
        teamChart2D = teamChartInteraction.getChart2D(this, userInterface, teamObjects3D, teamStats1, teamStats2, Event.chartTypeIndex);
        teamChart3D = teamChartInteraction.getChart3D(this, userInterface, teamStats1, teamStats2, teamStats3, Event.chartTypeIndex);
        if (Event.chartTypeIndex != 3) teamChart2D.draw(width, height);
    }

    private void drawPlayerLevelChart(ArrayList<Object3D<Player>> playerObjects3D, PlayerStats playerStats1, PlayerStats playerStats2, PlayerStats playerStats3) {
        playerStats1.switchStats(Event.stats1Index);
        playerStats2.switchStats(Event.stats2Index);
        playerStats3.switchStats(Event.stats3Index);
        playerObjects3D = playerStatsInteraction.setPlayerObjects3DStats(playerObjects3D, playerStats1);
        playerChart2D = playerChartInteraction.getChart2D(this, userInterface, playerObjects3D, playerStats1, playerStats2, Event.chartTypeIndex);
        playerChart3D = playerChartInteraction.getChart3D(this, userInterface, playerStats1, playerStats2, playerStats3, Event.chartTypeIndex);
        if (Event.chartTypeIndex != 3) playerChart2D.draw(width, height);
    }

    //region MOVEMENT
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

    @Override
    public void keyPressed() {
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
}