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

import java.util.List;
import jakarta.tutorial.roster.util.LeagueDetails;
import jakarta.tutorial.roster.util.MatchDetails;
import jakarta.tutorial.roster.util.PlayerDetails;
import jakarta.tutorial.roster.util.TeamDetails;
import javax.ejb.Remote;

@Remote
public interface Request {

    void addPlayer(String playerId, String teamId);

    void createLeague(LeagueDetails leagueDetails);

    void createPlayer(String id, String name, String position, double salary);

    void createTeamInLeague(TeamDetails teamDetails, String leagueId);

    void dropPlayer(String playerId, String teamId);

    List<MatchDetails> getAllMatches();

    List<PlayerDetails> getAllPlayers();

    LeagueDetails getLeague(String leagueId);

    List<LeagueDetails> getLeaguesOfPlayer(String playerId);

    List<TeamDetails> getTeamsInLeagueByScore(String leagueID);

    PlayerDetails getPlayer(String playerId);

    List<PlayerDetails> getPlayersByCity(String city);

    List<PlayerDetails> getPlayersByHigherSalary(String name);

    List<PlayerDetails> getPlayersByLeagueId(String leagueId);

    List<PlayerDetails> getPlayersByPosition(String position);

    List<PlayerDetails> getPlayersByPositionAndName(String position, String name);

    List<PlayerDetails> getPlayersBySalaryRange(double low, double high);

    List<PlayerDetails> getPlayersBySport(String sport);

    List<PlayerDetails> getPlayersNotOnTeam();

    List<PlayerDetails> getPlayersOfTeam(String teamId);

    List<String> getSportsOfPlayer(String playerId);

    TeamDetails getTeam(String teamId);

    List<TeamDetails> getTeamsOfLeague(String leagueId);

    void removeLeague(String leagueId);

    void removePlayer(String playerId);

    void removeTeam(String teamId);

    void createMatchWithExistingTeams(MatchDetails matchDetails, String hostTeam, String guestTeam);

    void addScoreToTeam(String teamID, int score);

    List<TeamDetails> getAllTeams();

    List<TeamDetails> getAllTeamsOrderedByScore();

    MatchDetails getMatchByMatchID(int matchID);
   
    List<MatchDetails> getHostMatchesOfTeamByTeamID(String teamID);
    
    List<MatchDetails> getGuestMatchesOfTeamByTeamID(String teamID);
    
    TeamDetails getHostTeamOfMatch(int matchID);
    
    TeamDetails getGuestTeamOfMatch(int matchID);
    
    List<TeamDetails> getAllTeamsOfMatch(int matchID);

}
