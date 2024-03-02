package com.logicbyte.chillers.util;

import com.logicbyte.chillers.enums.Outcome;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */


public class Utils {

    public static URI getUri() {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/get/<userId>").toUriString());
    }

    public static Outcome getOutcomeByOrdinal(int outcomeOrdinal) {
        return Arrays.stream(Outcome.values())
                .filter(o -> o.ordinal() == outcomeOrdinal)
                .findFirst().get();
    }

    public static LocalDateTime setUtcToSystemDefaultZone(LocalDateTime dateTimeToSynchronize) {
        if (dateTimeToSynchronize == null) {
            System.out.println("There's no time for us");
            return null;
        }
        ZonedDateTime zdt = ZonedDateTime.of(dateTimeToSynchronize, ZoneId.of("UTC"));
        zdt = zdt.withZoneSameInstant(ZoneId.systemDefault());
        return zdt.toLocalDateTime();
    }

}
