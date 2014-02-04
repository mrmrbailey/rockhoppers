/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.cucumber.steps;

import com.gtech.cucumber.Car;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *
 * @author mxbailey
 */
public class CarFuelSteps {
    private Car car;

    @Given("^a car with (\\d*) litres of fuel in the tank$")
    public void createCar(int initialFuelLevel) {
        car = new Car(initialFuelLevel);
    }

    @When("^you fill it with (\\d*) litres of fuel$")
    public void addFuel(int addedFuel) {
        car.addFuel(addedFuel);
    }

    @Then("^the tank contains (\\d*) litres$")
    public void checkBalance(int expectedFuelLevel) {
        int actualFuelLevel = car.getFuelLevel();
        assertThat(actualFuelLevel, is(expectedFuelLevel));
    }
}
