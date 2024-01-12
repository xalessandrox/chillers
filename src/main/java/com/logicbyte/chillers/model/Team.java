package com.logicbyte.chillers.model;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Alessandro Formica
 * @version 1.0
 * @since 05.12.2023
 */

@Getter
@Setter
@RequiredArgsConstructor
public class Team {

    List<Player> players;

}
