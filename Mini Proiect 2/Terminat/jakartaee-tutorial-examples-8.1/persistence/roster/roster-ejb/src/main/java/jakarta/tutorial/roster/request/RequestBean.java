/*
 * Copyright (c) 2014, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0, which is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */
package jakarta.tutorial.roster.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.tutorial.roster.entity.League;
import jakarta.tutorial.roster.entity.League_;
import jakarta.tutorial.roster.entity.Match;
import jakarta.tutorial.roster.entity.Match_;
import jakarta.tutorial.roster.entity.Player;
import jakarta.tutorial.roster.entity.Player_;
import jakarta.tutorial.roster.entity.SummerLeague;
import jakarta.tutorial.roster.entity.Team;
import jakarta.tutorial.roster.entity.Team_;
import jakarta.tutorial.roster.entity.WinterLeague;
import jakarta.tutorial.roster.util.IncorrectSportException;
import jakarta.tutorial.roster.util.LeagueDetails;
import jakarta.tutorial.roster.util.MatchDetails;
import jakarta.tutorial.roster.util.PlayerDetails;
import jakarta.tutorial.roster.util.TeamDetails;
import javax.annotation.PostConstruct;
import javax.ejb.EJBException;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * This is the bean class for the RequestBean enterprise bean.
 *
 * @author ian
 */
@Stateful
public class RequestBean implements Request, Serializable {

    private static final Logger logger
            = Logger.getLogger("roster.request.RequestBean");
    @PersistenceContext
    private EntityManager em;
    private CriteriaBuilder cb;

    @PostConstruct
    private void init() {
        cb = em.getCriteriaBuilder();
    }

