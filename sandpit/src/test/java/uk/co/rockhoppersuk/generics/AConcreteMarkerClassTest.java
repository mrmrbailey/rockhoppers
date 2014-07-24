/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.co.rockhoppersuk.generics;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class AConcreteMarkerClassTest {
    
    public AConcreteMarkerClassTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of blob method, of class AConcreteMarkerClass.
     */
    @Test
    public void testBlob() {
        System.out.println("blob");
        AConcreteMarkerClass instance = new AConcreteMarkerClass();
        int expResult = 0;
        int result = instance.blob();
        assertEquals(expResult, result);
        instance.getT();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    
    
}
