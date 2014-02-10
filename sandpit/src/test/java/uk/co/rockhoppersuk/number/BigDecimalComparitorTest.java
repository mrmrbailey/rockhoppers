/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.number;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author mxbailey
 */
public class BigDecimalComparitorTest {


    /**
     * Test of hasPositiveValue method, of class BigDecimalComparitor.
     */
    @Test
    public void testHasPositiveValue() {
        System.out.println("hasPositiveValue");
        BigDecimal value = BigDecimal.ZERO;
        boolean expResult = true;
        boolean result = BigDecimalComparitor.hasPositiveValue(value);
        assertEquals(expResult, result);
        
        value = BigDecimal.ONE;
        result = BigDecimalComparitor.hasPositiveValue(value);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
