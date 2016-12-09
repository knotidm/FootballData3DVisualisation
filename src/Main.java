import Filter.PlayerFilter;
import Filter.TeamFilter;
import Interaction.Interaction;
import Model.Competition;
import Model.Fixture;
import Model.Player;
import Model.Team;
import Object3D.Grid;
import Object3D.Object3D;
import UI.UserInterface;
import Util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import peasy.PeasyCam;
import processing.core.PApplet;
import toxi.geom.Vec3D;

import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.Collection;

public class Main extends PApplet {
    private PeasyCam peasyCam;
    private UserInterface userInterface;
    private Competition competition;
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
    private Fixture resultFixture;
    private Player resultPlayer;

    @Override
    public void setup() {
        peasyCam = new PeasyCam(this, gridSize / 2);
        peasyCam.setResetOnDoubleClick(false);
        userInterface = new UserInterface(this);
        competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
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
        Util.onFrontOfPeasyCam(this, peasyCam);
        if (userInterface.clickedObjects3D == 2) {
            fill(0, 102, 153);
            text(String.format("Matchday: %s - Status: %s", resultFixture.getMatchday(), resultFixture.getStatus()), 0, 0);
            fill(255, 0, 0);
            text(String.format("Date: %s", String.valueOf(resultFixture.getDate())), 0, 40);
            fill(0, 102, 153);
            text(String.format("%s vs %s", resultFixture.getHomeTeamName(), resultFixture.getAwayTeamName()), 0, 80);
            fill(255, 0, 0);
            text(String.format("Result: %s - %s", resultFixture.getResult().getGoalsHomeTeam(), resultFixture.getResult().getGoalsAwayTeam()), 0, 120);
        } else if (userInterface.indexLevel == 2) {
            fill(0, 102, 153);
            text(resultPlayer.getName(), 0, 0);
            fill(255, 0, 0);
            text(String.format("Date Of Birth: %s", String.valueOf(resultPlayer.getDateOfBirth())), 0, 40);
            fill(0, 102, 153);
            text(String.format("Nationality: %s - Position: %s - Jersey Number: %s", resultPlayer.getNationality(), resultPlayer.getPosition(), resultPlayer.getJerseyNumber()), 0, 80);
            fill(255, 0, 0);
            text(String.format("Market Value: %s - Contract Until: %s", Util.bigDecimalToString(resultPlayer.getMarketValue()), String.valueOf(resultPlayer.getContractUntil())), 0, 120);
        } else {
            fill(0, 102, 153);
            text(competition.getName(), 0, 0);
            fill(255, 0, 0);
            text(teamFilter.getName(), 0, 40);
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
                                    resultFixture = getFixture(homeTeamObject3D, awayTeamObject3D);
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
                    (Integer) new ArrayList(filteredValues).get(i),
                    i,
                    Util.getTeamByCompareTeamName(competition, i)
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
                    jerseyNumbers.get(i),
                    i,
                    players.get(i)
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

    private Fixture getFixture(Object3D<Team> homeTeamObject3D, Object3D<Team> awayTeamObject3D) {
        Fixture resultFixture = new Fixture();
        for (Fixture fixture : homeTeamObject3D.type.getFixtures()) {
            if (fixture.getHomeTeamName().equals(homeTeamObject3D.type.getName()) && fixture.getAwayTeamName().equals(awayTeamObject3D.type.getName())) {
                resultFixture = fixture;
            }
        }
        return resultFixture;
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
                competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/430"));
                teamFilter = new TeamFilter(competition);
                randomVectors.clear();
                competition.getStandings().forEach((standing) -> randomVectors.add(new Vec3D(random(gridSize), random(gridSize), random(gridSize / 4))));
                teamObjects3D = initialize(competition, teamFilter.goals());
                break;
            case '2':
                competition = new Competition(Util.getRequestToJSONObject("http://api.football-data.org/v1/competitions/438"));
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

    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    static public void main(String[] passedArgs) {
        String[] appletArgs = new String[]{"--window-color=#666666", "--stop-color=#cccccc", "Main"};
        PApplet.main(concat(appletArgs, passedArgs));
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    }
}