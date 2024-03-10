package com.logicbyte.chillers.service;

import com.logicbyte.chillers.model.Game;

import java.util.List;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 04.12.2023
 */


public interface GameService {
    Game createGame(Game game);
    Game saveGame(Game game);
    Game getGame(Integer id);
    List<Game> getGames();
    List<Game> getOngoingGames();

}
