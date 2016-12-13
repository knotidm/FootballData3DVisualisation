import DAO.DAO;
import Filter.PlayerFilter;
import Filter.TeamFilter;
import Interaction.Interaction;
import Model.*;
import Object3D.Grid;
import Object3D.Object3D;
import UI.UserInterface;
import Util.Get;
import Util.Misc;
import org.hibernate.Session;
import peasy.PeasyCam;
import processing.core.PApplet;
import processing.opengl.PShader;
import toxi.geom.Vec3D;

import java.util.ArrayList;
import java.util.Collection;

public class Main extends PApplet {
    private Session session;

    private PeasyCam peasyCam;
    private UserInterface userInterface;

    private PShader matCapShader;

    private Competition competition;
    private DAO<Competition> competitionDAO;
    private DAO<Standing> standingDAO;
    private DAO<Home> homeDAO;
    private DAO<Away> awayDAO;
    private DAO<Team> teamDAO;
    private DAO<Fixture> fixtureDAO;
    private DAO<Result> resultDAO;
    private DAO<Player> playerDAO;


    private ArrayList<Vec3D> randomVectors;

    private ArrayList<Object3D<Team>> teamObjects3D;
    private TeamFilter teamFilter;
    private Interaction<Team> teamInteraction;

    private ArrayList<Object3D<Player>> playerObjects3D;
    private PlayerFilter playerFilter;
    private Interaction<Player> playerInteraction;

    private Grid grid;


    private Integer grilleSize = 10;
    private Integer gridSize = 1000;

    private Integer minDistance = 100;
    private Float x = 0f;
    private Float y = 0f;
    private Boolean moveUp = false;
    private Boolean moveDown = false;
    private Boolean moveLeft = false;
    private Boolean moveRight = false;

    private Object3D<Team> homeTeamObject3D;
    private Object3D<Team> awayTeamObject3D;
    private Team resultTeam;
    private Fixture resultFixture;
    private Player resultPlayer;

    @Override
    public void setup() {
//        session = Hibernate.getSession();
        peasyCam = new PeasyCam(this, gridSize / 2);
        peasyCam.setResetOnDoubleClick(false);
        userInterface = new UserInterface(this);

        competition = new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/430"));

//        competitionDAO = new DAO<Competition>(session, competition);
//        for (Standing standing : competition.getStandings()) {
//            standingDAO = new DAO<Standing>(session, standing);
//            Home home = standing.getHome();
//            homeDAO = new DAO<Home>(session, home);
//            homeDAO.delete(Home.class, home.getHomeId());
//            Away away = standing.getAway();
//            awayDAO = new DAO<Away>(session, away);
//            awayDAO.delete(Away.class, away.getAwayId());
//            standingDAO.delete(Standing.class, standing.getStandingId());
//        }
//        for (Team team : competition.getTeams()) {
//            teamDAO = new DAO<Team>(session, team);
//            for (Fixture fixture : team.getFixtures()) {
//                fixtureDAO = new DAO<Fixture>(session, fixture);
//                Result result = fixture.getResult();
//                resultDAO = new DAO<Result>(session, result);
//                resultDAO.delete(Result.class, result.getResultId());
//                fixtureDAO.delete(Fixture.class, fixture.getFixtureId());
//            }
//            for (Player player : team.getPlayers()) {
//                playerDAO = new DAO<Player>(session, player);
//                playerDAO.delete(Player.class, player.getPlayerId());
//            }
//            teamDAO.delete(Team.class, team.getTeamId());
//        }
//        competitionDAO.delete(Competition.class, competition.getCompetitionId());
//        session.close();

        randomVectors = new ArrayList<>();
        competition.getStandings().forEach(standing -> randomVectors.add(new Vec3D(random(gridSize), random(gridSize), random(gridSize / 4))));

        teamObjects3D = new ArrayList<>();
        teamFilter = new TeamFilter(competition);
        teamObjects3D = initialize(competition, teamFilter.points());
        teamInteraction = new Interaction<Team>();

        playerObjects3D = new ArrayList<>();
        playerInteraction = new Interaction<Player>();

        grid = new Grid(this, gridSize, grilleSize);
    }

