/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.format;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class RaffleNumberFormatTest {

    public RaffleNumberFormatTest() {
    }

    /**
     * Test of getFormatedRaffleNumber method, of class RaffleNumberFormat.
     */
    @Test
    public void testGetFormatedRaffleNumber() {
        System.out.println("getFormatedRaffleNumber");
        RaffleNumberFormat instance = new RaffleNumberFormat();
        instance.setRaffleNumber("BLUE12341234");
        String expResult = "BLUE 1234 1234";
        String result = instance.getFormatedRaffleNumber();
        assertEquals(expResult, result);
    }

    /**
     * Test of getFormatedRaffleNumber method, of class RaffleNumberFormat.
     */
    @Test
    public void testGetFormatedRaffleNumberStringBuilder() {
        System.out.println("getFormatedRaffleNumber");
        RaffleNumberFormat instance = new RaffleNumberFormat();
        instance.setRaffleNumber("BLUE12341234");
        String expResult = "BLUE 1234 1234";
        String result = instance.getFormatedRaffleNumberStringBuilder();
        assertEquals(expResult, result);
    }
}
