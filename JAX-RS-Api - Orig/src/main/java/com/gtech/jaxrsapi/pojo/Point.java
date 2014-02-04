/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jaxrsapi.pojo;

/**
 *
 * @author mxbailey
 */
public final class Point {

    final private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    int x()  {return x;}
    int y()  {return y;}
    int getArea() {return x*y;}
}
