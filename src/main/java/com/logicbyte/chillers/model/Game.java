package com.logicbyte.chillers.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.logicbyte.chillers.enums.GameFormat;
import com.logicbyte.chillers.enums.GameState;
import com.logicbyte.chillers.enums.Outcome;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Immutable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 04.12.2023
 */

@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Immutable
public class Game {

    Integer id;
    private List<Player> team1;
    private List<Player> team2;
    private GameFormat gameFormat;
    private GameState gameState;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt;
    private Outcome outcome;
    private byte numberOfPlayers;
    private Player mvp;
    private int teamWinner;

    public Game(GameFormat gameFormat, byte numberOfPlayers) {
        this.team1 = new ArrayList<>(numberOfPlayers);
        this.team2 = new ArrayList<>(numberOfPlayers);
        this.gameFormat = gameFormat;
        this.numberOfPlayers = numberOfPlayers;
    }
}
