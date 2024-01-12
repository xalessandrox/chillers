package com.logicbyte.chillers.model;

import com.logicbyte.chillers.enums.GameFormat;

import java.util.ArrayList;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */


public class GameFactory {
    public static Game getGame(GameFormat gameFormat) {
        switch (gameFormat) {
            case FORMAT_2V2 -> {
                return new Game(gameFormat, (byte) 2);
            }
            case FORMAT_3V3 -> {
                return new Game(gameFormat, (byte) 3);
            }
            case FORMAT_4V4 -> {
                return new Game(gameFormat, (byte) 4);
            }
            case FORMAT_5V5 -> {
                return new Game(gameFormat, (byte) 5);
            }
        }
        return null;
    }
}
