/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class PropertiesLoaderTest {

    public PropertiesLoaderTest() {
    }

    /**
     * Test of outputSystemProperties method, of class PropertiesLoader.
     */
    @org.junit.Test
    public void testOutputSystemProperties() {
        PropertiesLoader instance = new PropertiesLoader();
        instance.outputSystemProperties();
    }

}
