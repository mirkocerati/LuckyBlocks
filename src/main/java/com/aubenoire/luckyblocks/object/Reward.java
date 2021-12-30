package com.aubenoire.luckyblocks.object;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Reward {

    /**
     * List of commands as String
     */
    @Getter
    private List<String> commands;

    /**
     * Message to be sent
     */
    @Getter
    private String message;

    /**
     * Coins to be given
     */
    @Getter
    private float coins;

}
