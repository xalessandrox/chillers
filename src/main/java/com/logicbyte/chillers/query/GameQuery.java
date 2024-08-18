package com.logicbyte.chillers.query;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */

public interface GameQuery {

    String INSERT_GAME_QUERY =
            "INSERT INTO games (created_at, game_format, number_of_players) VALUES (:createdAt, :gameFormat, :numberOfPlayers)";
    String INSERT_INTO_GAMES_PLAYERS =
            "INSERT INTO games_players (game_id, player_id, team) VALUES (:gameId, :playerId, :team);";
    String SELECT_ALL_GAMES =
            "SELECT * FROM games;";
    String SELECT_ALL_ONGOING_GAMES =
            "SELECT * FROM games WHERE game_state = '0'";
    String SELECT_GAME_BY_ID_QUERY =
            "SELECT * FROM games WHERE id = :gameId";
    String UPDATE_GAME_QUERY = """
            UPDATE games
            SET
            game_state = :gameState,
            outcome = :outcome,
            mvp = CASE WHEN :mvp <> 0 THEN :mvp END,
            finished_at = :finishedAt
            WHERE id = :gameId
            """;

}