    @Override
    public void draw() {
        background(0);
        lights();

        pushMatrix();
        textSize(40);
        Misc.onFrontOfPeasyCam(this, peasyCam);

        if (userInterface.clickedObjects3D == 2) {
            fill(0, 102, 153);
            text(String.format("Matchday: %s - Status: %s", resultFixture.getMatchday(), resultFixture.getStatus()), 0, 0);
            fill(255, 0, 0);
            text(String.format("Date: %s", String.valueOf(resultFixture.getDate())), 0, 40);
            fill(0, 102, 153);
            text(String.format("%s vs %s", resultFixture.getHomeTeamName(), resultFixture.getAwayTeamName()), 0, 80);
            fill(255, 0, 0);
            text(String.format("Result: %s - %s", resultFixture.getResult().getGoalsHomeTeam(), resultFixture.getResult().getGoalsAwayTeam()), 0, 120);
        } else if (userInterface.indexLevel == 0) {
            fill(0, 102, 153);
            text(competition.getName(), 0, 0);
            fill(255, 0, 0);
            text(teamFilter.getName(), 0, 40);
        } else if (userInterface.indexLevel == 1) {
            fill(0, 102, 153);
            text(resultTeam.getName(), 0, 0);
            fill(255, 0, 0);
            text(playerFilter.getName(), 0, 40);
        } else if (userInterface.indexLevel == 2) {
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

        if (userInterface.indexLevel == 0) {
            teamInteraction.switchMode(this, peasyCam, userInterface, grid, teamObjects3D);
            teamObjects3D = teamInteraction.switchTeamFilter(competition, teamObjects3D, teamFilter, userInterface.indexFilter);

            grid.resetZ();

            for (Object3D<Team> object3D : teamObjects3D) {
                grid.setZ(object3D.location.x, object3D.location.y, object3D.size - object3D.location.y * 0.01f);
                object3D.draw(peasyCam);
                object3D.lineBetween(new ArrayList<>(teamObjects3D), minDistance);
            }

            grid.draw();
        }

        if (userInterface.indexLevel == 1 && userInterface.clickedObjects3D != 2) {

            playerInteraction.switchMode(this, peasyCam, userInterface, grid, playerObjects3D);

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
        if (userInterface.indexMode == 2 && !userInterface.controlP5.isMouseOver() && userInterface.clickedObjects3D != 2) {
            if (mouseButton == LEFT) {
                switch (userInterface.indexLevel) {
                    case 0:
                        teamInteraction.resetAllObjects3DStates(teamObjects3D);
                        Object3D<Team> teamObject3D = teamObjects3D.get(Interaction.indexObject3D);
                        resultTeam = teamObject3D.type;
                        teamObject3D.isClicked = true;
                        randomVectors.clear();
                        teamObject3D.type.getPlayers().forEach((player) -> randomVectors.add(new Vec3D(random(gridSize), random(gridSize), random(gridSize / 4))));
                        playerObjects3D = initialize(teamObject3D.type);
                        userInterface.indexLevel = 1;
                        break;
                    case 1:
                        playerInteraction.resetAllObjects3DStates(playerObjects3D);
                        resultPlayer = playerObjects3D.get(Interaction.indexObject3D).type;
                        userInterface.indexLevel = 2;
                        break;
                }
            }
            if (mouseButton == RIGHT) {
                switch (userInterface.indexLevel) {
                    case 0:
                        switch (userInterface.clickedObjects3D) {
                            case 0:
                                homeTeamObject3D = teamObjects3D.get(Interaction.indexObject3D);
                                homeTeamObject3D.isClicked = true;
                                userInterface.clickedObjects3D++;
                                break;
                            case 1:
                                awayTeamObject3D = teamObjects3D.get(Interaction.indexObject3D);
                                if (awayTeamObject3D.isClicked) {
                                    awayTeamObject3D.isClicked = false;
                                    userInterface.clickedObjects3D--;
                                } else {
                                    awayTeamObject3D.isClicked = true;
                                    userInterface.clickedObjects3D++;
                                    resultFixture = Get.getFixture(homeTeamObject3D, awayTeamObject3D);
                                    userInterface.indexLevel = 1;
                                }
                                break;
                        }
                        break;
                    case 1:
                        Object3D<Player> playerObject3D = playerObjects3D.get(Interaction.indexObject3D);
                        playerObject3D.isClicked = true;
                        break;
                }
            }
        }
    }

    @Override
    public void mouseReleased() {
        if (mouseButton == LEFT) {
            switch (userInterface.indexLevel) {
                case 0:
                    teamInteraction.resetAllObjects3DStates(teamObjects3D);
                    break;
                case 1:
                    playerInteraction.resetAllObjects3DStates(playerObjects3D);
                    break;
            }
        }
    }

    private ArrayList<Object3D<Team>> initialize(Competition competition, Collection<Integer> filteredValues) {
        teamObjects3D.clear();
        for (Integer i = 0; i < competition.getStandings().size(); i++) {
            teamObjects3D.add(new Object3D<Team>(this,
                    new Vec3D(randomVectors.get(i)),
                    i,
                    Get.getTeam(competition, i),
                    (Integer) new ArrayList(filteredValues).get(i)
            ));
        }
        return teamObjects3D;
    }

    private ArrayList<Object3D<Player>> initialize(Team team) {
        playerFilter = new PlayerFilter(team);
        playerObjects3D.clear();
        ArrayList<Integer> jerseyNumbers = new ArrayList<>(playerFilter.jerseyNumber());
        ArrayList<Player> players = new ArrayList<>(team.getPlayers());

        for (Integer i = 0; i < team.getPlayers().size(); i++) {
            playerObjects3D.add(new Object3D<Player>(this,
                    new Vec3D(randomVectors.get(i)),
                    i,
                    players.get(i),
                    jerseyNumbers.get(i)
            ));
        }
        return playerObjects3D;
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
                competition = new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/430"));
                teamFilter = new TeamFilter(competition);
                randomVectors.clear();
                competition.getStandings().forEach((standing) -> randomVectors.add(new Vec3D(random(gridSize), random(gridSize), random(gridSize / 4))));
                teamObjects3D = initialize(competition, teamFilter.goals());
                break;
            case '2':
                competition = new Competition(Get.getJSONObject("http://api.football-data.org/v1/competitions/438"));
                teamFilter = new TeamFilter(competition);
                randomVectors.clear();
                competition.getStandings().forEach((standing) -> randomVectors.add(new Vec3D(random(gridSize), random(gridSize), random(gridSize / 4))));
                teamObjects3D = initialize(competition, teamFilter.goals());
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

    @Override
    public void settings() {
        size(1280, 720, P3D);
        smooth();
    }

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