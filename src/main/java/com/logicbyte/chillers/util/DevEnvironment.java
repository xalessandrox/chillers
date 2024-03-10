package com.logicbyte.chillers.util;

import com.logicbyte.chillers.enums.GameFormat;
import com.logicbyte.chillers.enums.Outcome;
import com.logicbyte.chillers.model.Game;
import com.logicbyte.chillers.service.GameService;
import com.logicbyte.chillers.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 18.02.2024
 */

@Slf4j
@Profile("dev")
@Component
public class DevEnvironment {

    private final PlayerService playerService;
    private final GameService gameService;


    public DevEnvironment(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @Bean
    CommandLineRunner lineRunner() {
        return args -> {
            Game game = new Game();
            game.setGameFormat(GameFormat.FORMAT_2V2);
            game.setNumberOfPlayers((byte) 2);
            game.setTeam1(Arrays.asList(
                    playerService.findPlayerById(1),
                    playerService.findPlayerById(2)
            ));
            game.setTeam2(Arrays.asList(
                    playerService.findPlayerById(3),
                    playerService.findPlayerById(4)
            ));
            gameService.createGame(game);
            log.info("Created game:{}{}", System.lineSeparator(), game);

            game.setOutcome(Outcome.DRAW);
            game.setMvp(playerService.findPlayerById(2));
            gameService.saveGame(game);
            log.info("Saved finished game:{}{}", System.lineSeparator(), game);

            Game game1 = new Game();
            game1.setGameFormat(GameFormat.FORMAT_2V2);
            game1.setNumberOfPlayers((byte) 2);
            game1.setTeam1(Arrays.asList(
                    playerService.findPlayerById(2),
                    playerService.findPlayerById(3)
            ));
            game1.setTeam2(Arrays.asList(
                    playerService.findPlayerById(1),
                    playerService.findPlayerById(4)
            ));
            gameService.createGame(game1);
            log.info("Created game:{}{}", System.lineSeparator(), game1);
            game1.setOutcome(Outcome.TEAM1_WINS);
            game1.setMvp(playerService.findPlayerById(3));
            gameService.saveGame(game1);
            log.info("Saved finished game:{}{}", System.lineSeparator(), game1);

            Game game2 = new Game();
            game2.setGameFormat(GameFormat.FORMAT_2V2);
            game2.setNumberOfPlayers((byte) 2);
            game2.setTeam1(Arrays.asList(
                    playerService.findPlayerById(2),
                    playerService.findPlayerById(3)
            ));
            game2.setTeam2(Arrays.asList(
                    playerService.findPlayerById(1),
                    playerService.findPlayerById(4)
            ));
            gameService.createGame(game2);
            log.info("Created game:{}{}", System.lineSeparator(), game1);

        };
    }

}
