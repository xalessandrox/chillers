package com.logicbyte.chillers.service.implementations;

import com.logicbyte.chillers.Utils;
import com.logicbyte.chillers.enums.Outcome;
import com.logicbyte.chillers.model.Game;
import com.logicbyte.chillers.model.Player;
import com.logicbyte.chillers.rowmapper.GameRowMapper;
import com.logicbyte.chillers.rowmapper.PlayerRowMapper;
import com.logicbyte.chillers.service.GameService;
import com.logicbyte.chillers.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.logicbyte.chillers.enums.GameState.FINISHED;
import static com.logicbyte.chillers.enums.GameState.STARTED;
import static com.logicbyte.chillers.enums.Outcome.SCRAP;
import static com.logicbyte.chillers.query.GameQuery.*;
import static com.logicbyte.chillers.query.PlayerQuery.*;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 04.12.2023
 */

@Service("GameServiceImpl")
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {
    private final NamedParameterJdbcTemplate jdbc;
    private final PlayerService playerService;

    @Override
    public Game createGame(Game game) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            SqlParameterSource parameterSource = getSqlParameterSourceForGame(game);
            jdbc.update(INSERT_GAME_QUERY, parameterSource, holder);
            game.setId((Long) Objects.requireNonNull(holder.getKeys()).get("id"));
            game.setGameState(STARTED);
            setGameAndPlayers(game.getTeam1(), game.getTeam2(), game.getId());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }
        return game;
    }

    @Override
    public Game saveGame(Game game) {
        game.setGameState(FINISHED);
        jdbc.update(SAVE_GAME_QUERY, Map.of(
                "gameId", game.getId(),
                "gameState", FINISHED.ordinal(),
                "outcome", game.getOutcome().ordinal(),
                "mvp", game.getMvp().getId()));
        if(game.getOutcome() != SCRAP) {
            playerService.updatePlayerPointsByGameId(game.getId(), game.getOutcome());
        }
        return game;
    }

    @Override
    public List<Game> getGames() {
        return jdbc.query(SELECT_ALL_GAMES, new GameRowMapper());
    }

    @Override
    public Game getGame(Long id) {
        try {
            Game game = jdbc.queryForObject(
                    SELECT_GAME_BY_ID_QUERY,
                    Map.of("gameId", id),
                    new GameRowMapper());
            if (game.getGameState() == FINISHED) {
                game.setMvp(playerService.getMvpPlayerByGameId(id));
                game.setOutcome(getOutcomeByGameId(id));
            }
            return game;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }

    }

    private void setGameAndPlayers(List<Player> team1, List<Player> team2, Long gameId) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            for (Player player : team1) {
                SqlParameterSource parameterSource = getSqlParameterSourceForGamesPlayers(gameId, player.getId(), '1');
                jdbc.update(INSERT_INTO_GAMES_PLAYERS, parameterSource, holder);
            }
            for (Player player : team2) {
                SqlParameterSource parameterSource = getSqlParameterSourceForGamesPlayers(gameId, player.getId(), '2');
                jdbc.update(INSERT_INTO_GAMES_PLAYERS, parameterSource, holder);
            }

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException("An error occurred. Please try again.");
        }

    }

    private SqlParameterSource getSqlParameterSourceForGamesPlayers(Long gameId, Long playerId, char team) {
        return new MapSqlParameterSource()
                .addValue("gameId", gameId)
                .addValue("playerId", playerId)
                .addValue("team", team);
    }

    private SqlParameterSource getSqlParameterSourceForGame(Game game) {

        return new MapSqlParameterSource()
                .addValue("gameFormat", game.getGameFormat().ordinal())
                .addValue("gameState", STARTED.ordinal());

    }

    private Outcome getOutcomeByGameId(Long id) {
        Integer outcomeAsKey = jdbc.queryForObject(
                "SELECT outcome FROM games WHERE id = :gameId",
                Map.of("gameId", id), Integer.class);
        return Utils.getOutcomeByOrdinal(outcomeAsKey);
    }

}
