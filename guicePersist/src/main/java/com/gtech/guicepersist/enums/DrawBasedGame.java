/*
 *  This document set is the property of GTECH UK, Watford,
 *  and contains confidential and trade secret information.
 *  It cannot be transferred from the custody or control of GTECH except as
 *  authorized in writing by an officer of GTECH. Neither this item nor
 *  the information it contains can be used, transferred, reproduced, published,
 *  or disclosed, in whole or in part, directly or indirectly, except as
 *  expressly authorized by an officer of GTECH, pursuant to written agreement.
 *
 *  Copyright 2014 GTECH UK. All Rights Reserved.
 */
package com.gtech.guicepersist.enums;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Enum of the games in the system.
 */
public enum DrawBasedGame {

    /**
     * The Lotto game.
     */
    LOTTO("lotto"),
    /**
     * The EuroMillions game.
     */
    EUROMILLIONS("euromillions"),
    /**
     * The Thunderball game.
     */
    THUNDERBALL("thunderball"),
    /**
     * The Lotto Hotpicks game.
     */
    LOTTO_HOTPICKS("lotto hotpicks");

    private static final Logger logger = Logger.getLogger(DrawBasedGame.class.getName());
    private final String name;

    private DrawBasedGame(final String name) {
        this.name = name;
    }

    /**
     * gets the Name of the game for the Enum.
     * <p>
     * @return the name of the screen.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Draw Based Game that the name represents.
     * @param name the name of the DBG.
     * @return the Draw Based Game that the name represents.
     */
    public static DrawBasedGame getGame(final String name) {
        if (name != null) {
            for (DrawBasedGame button : DrawBasedGame.values()) {
                if (name.equalsIgnoreCase(button.getName())) {
                    return button;
                }
            }
        }
        String errorMessage = "No Game found with the name :" + name;
        logger.log(Level.SEVERE, errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}
