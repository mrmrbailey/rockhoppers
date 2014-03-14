/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.compare;

import java.math.BigDecimal;

/**
 *
 * @author mxbailey
 */
public class Comparitor {

    public boolean hasPositiveValue(BigDecimal value) {
        return value != null && BigDecimal.ZERO.compareTo(value) < 0;
    }

}
