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
public class AlphaTest {

    /**
     * Test of getB method, of class Alpha.
     */
    @Test
    public void testGetB() {
        System.out.println("getB");
        Alpha instance = new Alpha();
        Beta expResult = new Beta();
        instance.setB(expResult);
        Beta result = instance.getB();
        assertEquals(expResult, result);
    }

    @Test
    public void testDoSomethingImportant() {
        System.out.println("getB");
        Alpha instance = new Alpha();
        int expResult = 1;
        int result = instance.doSomethingImportant();
        assertEquals(expResult, result);
    }

}
