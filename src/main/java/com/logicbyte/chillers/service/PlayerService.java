package com.logicbyte.chillers.service;

import com.logicbyte.chillers.enums.Outcome;
import com.logicbyte.chillers.model.Player;
import com.logicbyte.chillers.model.PlayerSave;

import java.util.List;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 04.12.2023
 */


public interface PlayerService {

    List<Player> getAll();
    List<Player> getTeam1PlayersOfTheGame(Integer gameId);
    List<Player> getTeam2PlayersOfTheGame(Integer gameId);
    Player findPlayerById(Integer id);
    List<Integer> findPlayersByGameIdAndTeamFromGamesPlayers(int gameId, char winnerTeam);
    Player getMvpPlayerByGameId(Integer id);
    void updatePlayersPointsByGameIdAndOutcome(Integer id, Outcome outcome);
    void updatePlayerPointsByPlayerId(int playerId, int points);
    void createPlayer(PlayerSave playerSave);
}
