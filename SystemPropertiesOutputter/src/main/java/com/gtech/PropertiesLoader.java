/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech;

import java.util.Map;

/**
 *
 * @author mxbailey
 */
public class PropertiesLoader {

    public void outputSystemProperties() {
        for (Map.Entry<Object, Object> entrySet : System.getProperties().entrySet()) {
            System.out.println(entrySet.getKey() + " => " + entrySet.getValue());
            }
    }

}
