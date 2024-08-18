package com.logicbyte.chillers.query;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */


public interface PlayerQuery {

    String SELECT_ALL_PLAYERS_QUERY =
            " SELECT * FROM players;";
    String SELECT_PLAYERS_BY_GAME_ID_QUERY = """
            SELECT p.id, p.nickname, p.image_url, p.player_points FROM players p
            JOIN games_players gp on p.id = gp.player_id
            WHERE game_id = :gameId AND team = :team;
            """;
    String SELECT_PLAYER_BY_ID =
            "SELECT * FROM players WHERE id = :playerId";
    String SELECT_MVP_PLAYER_BY_GAME_ID_QUERY = """
            SELECT * FROM players WHERE id =
            (SELECT mvp FROM games WHERE id = :gameId);
            """;
    String SELECT_PLAYERS_FK_BY_GAME_ID_AND_TEAM = """
            SELECT player_id FROM games_players
            WHERE game_id = :gameId AND team = CAST(:team AS CHAR);
            """;
    String SELECT_PLAYERS_FK_BY_GAME_ID =
            "SELECT player_id FROM games_players WHERE game_id = :gameId;";
    String UPDATE_PLAYER_POINTS_BY_ID_QUERY = """
            UPDATE players SET player_points =
            (SELECT player_points FROM players WHERE id = :playerId) + :points
            WHERE id = :playerId
            """;
    String INSERT_PLAYER_QUERY = " INSERT INTO players (nickname, player_points) VALUES (:nickname, :points)";
    String SELECT_PLAYER_BY_NICKNAME = " SELECT * FROM players WHERE nickname = :nickname ";
}
