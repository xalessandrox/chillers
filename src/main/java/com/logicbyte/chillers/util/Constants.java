package com.logicbyte.chillers.util;

import java.util.Arrays;
import java.util.List;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 11.01.2024
 */


public interface Constants {

    int WIN_POINTS = 50;
    int DRAW_POINTS = 20;
    int MVP_POINTS = 25;

    String STANDARD_RUNTIME_EXCEPTION_MSG =
            "An error occurred";

    /*  CORS POLICY */
    List<String> ALLOWED_ORIGINS = Arrays.asList(
            "http://localhost:4200", "http://172.17.0.1", "http://213.165.72.126", "http://www.chillersonline.rocks", "https://www.chillersonline.rocks");
    List<String> ALLOWED_HEADERS = Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept",
            "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Content-Length");
    List<String> EXPOSED_HEADERS = Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "Authorization",
            "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "File-Name", "Access-Control-Expose-Headers", "Content-Length");

    List<String> ALLOWED_METHODS = Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");
}
