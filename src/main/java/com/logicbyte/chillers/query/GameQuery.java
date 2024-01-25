package com.logicbyte.chillers.query;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */

public interface GameQuery {

    String INSERT_GAME_QUERY =
            "INSERT INTO games (gameFormat, gameState) VALUES (:gameFormat, :gameState)";
    String INSERT_INTO_GAMES_PLAYERS =
            "INSERT INTO games_players (game_id, player_id, team) VALUES (:gameId, :playerId, :team);";
    String SELECT_ALL_GAMES =
            "SELECT * FROM games;";
    String SELECT_GAME_BY_ID_QUERY =
            "SELECT * FROM games WHERE id = :gameId";
    String SAVE_GAME_QUERY = """
            UPDATE games
            SET
            gameState = :gameState,
            outcome = :outcome,
            mvp = CASE WHEN :mvp <> 0 THEN :mvp END,
            finished_at = :finishedAt
            WHERE id = :gameId
            """;

}
