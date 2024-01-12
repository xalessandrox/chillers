package com.logicbyte.chillers.service;

import com.logicbyte.chillers.enums.Outcome;
import com.logicbyte.chillers.model.Player;

import java.util.List;
import java.util.Map;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 04.12.2023
 */


public interface PlayerService {

    List<Player> getAll();
    List<Map<String, List<Player>>> getPlayersOfTheGame(Long gameId);
    Player findPlayerById(Long id);
    List<Integer> findPlayersByGameIdAndTeamFromGamesPlayers(int gameId, char winnerTeam);
    Player getMvpPlayerByGameId(Long id);
    void updatePlayerPointsByGameId(Long id, Outcome outcome);

}
