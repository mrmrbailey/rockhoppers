/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.regex;

import java.text.ParseException;
import java.util.Date;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class HostDateParserTest {

    public HostDateParserTest() {
    }

    /**
     * Test of getDate method, of class HostDateParser.
     */
    @Test
    public void testGetDate() throws ParseException {

        HostDateParser parser = new HostDateParser();
        Date date = parser.getDate("** Start Products with user:  oltp7\n" +
"\n" +
"DATE:Mon Dec 08 2014 10:55:39");

        System.out.println(date.toString());
    }

    /**
     * Test of getDate method, of class HostDateParser.
     */
    @Test
    public void testGetDate1() throws ParseException {

        HostDateParser parser = new HostDateParser();
        Date date = parser.getDate("DATE:Mon Dec 08 2014 10:55:39");
        System.out.println(date.toString());
    }

}
