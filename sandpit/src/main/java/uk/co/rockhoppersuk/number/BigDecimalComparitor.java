/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.number;

import java.math.BigDecimal;

/**
 *
 * @author mxbailey
 */
public class BigDecimalComparitor {
    
    /**
     * checks if the value is positive
     * @param value
     * @return
     */
    protected static boolean hasPositiveValue(BigDecimal value) {
        return value != null && BigDecimal.ZERO.compareTo(value) < 0;
    }
    
    
}
