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
    private Competition customCompetition;

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
    private Chart3D teamChart3D;
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
    private Chart3D playerChart3D;
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

        selectCompetition();

        teamModeInteraction = new ModeInteraction<>();
        teamStatsInteraction = new StatsInteraction<>();
        teamChartInteraction = new ChartInteraction<>();

        customCompetition = new Competition();
        teamObjects3D2 = new ArrayList<>();

        fixtureObjects3D = new ArrayList<>();
        fixtureModeInteraction = new ModeInteraction<>();

        playerObjects3D2 = new ArrayList<>();
        playerModeInteraction = new ModeInteraction<>();
        playerStatsInteraction = new StatsInteraction<>();
        playerChartInteraction = new ChartInteraction<>();

        customTeam = new Team();
        playerObjects3D2 = new ArrayList<>();

        grid = new Grid(this, gridSize, grilleSize);
    }

    @Override
    public void mousePressed() {
        if (!competition.equals(initial.competitions.get((int) userInterface.competition.getValue()))) {
            selectCompetition();
        }

        if (Event.modeIndex == 2 && !Event.chartView) {
            if (mouseButton == LEFT) {
                switch (Event.levelIndex) {
                    case 0:
                        if (!Event.customList) {
                            selectTeam(teamObjects3D1);
                        } else if (Event.customList && teamObjects3D2.size() != 0) {
                            selectTeam(teamObjects3D2);
                        }
                        break;
                    case 1:
                        if ((int) userInterface.teamField.getValue() == 0) {
                            resultFixture = fixtureObjects3D.get(ModeInteraction.indexObject3D).type;
                        }
                        if (!Event.customList) {
                            selectPlayer(playerObjects3D1);
                        } else if (Event.customList && playerObjects3D2.size() != 0) {
                            selectPlayer(playerObjects3D2);
                        }
                        Event.levelIndex = 2;
                        break;
                }
            }
            if (mouseButton == RIGHT) {
                switch (Event.levelIndex) {
                    case 0:
                        if (!Event.customList) {
                            Object3D<Team> teamObject3D = teamObjects3D1.get(ModeInteraction.indexObject3D);
                            teamObject3D.isClicked = true;
                            customCompetition.teams.add(teamObject3D.type);
                            for (Standing standing : competition.standings) {
                                if (standing.teamName.equals(teamObject3D.type.name))
                                    customCompetition.standings.add(standing);
                            }

                            teamStats4 = new TeamStats(customCompetition, (int) userInterface.teamStats1Mode.getValue(), (int) userInterface.teamStats1.getValue());
                            teamStats5 = new TeamStats(customCompetition, (int) userInterface.teamStats2Mode.getValue(), (int) userInterface.teamStats2.getValue());
                            teamStats6 = new TeamStats(customCompetition, (int) userInterface.teamStats3Mode.getValue(), (int) userInterface.teamStats3.getValue());

                            teamObjects3D2 = Get.getTeamObjects3D(this, customCompetition, teamStats4, gridSize);
                        }
                        break;
                    case 1:
                        if (!Event.customList) {
                            Object3D<Player> playerObject3D = playerObjects3D1.get(ModeInteraction.indexObject3D);
                            playerObject3D.isClicked = true;
                            customTeam.players.add(playerObject3D.type);

                            playerStats4 = new PlayerStats(customTeam, (int) userInterface.playerStats1.getValue());
                            playerStats5 = new PlayerStats(customTeam, (int) userInterface.playerStats2.getValue());
                            playerStats6 = new PlayerStats(customTeam, (int) userInterface.playerStats3.getValue());

                            playerObjects3D2 = Get.getPlayerObjects3D(this, customTeam, playerStats4, gridSize);
                        }
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
                drawTeamStatsInfo(competition, teamStats1);
            } else if (Event.customList && teamObjects3D2.size() != 0) {
                drawTeamStatsInfo(customCompetition, teamStats4);
            }
        } else if (Event.levelIndex == 1 && (int) userInterface.teamField.getValue() == 0) {
            fill(0, 102, 153);
            text(resultTeam.name, 0, 0);
        } else if (Event.levelIndex == 1 && (int) userInterface.teamField.getValue() == 1) {
            if (!Event.customList) {
                drawPlayerStatsInfo(resultTeam, playerStats1);
            } else if (Event.customList && playerObjects3D2.size() != 0) {
                drawPlayerStatsInfo(customTeam, playerStats4);
            }
        }
        peasyCam.endHUD();

        if (!Event.chartView) {
            peasyCam.beginHUD();
            textSize(40);
            translate(width / 2, height / 2);
            if (Event.levelIndex == 2 && (int) userInterface.teamField.getValue() == 0) {
                fill(0, 102, 153);
                text(String.format("%s vs %s", resultFixture.homeTeamName, resultFixture.awayTeamName), 0, 0);
                fill(255, 0, 0);
                text(String.format("Date: %s", String.valueOf(resultFixture.date)), 0, 40);
                fill(0, 102, 153);
                text(String.format("Matchday: %s - Status: %s", resultFixture.matchday, resultFixture.status), 0, 80);
                fill(255, 0, 0);
                text(String.format("Result: %s - %s", resultFixture.result.goalsHomeTeam, resultFixture.result.goalsAwayTeam), 0, 120);
            } else if (Event.levelIndex == 2 && (int) userInterface.teamField.getValue() == 1) {
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
                } else if (Event.customList && teamObjects3D2.size() != 0) {
                    drawTeamLevelScene(teamObjects3D2, teamStats4, teamStats5, teamStats6);
                }
            }

            if (Event.levelIndex == 1 && (int) userInterface.teamField.getValue() == 0) {
                fixtureModeInteraction.switchMode(this, peasyCam, grid, fixtureObjects3D);

                grid.resetZ();
                for (int i = 0; i < fixtureObjects3D.size(); i++) {
                    Object3D<Fixture> object3D = fixtureObjects3D.get(i);
                    grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                    object3D.draw(peasyCam, this.color(255, 0, 0));
                }
                grid.draw();
            }

            if (Event.levelIndex == 1 && (int) userInterface.teamField.getValue() == 1) {
                if (!Event.customList) {
                    drawPlayerLevelScene(playerObjects3D1, playerStats1, playerStats2, playerStats3);
                } else if (Event.customList && playerObjects3D2.size() != 0) {
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
                } else if (Event.customList && teamObjects3D2.size() != 0) {
                    drawTeamLevelChart(teamObjects3D2, teamStats4, teamStats5, teamStats6);
                }
            }
            if (Event.levelIndex == 1 && (int) userInterface.teamField.getValue() == 1) {
                if (!Event.customList) {
                    drawPlayerLevelChart(playerObjects3D1, playerStats1, playerStats2, playerStats3);
                } else if (Event.customList && playerObjects3D2.size() != 0) {
                    drawPlayerLevelChart(playerObjects3D2, playerStats4, playerStats5, playerStats6);
                }
            }
            peasyCam.endHUD();

            if ((int) userInterface.chartType.getValue() == 3) {
                peasyCam.setActive(true);
                rotateX(PI / 2);

                if (Event.levelIndex == 0) {
                    teamChart3D.draw(peasyCam);
                }

                if (Event.levelIndex == 1 && (int) userInterface.teamField.getValue() == 1) {
                    playerChart3D.draw(peasyCam);
                }

            }
            userInterface.onFrontOfPeasyCam(peasyCam);
        }
    }

    private void selectCompetition() {
        competition = initial.competitions.get((int) userInterface.competition.getValue());
        teamStats1 = new TeamStats(competition, (int) userInterface.teamStats1Mode.getValue(), (int) userInterface.teamStats1.getValue());
        teamStats2 = new TeamStats(competition, (int) userInterface.teamStats2Mode.getValue(), (int) userInterface.teamStats2.getValue());
        teamStats3 = new TeamStats(competition, (int) userInterface.teamStats3Mode.getValue(), (int) userInterface.teamStats3.getValue());
        teamObjects3D1 = Get.getTeamObjects3D(this, competition, teamStats1, gridSize);
    }

    private void selectTeam(ArrayList<Object3D<Team>> teamObjects3D) {
        teamModeInteraction.resetAllObjects3DStates(teamObjects3D);
        resultTeam = teamObjects3D.get(ModeInteraction.indexObject3D).type;

        playerStats1 = new PlayerStats(resultTeam, (int) userInterface.playerStats1.getValue());
        playerStats2 = new PlayerStats(resultTeam, (int) userInterface.playerStats2.getValue());
        playerStats3 = new PlayerStats(resultTeam, (int) userInterface.playerStats3.getValue());

        playerObjects3D1 = Get.getPlayerObjects3D(this, resultTeam, playerStats1, gridSize);

        fixtureObjects3D = Get.getFixtureObjects3D(this, resultTeam, gridSize);

        userInterface.teamField.show().setOpen(false);
        userInterface.teamField.setLabel(resultTeam.name);
    }

    private void selectPlayer(ArrayList<Object3D<Player>> playerObjects3D) {
        playerModeInteraction.resetAllObjects3DStates(playerObjects3D);
        if ((int) userInterface.teamField.getValue() == 1) {
            resultPlayer = playerObjects3D.get(ModeInteraction.indexObject3D).type;
        }
    }

    private void drawTeamStatsInfo(Competition competition, TeamStats teamStats) {
        fill(0, 102, 153);
        text(competition.name, 0, 0);
        fill(255, 0, 0);
        text(teamStats.name, 0, 25);
    }

    private void drawPlayerStatsInfo(Team team, PlayerStats playerStats) {
        fill(0, 102, 153);
        text(team.name, 0, 0);
        fill(255, 0, 0);
        text(playerStats.name, 0, 25);
    }

    private void drawTeamLevelScene(ArrayList<Object3D<Team>> teamObjects3D, TeamStats teamStats1, TeamStats teamStats2, TeamStats teamStats3) {
        teamModeInteraction.switchMode(this, peasyCam, grid, teamObjects3D);
        teamStats1.switchStats((int) userInterface.teamStats1Mode.getValue(), (int) userInterface.teamStats1.getValue());
        teamStats2.switchStats((int) userInterface.teamStats2Mode.getValue(), (int) userInterface.teamStats2.getValue());
        teamStats3.switchStats((int) userInterface.teamStats3Mode.getValue(), (int) userInterface.teamStats3.getValue());
        teamObjects3D = teamStatsInteraction.setTeamObjects3DStats(teamObjects3D, teamStats1);
        teamChart2D = teamChartInteraction.getChart2D(this, userInterface, teamObjects3D, teamStats1, teamStats2, (int) userInterface.chartType.getValue());
        teamChart3D = teamChartInteraction.getChart3D(this, userInterface, teamStats1, teamStats2, teamStats3, (int) userInterface.chartType.getValue());

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
        playerStats1.switchStats((int) userInterface.playerStats1.getValue());
        playerStats2.switchStats((int) userInterface.playerStats2.getValue());
        playerStats3.switchStats((int) userInterface.playerStats3.getValue());
        playerObjects3D = playerStatsInteraction.setPlayerObjects3DStats(playerObjects3D, playerStats1);
        playerChart2D = playerChartInteraction.getChart2D(this, userInterface, playerObjects3D, playerStats1, playerStats2, (int) userInterface.chartType.getValue());
        playerChart3D = playerChartInteraction.getChart3D(this, userInterface, playerStats1, playerStats2, playerStats3, (int) userInterface.chartType.getValue());

        grid.resetZ();
        for (int i = 0; i < playerObjects3D.size(); i++) {
            Object3D<Player> object3D = playerObjects3D.get(i);
            grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
            object3D.draw(peasyCam, ChartInteraction.colours[i]);
        }
        grid.draw();
    }

    private void drawTeamLevelChart(ArrayList<Object3D<Team>> teamObjects3D, TeamStats teamStats1, TeamStats teamStats2, TeamStats teamStats3) {
        teamStats1.switchStats((int) userInterface.teamStats1Mode.getValue(), (int) userInterface.teamStats1.getValue());
        teamStats2.switchStats((int) userInterface.teamStats2Mode.getValue(), (int) userInterface.teamStats2.getValue());
        teamStats3.switchStats((int) userInterface.teamStats3Mode.getValue(), (int) userInterface.teamStats3.getValue());
        teamObjects3D = teamStatsInteraction.setTeamObjects3DStats(teamObjects3D, teamStats1);
        teamChart2D = teamChartInteraction.getChart2D(this, userInterface, teamObjects3D, teamStats1, teamStats2, (int) userInterface.chartType.getValue());
        teamChart3D = teamChartInteraction.getChart3D(this, userInterface, teamStats1, teamStats2, teamStats3, (int) userInterface.chartType.getValue());
        if ((int) userInterface.chartType.getValue() != 3) teamChart2D.draw(width, height);
    }

    private void drawPlayerLevelChart(ArrayList<Object3D<Player>> playerObjects3D, PlayerStats playerStats1, PlayerStats playerStats2, PlayerStats playerStats3) {
        playerStats1.switchStats((int) userInterface.playerStats1.getValue());
        playerStats2.switchStats((int) userInterface.playerStats2.getValue());
        playerStats3.switchStats((int) userInterface.playerStats3.getValue());
        playerObjects3D = playerStatsInteraction.setPlayerObjects3DStats(playerObjects3D, playerStats1);
        playerChart2D = playerChartInteraction.getChart2D(this, userInterface, playerObjects3D, playerStats1, playerStats2, (int) userInterface.chartType.getValue());
        playerChart3D = playerChartInteraction.getChart3D(this, userInterface, playerStats1, playerStats2, playerStats3, (int) userInterface.chartType.getValue());
        if ((int) userInterface.chartType.getValue() != 3) playerChart2D.draw(width, height);
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