package SOExample;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cucumber.api.java.en.Given;
import javax.inject.Inject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class aStep {

    @Inject
    AService service;

    @Given("^I am about to do something$")
    public void doSomething() throws Throwable {
        service.doSomething();
    }


}
