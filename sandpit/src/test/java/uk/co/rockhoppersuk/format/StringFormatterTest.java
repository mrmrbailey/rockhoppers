/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.format;

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
public class StringFormatterTest {
    @Test
    public void testStringFormat() {
        String expectedValue = "<game type=\"lotto\">";
        String actualValue = String.format("<game type=\"%s\">", "lotto");
        
        assertTrue(expectedValue.equals(actualValue));
    }
    
}