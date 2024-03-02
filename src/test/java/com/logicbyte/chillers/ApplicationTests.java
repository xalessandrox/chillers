package com.logicbyte.chillers;

import com.logicbyte.chillers.enums.GameFormat;
import com.logicbyte.chillers.model.Game;
import com.logicbyte.chillers.model.GameFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {

        Game game = GameFactory.getGame(GameFormat.FORMAT_2V2);


    }

}
