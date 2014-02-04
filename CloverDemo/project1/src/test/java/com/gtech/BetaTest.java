/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class BetaTest {

    /**
     * Test of getC method, of class Beta.
     */
    @Test
    public void testGetC() {
        System.out.println("getC");
        Charlie expResult = new Charlie();
        Beta instance = new Beta();
        instance.setC(expResult);
        Charlie result = instance.getC();
        assertEquals(expResult, result);
    }


}
