/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.compare;

import java.math.BigDecimal;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class ComparitorTest {


    @Test
    public void testHasPositiveValue() {
        System.out.println("hasPositiveValue");
        BigDecimal value = BigDecimal.ZERO;
        Comparitor instance = new Comparitor();
        boolean expResult = false;
        boolean result = instance.hasPositiveValue(value);
        assertEquals(expResult, result);
    }

}
