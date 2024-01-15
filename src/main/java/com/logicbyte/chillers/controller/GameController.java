package com.logicbyte.chillers.controller;

import com.logicbyte.chillers.model.Game;
import com.logicbyte.chillers.model.HttpResponse;
import com.logicbyte.chillers.service.GameService;
import com.logicbyte.chillers.service.PlayerService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

import static com.logicbyte.chillers.Utils.getUri;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */

@RestController
@RequestMapping(path = "/games")
@Slf4j
public class GameController {

    private final GameService gameService;
    private final PlayerService playerService;

    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @PostMapping("/new")
    public ResponseEntity<HttpResponse> createGame(@RequestBody @Valid Game game) {
        return ResponseEntity
                .created(getUri())
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("game", gameService.createGame(game)))
                                .message("Game successfully created")
                                .httpStatus(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .build());
    }

    @PatchMapping("/save")
    public ResponseEntity<HttpResponse> saveGame(@RequestBody @Valid Game game) {
        return ResponseEntity
                .ok()
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of(
                                        "game", gameService.saveGame(game),
                                        "players", playerService.getPlayersOfTheGame(game.getId())
                                ))
                                .message("Game successfully saved")
                                .httpStatus(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
    }

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getGames() {
        return ResponseEntity
                .ok()
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of("games", gameService.getGames()))
                                .message("Retrieved Games")
                                .httpStatus(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpResponse> getGame(@PathVariable Integer id) {
        return ResponseEntity
                .ok()
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of(
                                        "game", gameService.getGame(id),
                                        "players", playerService.getPlayersOfTheGame(id))
                                )
                                .message("Retrieved Games")
                                .httpStatus(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
    }

}
