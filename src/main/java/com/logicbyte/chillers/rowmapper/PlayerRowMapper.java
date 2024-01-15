package com.logicbyte.chillers.rowmapper;

import com.logicbyte.chillers.model.Player;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */


public class PlayerRowMapper implements RowMapper<Player> {

    @Override
    public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
       return Player.builder()
               .id(rs.getInt("id"))
               .nickname(rs.getString("nickname"))
               .image_url(rs.getString("image_url"))
               .playerPoints(rs.getInt("player_points"))
               .build();
    }
}
