package com.logicbyte.chillers.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 04.12.2023
 */

@Getter
@Setter
@ToString
@NoArgsConstructor
@SuperBuilder
@AllArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Player {
    private Integer id;
    private String nickname;
    private String image_url;
    private int playerPoints;
//    @Transient
//    private char team;

}
