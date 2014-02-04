/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.number;

import java.math.BigDecimal;
import java.math.MathContext;
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
public class BigDecimalMathContextTest {

    /**
     * Test of getRoundedNumber method, of class BigDecimalMathContext.
     */
    //@Test
    public void testGetRoundedNumberOld() {
        System.out.println("getRoundedNumber");
        double number = 123.000005;
        int precision = 2;
        BigDecimalMathContext instance = new BigDecimalMathContext();
        BigDecimal expResult = new BigDecimal(120);
        BigDecimal result = instance.getRoundedNumber(number, precision);

        System.out.println(result);
        System.out.println(result.doubleValue());
        assertTrue(expResult.doubleValue()==result.doubleValue());
    }
    
    @Test
    public void testGetRoundedNumber() {
        BigDecimal origPrizeFund = new BigDecimal(1234.56, new MathContext(4));
        
        BigDecimal copiedPrizeFund = new BigDecimal(origPrizeFund.doubleValue(), new MathContext(2));
        System.out.println(origPrizeFund);
        System.out.println(copiedPrizeFund);
        System.out.println(copiedPrizeFund.doubleValue());
//        assertTrue(origPrizeFund.doubleValue()==copiedPrizeFund.doubleValue());
        
        System.out.println(origPrizeFund.pow(2).doubleValue());
        System.out.println(copiedPrizeFund.pow(2).doubleValue());        
    }
}
