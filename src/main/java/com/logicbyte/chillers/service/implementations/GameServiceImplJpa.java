package com.logicbyte.chillers.service.implementations;

import com.logicbyte.chillers.model.Game;
import com.logicbyte.chillers.service.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 07.12.2023
 */

@Service("GameServiceImplJpa")
@RequiredArgsConstructor
public class GameServiceImplJpa implements GameService {

    @Override
    public Game createGame(Game game) {

        return null;
    }

    @Override
    public Game saveGame(Game game) {
        return null;
    }

    @Override
    public List<Game> getGames() {
        return null;
    }

    @Override
    public Game getGame(Long id) {
        return null;
    }
}
