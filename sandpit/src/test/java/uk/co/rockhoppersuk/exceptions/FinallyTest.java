/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.exceptions;

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
public class FinallyTest {
    

    /**
     * Test of x method, of class Finally.
     */
    @Test
    public void testX() {
        System.out.println("x");
        Finally instance = new Finally();
        String expResult = "";
        String result = instance.x();
        assertEquals(expResult, result);
    }
    

    /**
     * Test of x method, of class Finally.
     */
    @Test
    public void testXint() throws Exception {
        System.out.println("x");
        Finally instance = new Finally();
        instance.what();
    }    
}
