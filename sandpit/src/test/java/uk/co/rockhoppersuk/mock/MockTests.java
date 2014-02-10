/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.mock;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

import static org.mockito.Mockito.*;

/**
 *
 * @author mxbailey
 */
public class MockTests {
    
    Object obj = mock(Object.class);
    
    @Test
    public void testMultipleWhens() {
        String string1 = "String 1";
        String string2 = "String 2";

        when(obj.toString()).thenReturn(string1);
        assertEquals(string1, obj.toString());

        when(obj.toString()).thenReturn(string2);
        assertEquals(string2, obj.toString());
        
        when(obj.toString()).thenReturn(string1);
        when(obj.toString()).thenReturn(string2);
        assertEquals(string2, obj.toString());
        
    }
    

}
