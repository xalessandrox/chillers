package com.logicbyte.chillers.rowmapper;

import com.logicbyte.chillers.enums.GameFormat;
import com.logicbyte.chillers.enums.GameState;
import com.logicbyte.chillers.model.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import static com.logicbyte.chillers.util.Utils.setUtcToSystemDefaultZone;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */

public class GameRowMapper implements RowMapper<Game> {
    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDateTime synchronizedCreatedAt = setUtcToSystemDefaultZone(rs.getTimestamp("created_at").toLocalDateTime());
        LocalDateTime synchronizedFinishedAt = rs.getTimestamp("finished_at") == null ? null : setUtcToSystemDefaultZone(rs.getTimestamp("finished_at").toLocalDateTime());
        return Game.builder()
                .id(rs.getInt("id"))
                .gameFormat(GameFormat.values()[(rs.getInt("game_format"))])
                .gameState(GameState.values()[(rs.getInt("game_state"))])
                .createdAt(synchronizedCreatedAt)
                .finishedAt(synchronizedFinishedAt)
                .numberOfPlayers((byte) rs.getByte("number_of_players"))
                .build();
    }
}
