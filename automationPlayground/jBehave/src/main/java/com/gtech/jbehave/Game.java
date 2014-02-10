/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.jbehave;

/**
 *
 * @author mxbailey
 */
public class Game {

    private int width, height;
    private StringRenderer observer;

    public Game(int width, int height) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public void setObserver(StringRenderer renderer) {
        this.observer = renderer;
    }
    
    public void toggleCellAt(int column, int row) {
        
    }
}
