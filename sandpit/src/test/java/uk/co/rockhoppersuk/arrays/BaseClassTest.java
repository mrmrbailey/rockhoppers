/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import org.junit.Test;

/**
 *
 * @author mxbailey
 */
public class BaseClassTest {

    @Test
    public void testSizeOfArrays() {

        BaseClass a = new BaseClass();
        BaseClass b = new SubClassSmaller();
        BaseClass c = new SubClassBigger();

        assertEquals("7", a.toString());
        assertEquals("5", b.toString());
        assertEquals("15", c.toString());
    }
}
