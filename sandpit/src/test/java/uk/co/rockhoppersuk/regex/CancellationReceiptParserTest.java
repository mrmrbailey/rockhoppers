/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.regex;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class CancellationReceiptParserTest {

    String CANCELLATIION_TEXT = "#    CANCELLATIONTICKET NUMBER: 8401-060550404-20XX79- - - - - - - - - - - - - - - - - - - - - - - -         REFUND AMOUNT:  Â£2.00- - - - - - - - - - - - - - - - - - - - - - - -WED31 DEC 14 05:53:31  RETAILER 100152TRANSACTION NUMBER: 8401-004626689-204579";
    String MANUAL_CANCELLATIION_TEXT = "#    CANCELLATIONTICKET NUMBER: 8401-060550404-205979- - - - - - - - - - - - - - - - - - - - - - - -         REFUND AMOUNT:  Â£2.00- - - - - - - - - - - - - - - - - - - - - - - -WED31 DEC 14 05:53:31  RETAILER 100152TRANSACTION NUMBER: 8401-004626689-204579";

    /**
     * Test of parseReceipt method, of class CancellationReceiptParser.
     */
//    @Test
    public void testParseReceipt() {
        System.out.println("parseReceipt");
        String ticketText = "";
        CancellationReceiptParser instance = new CancellationReceiptParser();
        String expResult = "";
        String result = instance.parseReceipt(ticketText);
        assertEquals(expResult, result);
        fail("The test case is a prototype.");
    }

    /**
     * Test of parseSomeTicket method, of class CancellationReceiptParser.
     */
    @Test
    public void testParseSomeReceipt() {
        CancellationReceiptParser instance = new CancellationReceiptParser();
        instance.parseSomeReceipt(CANCELLATIION_TEXT);
        instance.parseSomeReceipt(MANUAL_CANCELLATIION_TEXT);
    }
}
