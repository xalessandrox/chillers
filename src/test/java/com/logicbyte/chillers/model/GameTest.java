package com.logicbyte.chillers.model;

import com.logicbyte.chillers.enums.GameFormat;
import com.logicbyte.chillers.enums.GameState;
import com.logicbyte.chillers.service.GameService;
import com.logicbyte.chillers.service.PlayerService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GameTest {

    @Autowired
    GameService gameService;
    @Autowired
    PlayerService playerService;

    List<Player> team1 = new ArrayList<>();
    List<Player> team2 = new ArrayList<>();

    Game game = new Game();

    @BeforeAll
    void setUp() {

        team1.add(playerService.findPlayerById(1));
        team1.add(playerService.findPlayerById(2));
        team2.add(playerService.findPlayerById(3));
        team2.add(playerService.findPlayerById(4));

        GameFormat gameFormat = GameFormat.FORMAT_2V2;
        game.setGameFormat(gameFormat);
        game.setTeam1(team1);
        game.setTeam2(team2);

        gameService.createGame(game);

    }

    @Test
    @Order(1)
    void createdGameShouldExist() {
        System.out.println("one");
        List<Game> gamesList = gameService.getGames();
        assertEquals(1, gamesList.size());
    }

    @Test
    @Order(2)
    void createdGameShouldStartWithGameStateSTARTED() {
        System.out.println("two");
        Game persistedGame = gameService.getGame(1);
        assertEquals(GameState.STARTED, persistedGame.getGameState());
    }

    @Test
    @Order(3)
    void createdGameShouldHaveIdAssigned() {
        System.out.println("three");
        assertEquals(1, game.getId());
    }

}