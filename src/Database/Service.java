package Database;

import Model.*;
import Util.Hibernate;
import org.hibernate.Session;

public class Service {
    private Session session;

    private DAO<Competition> competitionDAO;
    private DAO<Standing> standingDAO;
    private DAO<Home> homeDAO;
    private DAO<Away> awayDAO;
    private DAO<Team> teamDAO;
    private DAO<Fixture> fixtureDAO;
    private DAO<Result> resultDAO;
    private DAO<Player> playerDAO;

    public Service(Competition competition) {
        session = Hibernate.getSession();
        competitionDAO = new DAO<>(session, competition);
        for (Standing standing : competition.getStandings()) {
            standingDAO = new DAO<>(session, standing);
            Home home = standing.getHome();
            homeDAO = new DAO<>(session, home);
            homeDAO.delete(Home.class, home.getHomeId());
            Away away = standing.getAway();
            awayDAO = new DAO<>(session, away);
            awayDAO.delete(Away.class, away.getAwayId());
            standingDAO.delete(Standing.class, standing.getStandingId());
        }
        for (Team team : competition.getTeams()) {
            teamDAO = new DAO<>(session, team);
            for (Fixture fixture : team.getFixtures()) {
                fixtureDAO = new DAO<>(session, fixture);
                Result result = fixture.getResult();
                resultDAO = new DAO<>(session, result);
                resultDAO.delete(Result.class, result.getResultId());
                fixtureDAO.delete(Fixture.class, fixture.getFixtureId());
            }
            for (Player player : team.getPlayers()) {
                playerDAO = new DAO<>(session, player);
                playerDAO.delete(Player.class, player.getPlayerId());
            }
            teamDAO.delete(Team.class, team.getTeamId());
        }
        competitionDAO.delete(Competition.class, competition.getCompetitionId());
        session.close();

    }
}