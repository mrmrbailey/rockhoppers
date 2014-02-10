/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.format;

import java.math.BigDecimal;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author mxbailey
 */
public class NumberFormaterTest {
    
    /**
     * Test of main method, of class NumberFormater.
     */
    @Test
    public void testGetNumber() {
        NumberFormater formatter = new NumberFormater();

        BigDecimal bd = BigDecimal.ONE;       
        assertEquals("1.00", formatter.getNumber(bd));
        
        bd = new BigDecimal(100000);
        assertEquals("100,000", formatter.getNumber(bd));

        bd = new BigDecimal(999);
        assertEquals("999.00", formatter.getNumber(bd));

        bd = new BigDecimal(1000);
        assertEquals("1,000", formatter.getNumber(bd));

        bd = new BigDecimal(1001);
        assertEquals("1,001", formatter.getNumber(bd));
        
    }
}
