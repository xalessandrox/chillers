package com.logicbyte.chillers.controller;

import com.logicbyte.chillers.model.HttpResponse;
import com.logicbyte.chillers.model.PlayerSave;
import com.logicbyte.chillers.service.GameService;
import com.logicbyte.chillers.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.logicbyte.chillers.util.Utils.getUri;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */

@RestController
@RequestMapping(path = "/players")
@RequiredArgsConstructor
@Slf4j
public class PlayerController {

    private final PlayerService playerService;
    private final GameService gameService;

    @GetMapping("/all")
    public ResponseEntity<HttpResponse> getPlayers() {
        return ResponseEntity
                .ok()
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .data(Map.of(
                                        "players", playerService.getAll(),
                                        "ongoingGames", gameService.getOngoingGames()))
                                .message("Retrieved Players")
                                .httpStatus(HttpStatus.OK)
                                .statusCode(HttpStatus.OK.value())
                                .build());
    }

    @PostMapping
    public ResponseEntity<HttpResponse> createPlayer(@RequestBody PlayerSave playerSave) {
        playerService.createPlayer(playerSave);
        return ResponseEntity
                .created(getUri())
                .body(
                        HttpResponse.builder()
                                .timeStamp(LocalDateTime.now().toString())
                                .message("Player created")
                                .httpStatus(HttpStatus.CREATED)
                                .statusCode(HttpStatus.CREATED.value())
                                .build());
    }

}