    @Override
    public void createPlayer(String id,
            String name,
            String position,
            double salary) {
        logger.info("createPlayer");
        try {
            Player player = new Player(id, name, position, salary);
            em.persist(player);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void addPlayer(String playerId, String teamId) {
        logger.info("addPlayer");
        try {
            Player player = em.find(Player.class, playerId);
            Team team = em.find(Team.class, teamId);

            team.addPlayer(player);
            player.addTeam(team);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void removePlayer(String playerId) {
        logger.info("removePlayer");
        try {
            Player player = em.find(Player.class, playerId);

            Collection<Team> teams = player.getTeams();
            Iterator<Team> i = teams.iterator();
            while (i.hasNext()) {
                Team team = i.next();
                team.dropPlayer(player);
            }

            em.remove(player);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void dropPlayer(String playerId, String teamId) {
        logger.info("dropPlayer");
        try {
            Player player = em.find(Player.class, playerId);
            Team team = em.find(Team.class, teamId);

            team.dropPlayer(player);
            player.dropTeam(team);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public PlayerDetails getPlayer(String playerId) {
        logger.info("getPlayerDetails");
        try {
            Player player = em.find(Player.class, playerId);
            PlayerDetails playerDetails = new PlayerDetails(player.getId(),
                    player.getName(),
                    player.getPosition(),
                    player.getSalary());
            return playerDetails;
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersOfTeam(String teamId) {
        logger.info("getPlayersOfTeam");
        List<PlayerDetails> playerList = null;
        try {
            Team team = em.find(Team.class, teamId);
            playerList = this.copyPlayersToDetails((List<Player>) team.getPlayers());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return playerList;
    }

    @Override
    public List<TeamDetails> getTeamsOfLeague(String leagueId) {
        logger.info("getTeamsOfLeague");
        List<TeamDetails> detailsList = new ArrayList<>();
        Collection<Team> teams = null;

        try {
            League league = em.find(League.class, leagueId);
            teams = league.getTeams();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }

        Iterator<Team> i = teams.iterator();
        while (i.hasNext()) {
            Team team = (Team) i.next();
            TeamDetails teamDetails = new TeamDetails(team.getId(),
                    team.getName(),
                    team.getCity(),
                    team.getScore());
            detailsList.add(teamDetails);
        }
        return detailsList;
    }

    @Override
    public List<PlayerDetails> getPlayersByPosition(String position) {
        logger.info("getPlayersByPosition");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();
                // set the where clause
                cq.where(cb.equal(player.get(Player_.position), position));
                cq.select(player);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByHigherSalary(String name) {
        logger.info("getPlayersByHigherSalary");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player1 = cq.from(Player.class);
                Root<Player> player2 = cq.from(Player.class);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player1.getModel();
                // create a Predicate object that finds players with a salary
                // greater than player1
                Predicate gtPredicate = cb.greaterThan(
                        player1.get(Player_.salary),
                        player2.get(Player_.salary));
                // create a Predicate object that finds the player based on
                // the name parameter
                Predicate equalPredicate = cb.equal(
                        player2.get(Player_.name), name);
                // set the where clause with the predicates
                cq.where(gtPredicate, equalPredicate);
                // set the select clause, and return only unique entries
                cq.select(player1).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersBySalaryRange(double low, double high) {
        logger.info("getPlayersBySalaryRange");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();
                // set the where clause
                cq.where(cb.between(player.get(
                        Player_.salary),
                        low,
                        high));
                // set the select clause
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByLeagueId(String leagueId) {
        logger.info("getPlayersByLeagueId");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();
                // set the where clause
                cq.where(cb.equal(league.get(League_.id), leagueId));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersBySport(String sport) {
        logger.info("getPlayersByLeagueId");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();
                // set the where clause
                cq.where(cb.equal(league.get(League_.sport), sport));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByCity(String city) {
        logger.info("getPlayersByCity");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();
                // set the where clause
                cq.where(cb.equal(team.get(Team_.city), city));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getAllPlayers() {
        logger.info("getAllPlayers");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);

                cq.select(player);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersNotOnTeam() {
        logger.info("getPlayersNotOnTeam");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();
                // set the where clause
                cq.where(cb.isEmpty(player.get(Player_.teams)));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<PlayerDetails> getPlayersByPositionAndName(String position, String name) {
        logger.info("getPlayersByPositionAndName");
        List<Player> players = null;

        try {
            CriteriaQuery<Player> cq = cb.createQuery(Player.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();
                // set the where clause
                cq.where(cb.equal(player.get(Player_.position), position),
                        cb.equal(player.get(Player_.name), name));
                cq.select(player).distinct(true);
                TypedQuery<Player> q = em.createQuery(cq);
                players = q.getResultList();
            }
            return copyPlayersToDetails(players);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<LeagueDetails> getLeaguesOfPlayer(String playerId) {
        logger.info("getLeaguesOfPlayer");
        List<LeagueDetails> detailsList = new ArrayList<>();
        List<League> leagues = null;

        try {
            CriteriaQuery<League> cq = cb.createQuery(League.class);
            if (cq != null) {
                Root<League> league = cq.from(League.class);
                //EntityType<League> League_ = league.getModel();
                Join<League, Team> team = league.join(League_.teams);
                //EntityType<Team> Team_ = team.getModel();
                Join<Team, Player> player = team.join(Team_.players);

                cq.where(cb.equal(player.get(Player_.id), playerId));
                cq.select(league).distinct(true);
                TypedQuery<League> q = em.createQuery(cq);
                leagues = q.getResultList();
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }

        if (leagues == null) {
            logger.log(Level.WARNING, "No leagues found for player with ID {0}.", playerId);
            return null;
        } else {
            Iterator<League> i = leagues.iterator();
            while (i.hasNext()) {
                League league = (League) i.next();
                LeagueDetails leagueDetails = new LeagueDetails(league.getId(),
                        league.getName(),
                        league.getSport());
                detailsList.add(leagueDetails);
            }

        }
        return detailsList;
    }

    @Override
    public List<String> getSportsOfPlayer(String playerId) {
        logger.info("getSportsOfPlayer");
        List<String> sports = new ArrayList<>();

        try {
            CriteriaQuery<String> cq = cb.createQuery(String.class);
            if (cq != null) {
                Root<Player> player = cq.from(Player.class);
                Join<Player, Team> team = player.join(Player_.teams);
                Join<Team, League> league = team.join(Team_.league);

                // Get MetaModel from Root
                //EntityType<Player> Player_ = player.getModel();
                // set the where clause
                cq.where(cb.equal(player.get(Player_.id), playerId));
                cq.select(league.get(League_.sport)).distinct(true);
                TypedQuery<String> q = em.createQuery(cq);
                sports = q.getResultList();
            }

//        Player player = em.find(Player.class, playerId);
//        Iterator<Team> i = player.getTeams().iterator();
//        while (i.hasNext()) {
//            Team team = i.next();
//            League league = team.getLeague();
//            sports.add(league.getSport());
//        }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return sports;
    }

    @Override
    public void createTeamInLeague(TeamDetails teamDetails, String leagueId) {
        logger.info("createTeamInLeague");
        try {
            League league = em.find(League.class, leagueId);
            Team team = new Team(teamDetails.getId(),
                    teamDetails.getName(),
                    teamDetails.getCity());
            em.persist(team);
            team.setLeague(league);
            league.addTeam(team);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void removeTeam(String teamId) {
        logger.info("removeTeam");
        try {
            Team team = em.find(Team.class, teamId);

            Collection<Player> players = team.getPlayers();
            Iterator<Player> i = players.iterator();
            while (i.hasNext()) {
                Player player = (Player) i.next();
                player.dropTeam(team);
            }

            em.remove(team);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public TeamDetails getTeam(String teamId) {
        logger.info("getTeam");
        TeamDetails teamDetails = null;

        try {
            Team team = em.find(Team.class, teamId);
            teamDetails = new TeamDetails(team.getId(), team.getName(), team.getCity());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return teamDetails;
    }

    @Override
    public void createLeague(LeagueDetails leagueDetails) {
        logger.info("createLeague");
        try {
            if (leagueDetails.getSport().equalsIgnoreCase("soccer")
                    || leagueDetails.getSport().equalsIgnoreCase("swimming")
                    || leagueDetails.getSport().equalsIgnoreCase("basketball")
                    || leagueDetails.getSport().equalsIgnoreCase("baseball")) {
                SummerLeague league = new SummerLeague(leagueDetails.getId(),
                        leagueDetails.getName(),
                        leagueDetails.getSport());
                em.persist(league);
            } else if (leagueDetails.getSport().equalsIgnoreCase("hockey")
                    || leagueDetails.getSport().equalsIgnoreCase("skiing")
                    || leagueDetails.getSport().equalsIgnoreCase("snowboarding")) {
                WinterLeague league = new WinterLeague(leagueDetails.getId(),
                        leagueDetails.getName(),
                        leagueDetails.getSport());
                em.persist(league);
            } else {
                throw new IncorrectSportException("The specified sport is not valid.");
            }
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void removeLeague(String leagueId) {
        logger.info("removeLeague");
        try {
            League league = em.find(League.class, leagueId);
            em.remove(league);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public LeagueDetails getLeague(String leagueId) {
        logger.info("getLeague");
        LeagueDetails leagueDetails = null;

        try {
            League league = em.find(League.class, leagueId);
            leagueDetails = new LeagueDetails(league.getId(),
                    league.getName(),
                    league.getSport());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        return leagueDetails;
    }

    private List<PlayerDetails> copyPlayersToDetails(List<Player> players) {
        List<PlayerDetails> detailsList = new ArrayList<>();
        Iterator<Player> i = players.iterator();
        while (i.hasNext()) {
            Player player = (Player) i.next();
            PlayerDetails playerDetails = new PlayerDetails(player.getId(),
                    player.getName(),
                    player.getPosition(),
                    player.getSalary());
            detailsList.add(playerDetails);
        }
        return detailsList;
    }

    private List<MatchDetails> copyMatchesToDetails(List<Match> matches) {
        List<MatchDetails> detailsList = new ArrayList<>();
        Iterator<Match> i = matches.iterator();
        while (i.hasNext()) {
            Match match = (Match) i.next();
            MatchDetails matchDetails = new MatchDetails(match.getId(),
                    match.getHostPoints(),
                    match.getGuestPoints());
            detailsList.add(matchDetails);
        }

        return detailsList;
    }

    private List<TeamDetails> copyTeamsToDetails(List<Team> teams) {
        List<TeamDetails> detailsList = new ArrayList<>();
        Iterator<Team> i = teams.iterator();
        while (i.hasNext()) {
            Team team = (Team) i.next();
            TeamDetails teamDetails = new TeamDetails(team.getId(),
                    team.getName(),
                    team.getCity(),
                    team.getScore()
            );
            detailsList.add(teamDetails);
        }

        return detailsList;
    }

    @Override
    public void addScoreToTeam(String teamID, int points) {
        logger.info("addPointsToTeam");

        try {
            Team team = em.find(Team.class, points);
            int oldScore = team.getScore();
            int newScore = oldScore + points;
            team.setScore(newScore);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public void createMatchWithExistingTeams(MatchDetails matchDetails, String hostTeamID, String guestTeamID) {
        logger.info("createMatchWithExistingTeams");

        try {
            Team hostTeam = em.find(Team.class, hostTeamID);
            Team guestTeam = em.find(Team.class, guestTeamID);
            Match match = new Match(matchDetails.getId(), matchDetails.getHostPoints(), matchDetails.getGuestPoints());

            match.setHostTeam(hostTeam);
            match.setGuestTeam(guestTeam);

            hostTeam.setScore(hostTeam.getScore() + matchDetails.getHostPoints());
            guestTeam.setScore(guestTeam.getScore() + matchDetails.getGuestPoints());

            hostTeam.setMatchesPlayed(hostTeam.getMatchesPlayed() + 1);
            guestTeam.setMatchesPlayed(guestTeam.getMatchesPlayed() + 1);

            em.persist(match);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<MatchDetails> getAllMatches() {
        logger.info("getAllMatches");
        List<Match> matches = null;
        try {
            CriteriaQuery<Match> cq = cb.createQuery(Match.class);

            if (cq != null) {
                Root<Match> match = cq.from(Match.class);

                cq.select(match);
                TypedQuery<Match> tq = em.createQuery(cq);
                matches = tq.getResultList();
            }
            return copyMatchesToDetails(matches);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<TeamDetails> getAllTeams() {
        logger.info("getAllTeams");
        List<Team> teams = null;
        try {
            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            if (cq != null) {
                Root<Team> team = cq.from(Team.class);

                cq.select(team);
                TypedQuery<Team> tq = em.createQuery(cq);
                teams = tq.getResultList();
            }
            return copyTeamsToDetails(teams);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<TeamDetails> getAllTeamsOrderedByScore() {
        logger.info("getTeamsByScore");
        List<Team> teams = null;
        try {
            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            if (cq != null) {
                Root<Team> team = cq.from(Team.class);
                cq.orderBy(cb.desc(team.get(Team_.score)));
                TypedQuery<Team> tq = em.createQuery(cq);
                teams = tq.getResultList();
            }
            return copyTeamsToDetails(teams);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public List<TeamDetails> getTeamsInLeagueByScore(String leagueID) {
        logger.info("getTeamsInLeeagueByScore");
        List<Team> teams = null;
        try {
            CriteriaQuery<Team> cq = cb.createQuery(Team.class);
            if (cq != null) {
                Root<Team> team = cq.from(Team.class);
                Join<Team, League> league = team.join(Team_.league);

                cq.where(cb.equal(league.get(League_.id), leagueID));
                cq.orderBy(cb.desc(team.get("score")));

                TypedQuery<Team> tq = em.createQuery(cq);
                teams = tq.getResultList();
            }
            return copyTeamsToDetails(teams);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public MatchDetails getMatchByMatchID(int matchID) {
        logger.info("getMatchByMatchID");
        MatchDetails matchDetails = null;
        try {
            Match match = em.find(Match.class, matchID);
            matchDetails = new MatchDetails(match.getId(), match.getHostPoints(), match.getGuestPoints());
        } catch (Exception ex) {
            throw new EJBException(ex);
        }

        return matchDetails;

    }

    @Override
    public List<MatchDetails> getHostMatchesOfTeamByTeamID(String teamID) {
        logger.info("getHostMatchesOfTeamByTeamID");
        List<Match> matches = null;

        try {
            CriteriaQuery<Match> cq = cb.createQuery(Match.class);
            if (cq != null) {
                Root<Match> match = cq.from(Match.class);
                Join<Match, Team> team = match.join(Match_.hostTeam);

                cq.where(cb.equal(team.get(Team_.id), teamID));
                TypedQuery<Match> tq = em.createQuery(cq);
                matches = tq.getResultList();
            }
            return copyMatchesToDetails(matches);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }

    }

    @Override
    public List<MatchDetails> getGuestMatchesOfTeamByTeamID(String teamID) {
        logger.info("getGuestMatchesOfTeamByTeamID");
        List<Match> matches = null;

        try {
            CriteriaQuery<Match> cq = cb.createQuery(Match.class);
            if (cq != null) {
                Root<Match> match = cq.from(Match.class);
                Join<Match, Team> team = match.join(Match_.guestTeam);
                cq.where(cb.equal(team.get(Team_.id), teamID));

                TypedQuery<Match> tq = em.createQuery(cq);
                matches = tq.getResultList();
            }
            return copyMatchesToDetails(matches);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }

    @Override
    public TeamDetails getHostTeamOfMatch(int matchID) {
        logger.info("getHostTeamOfMatch");
        TeamDetails teamDetails;
        Team hostTeam = null;

        try {
            Match match = em.find(Match.class, matchID);
            hostTeam = match.getHostTeam();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        teamDetails = new TeamDetails(hostTeam.getId(), hostTeam.getName(), hostTeam.getCity());
        return teamDetails;
    }

    @Override
    public TeamDetails getGuestTeamOfMatch(int matchID) {
        logger.info("getGuestTeamOfMatch");
        TeamDetails teamDetails;
        Team guestTeam = null;

        try {
            Match match = em.find(Match.class, matchID);
            guestTeam = match.getGuestTeam();
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
        teamDetails = new TeamDetails(guestTeam.getId(), guestTeam.getName(), guestTeam.getCity());
        return teamDetails;
    }

    @Override
    public List<TeamDetails> getAllTeamsOfMatch(int matchID) {
        List<TeamDetails> teamDetails = new ArrayList<>();
        TeamDetails hostTeamDetails = getHostTeamOfMatch(matchID);
        TeamDetails guesTeamDetails = getGuestTeamOfMatch(matchID);

        teamDetails.add(hostTeamDetails);
        teamDetails.add(guesTeamDetails);
        return teamDetails;
    }

}
