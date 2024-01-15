package com.logicbyte.chillers.rowmapper;

import com.logicbyte.chillers.enums.GameFormat;
import com.logicbyte.chillers.enums.GameState;
import com.logicbyte.chillers.model.Game;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */

public class GameRowMapper implements RowMapper<Game> {
    @Override
    public Game mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Game.builder()
                .id(rs.getInt("id"))
                .gameFormat(GameFormat.values()[(rs.getInt("gameFormat"))])
                .gameState(GameState.values()[(rs.getInt("gameState"))])
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .finishedAt(rs.getTimestamp("finished_at") == null ? null : rs.getTimestamp("finished_at").toLocalDateTime())
                .build();
    }
}
