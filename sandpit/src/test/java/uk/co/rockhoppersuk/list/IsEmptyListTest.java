/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.list;

import java.util.ArrayList;
import java.util.List;
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
public class IsEmptyListTest {
    

    @Test
    public void testIsEmpty() {
        AnObject anObject = new AnObject();
        try {
            anObject.alist.isEmpty();
            fail("Expected a null pointer exception");
        } catch (NullPointerException nex) {
            assertTrue(true);            
        }
    }
    
    private class AnObject {
        
        List alist;

        public List getAlist() {
            return alist;
        }

        public void setAlist(List alist) {
            this.alist = alist;
        }
    }
}
