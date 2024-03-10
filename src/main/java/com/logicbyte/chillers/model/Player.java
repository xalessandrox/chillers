package com.logicbyte.chillers.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if(object == this) return true;
        if(!(object instanceof Player other)) return false;
        return
                Objects.equals(other.getNickname(), this.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNickname());
    }

}
