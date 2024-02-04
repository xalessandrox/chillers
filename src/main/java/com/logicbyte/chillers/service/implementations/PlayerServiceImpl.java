package com.logicbyte.chillers.service.implementations;

import com.logicbyte.chillers.Utils;
import com.logicbyte.chillers.enums.Outcome;
import com.logicbyte.chillers.model.Player;
import com.logicbyte.chillers.rowmapper.PlayerRowMapper;
import com.logicbyte.chillers.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.logicbyte.chillers.enums.Outcome.DRAW;
import static com.logicbyte.chillers.query.PlayerQuery.*;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 04.12.2023
 */


@RequiredArgsConstructor
@Service
@Slf4j
public class PlayerServiceImpl implements PlayerService {
    private final NamedParameterJdbcTemplate jdbc;


    @Override
    public List<Player> getAll() {
        return jdbc.query(SELECT_ALL_PLAYERS_QUERY, new PlayerRowMapper());
    }

    @Override
    public List<Map<String, List<Player>>> getPlayersOfTheGame(Integer gameId) {
        List<Player> team1 = jdbc.query(SELECT_PLAYERS_BY_GAME_ID_QUERY, getSqlParameterSource(gameId, '1'), new PlayerRowMapper());
        List<Player> team2 = jdbc.query(SELECT_PLAYERS_BY_GAME_ID_QUERY, getSqlParameterSource(gameId, '2'), new PlayerRowMapper());

        Map<String, List<Player>> team1asMap = Map.of("team1", team1);
        Map<String, List<Player>> team2asMap = Map.of("team2", team2);
        return Arrays.asList(team1asMap, team2asMap);
    }

    @Override
    public Player findPlayerById(Integer id) {
        try {
            return jdbc.queryForObject("SELECT * FROM players WHERE id = :playerId", Map.of("playerId", id), new PlayerRowMapper());
        } catch(Exception ex) {
            log.error("No player found with this id: {}{}{}", id, System.lineSeparator(), ex.getMessage());
            return new Player();
        }
    }


    @Override
    public List<Integer> findPlayersByGameIdAndTeamFromGamesPlayers(int gameId, char winnerTeam) {
        return null;
    }

    @Override
    public Player getMvpPlayerByGameId(Integer id) {
        return jdbc.queryForObject(
                SELECT_MVP_PLAYER_BY_GAME_ID_QUERY,
                Map.of("gameId", id),
                new PlayerRowMapper());
    }

    @Override
    public void updatePlayersPointsByGameIdAndOutcome(Integer id, Outcome outcome) {
        if(outcome == DRAW) {
            List<Integer> allPlayers = getPlayersFkById(id);
            allPlayers.forEach((playerId) -> {
                updatePlayerPointsByPlayerId(playerId, 25);
            });
        } else {
            int winners = outcome.ordinal();
            int losers = winners == 0 ? 1 : 0;
            List<Integer> winningPlayers = getPlayersFkByIdByTeam(id, winners + 1);
            List<Integer> losingPlayers =  getPlayersFkByIdByTeam(id, losers + 1);

            winningPlayers.forEach((playerId) -> {
                updatePlayerPointsByPlayerId(playerId, 50);
            });
            losingPlayers.forEach((playerId) -> {
                updatePlayerPointsByPlayerId(playerId, -15);
            });
        }
    }

    @Override
    public void updatePlayerPointsByPlayerId(int playerId, int points) {
        jdbc.update(UPDATE_PLAYER_POINTS_BY_ID_QUERY, Map.of("playerId", playerId, "points", points));
    }

    List<Integer> getPlayersFkByIdByTeam(Integer id, int team) {
        return jdbc.queryForList(SELECT_PLAYERS_FK_BY_GAME_ID_AND_TEAM, Map.of("gameId", id, "team", team), Integer.class);
    }

    List<Integer> getPlayersFkById(Integer id) {
        return jdbc.queryForList(SELECT_PLAYERS_FK_BY_GAME_ID, Map.of("gameId", id), Integer.class);
    }

    private SqlParameterSource getSqlParameterSource(Integer gameId, char team) {
        return new MapSqlParameterSource()
                .addValue("gameId", gameId)
                .addValue("team", team);
    }

}
