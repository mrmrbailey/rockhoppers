/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.telnet;

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
public class PortScannerTest {
    
    public PortScannerTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of connect method, of class PortScanner.
     */
    @Test
    public void testConnect() {
        System.out.println("connect");
        String ip = "alapt411";
        int port = 19315;
        PortScanner instance = new PortScanner();
        instance.connect(ip, port);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
