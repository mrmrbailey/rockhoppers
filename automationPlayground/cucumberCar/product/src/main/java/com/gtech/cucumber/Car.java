/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.cucumber;

/**
 *
 * @author mxbailey
 */
public class Car {

    private Integer fuelLevel;

    public Car(int initialFuelLevel) {
        fuelLevel = initialFuelLevel;
    }

    public void addFuel(int addedFuel) {
        fuelLevel = fuelLevel + addedFuel;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }
}
