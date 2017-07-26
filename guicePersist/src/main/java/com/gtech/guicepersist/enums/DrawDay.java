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
 * The draw day selections.
 */
public enum DrawDay {

    /**
     * Tuesday only draw.
     */
    TUESDAY("Tue"),
    /**
     * Tuesday and Friday draws.
     */
    TUESDAY_FRIDAY("Tue Fri"),
    /**
     * Wednesday only draw.
     */
    WEDNESDAY("Wed"),
    /**
     * Wednesday and Friday draws.
     */
    WEDNESDAY_FRIDAY("Wed Fri"),
    /**
     * Wednesday, Friday and Saturday draws.
     */
    WEDNESDAY_FRIDAY_SATURDAY("Wed Fri Sat"),
    /**
     * Wednesday and Saturday draws.
     */
    WEDNESDAY_SATURDAY("Wed Sat"),
    /**
     * Friday only draws.
     */
    FRIDAY("Fri"),
    /**
     * Friday and Saturday draws.
     */
    FRIDAY_SATURDAY("Fri Sat"),
    /**
     * Saturday only draws.
     */
    SATURDAY("Sat");

    private static final Logger logger = Logger.getLogger(DrawDay.class.getName());

    private final String name;

    DrawDay(final String name) {
        this.name = name;
    }

    /**
     * Gets the name for the DrawDay.
     * <p>
     * @return the name for the DrawDay.
     */
    public String getName() {
        return name;
    }

    /**
     * For a given name with return the draw day.
     * <p>
     * @param drawDay the name of the draw day requested
     * @return the DrawDay representing that name.
     */
    public static DrawDay getDrawDay(final String drawDay) {
        logger.log(Level.FINEST, "Looking for drawDay {0}", drawDay);
        if (drawDay != null) {
            for (DrawDay day : DrawDay.values()) {
                if (drawDay.equalsIgnoreCase(day.getName())) {
                    return day;
                }
            }
        }
        String errorMessage = "No DrawDay found with the value :" + drawDay;
        logger.log(Level.SEVERE, errorMessage);
        throw new IllegalArgumentException(errorMessage);
    }
}
