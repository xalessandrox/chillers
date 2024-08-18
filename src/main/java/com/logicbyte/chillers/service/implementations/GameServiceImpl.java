package com.logicbyte.chillers.service.implementations;

import com.logicbyte.chillers.enums.Outcome;
import com.logicbyte.chillers.model.Game;
import com.logicbyte.chillers.model.Player;
import com.logicbyte.chillers.rowmapper.GameRowMapper;
import com.logicbyte.chillers.service.GameService;
import com.logicbyte.chillers.service.PlayerService;
import com.logicbyte.chillers.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

import static com.logicbyte.chillers.enums.GameState.FINISHED;
import static com.logicbyte.chillers.enums.Outcome.SCRAP;
import static com.logicbyte.chillers.query.GameQuery.*;
import static com.logicbyte.chillers.util.Constants.STANDARD_RUNTIME_EXCEPTION_MSG;
import static com.logicbyte.chillers.util.Utils.setUtcToSystemDefaultZone;

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
            game.setNumberOfPlayers((byte) (game.getTeam1().size()));
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource parameterSource = getSqlParameterSourceForGame(game);
            int res = jdbc.update(INSERT_GAME_QUERY, parameterSource, keyHolder);
            game.setId(Integer.parseInt(keyHolder.getKeys().get("id").toString()));
            setGameAndPlayers(game.getTeam1(), game.getTeam2(), game.getId());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(STANDARD_RUNTIME_EXCEPTION_MSG);
        }
        return game;
    }

    @Override
    public Game saveGame(Game game) {
        log.info("Saving game {}", game);
        try {
            game.setGameState(FINISHED);
            // Update game
            // TODO: solve mvp = 0 causing Exception
            LocalDateTime finishedAtToPersist = LocalDateTime.now(Clock.system(ZoneId.of("UTC")));
            jdbc.update(UPDATE_GAME_QUERY, Map.of(
                    "gameId", game.getId(),
                    "gameState", FINISHED.ordinal(),
                    "outcome", game.getOutcome().ordinal(),
                    "mvp", game.getMvp() != null ? game.getMvp().getId() : 0,
                    "finishedAt", finishedAtToPersist)
            );
            game.setFinishedAt(setUtcToSystemDefaultZone(finishedAtToPersist));
            Outcome outcome = game.getOutcome();
            if (outcome != SCRAP) {
                playerService.updatePlayersPointsByGameIdAndOutcome(game.getId(), outcome);
                if(game.getMvp() != null) {
                    // Here are assigned the points to the MVP \\
                    playerService.updatePlayerPointsByPlayerId(game.getMvp().getId(), 35);
                    game.setMvp(playerService.findPlayerById(game.getMvp().getId()));
                }
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(STANDARD_RUNTIME_EXCEPTION_MSG);
        }
        return game;
    }

    @Override
    public List<Game> getGames() {
        List<Game> games = jdbc.query(SELECT_ALL_GAMES, new GameRowMapper());
        games.forEach(this::setTeams);
        return games;
    }

    @Override
    public List<Game> getOngoingGames() {
        List<Game> games = jdbc.query(SELECT_ALL_ONGOING_GAMES, new GameRowMapper());
        games.forEach(this::setTeams);
        return games;
    }

    @Override
    public Game getGame(Integer id) {
        try {
            Game game = jdbc.queryForObject(
                    SELECT_GAME_BY_ID_QUERY,
                    Map.of("gameId", id),
                    new GameRowMapper());
            if (game.getGameState() == FINISHED) {
                game.setOutcome(getOutcomeByGameId(id));
                if (game.getOutcome() != SCRAP)
                    game.setMvp(playerService.getMvpPlayerByGameId(id));
            }
//            setTeams(game);
            return game;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new RuntimeException(STANDARD_RUNTIME_EXCEPTION_MSG);
        }
    }

    private void setTeams(Game game) {
        game.setTeam1(playerService.getTeam1PlayersOfTheGame(game.getId()));
        game.setTeam2(playerService.getTeam2PlayersOfTheGame(game.getId()));
    }

    private void setGameAndPlayers(List<Player> team1, List<Player> team2, Integer gameId) {
        try {
            KeyHolder holder = new GeneratedKeyHolder();
            int index = 0;
            for (Player player : team1) {
                SqlParameterSource parameterSource = getSqlParameterSourceForGamesPlayers(gameId, player.getId(), '1');
                jdbc.update(INSERT_INTO_GAMES_PLAYERS, parameterSource, holder);
                Player p = playerService.findPlayerById(player.getId());
                team1.set(index++, p);
            }
            index = 0;
            for (Player player : team2) {
                SqlParameterSource parameterSource = getSqlParameterSourceForGamesPlayers(gameId, player.getId(), '2');
                jdbc.update(INSERT_INTO_GAMES_PLAYERS, parameterSource, holder);
                Player p = playerService.findPlayerById(player.getId());
                team2.set(index++, p);
            }

        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw new RuntimeException(STANDARD_RUNTIME_EXCEPTION_MSG);
        }

    }

    private SqlParameterSource getSqlParameterSourceForGamesPlayers(Integer gameId, Integer playerId, char team) {
        return new MapSqlParameterSource()
                .addValue("gameId", gameId)
                .addValue("playerId", playerId)
                .addValue("team", team);
    }

    private SqlParameterSource getSqlParameterSourceForGame(Game game) {

        return new MapSqlParameterSource()
                .addValue("createdAt", LocalDateTime.now(ZoneOffset.UTC))
                .addValue("numberOfPlayers", game.getNumberOfPlayers())
                .addValue("gameFormat", game.getGameFormat().ordinal());

    }

    private Outcome getOutcomeByGameId(Integer id) {
        Integer outcomeAsKey = null;
        try {
            outcomeAsKey = jdbc.queryForObject(
                    "SELECT outcome FROM games WHERE id = :gameId",
                    Map.of("gameId", id), Integer.class);
        } catch (Exception ex) {
            log.error("Could not get outcome by game id {}{}", System.lineSeparator(), ex.getMessage());
            throw new RuntimeException(STANDARD_RUNTIME_EXCEPTION_MSG);
        }

        return Utils.getOutcomeByOrdinal(outcomeAsKey);
    }

}
