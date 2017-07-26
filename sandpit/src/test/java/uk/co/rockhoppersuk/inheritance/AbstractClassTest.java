/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.inheritance;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class AbstractClassTest {

    public AbstractClassTest() {
    }

    /**
     * Test of parse method, of class AbstractClass.
     */
    @Test
    public void testParse() {
        System.out.println("parse");
        String y = "";
        AbstractClass instance = new Child();
        instance.parse(y);
    }


}
