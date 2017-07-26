/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.automation.uk.steps;

import com.gtech.automation.uk.service.gaming.CancellationReceiptService;
import com.gtech.automation.uk.service.system.ControlService;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 *
 * @author mxbailey
 */
public class Steps {

    private static final Logger logger = Logger.getLogger(Steps.class.getName());

    @Inject ControlService cs;
    @Inject CancellationReceiptService crs;

    @Given("^I am signed on to the altura as \"(.*?)\"$")
    public void i_am_signed_on_to_the_altura_as(String arg1) throws Throwable {
        logger.log(Level.ALL, "i_am_signed_on_to_the_altura_as");
        logger.log(Level.ALL, "current run id {0}", cs.getCurrentRunId());
        logger.log(Level.ALL, "blah: {}", crs.getReceipt(arg1));
    }

    @Then("^I am signed on to the altura$")
    public void i_am_signed_on_to_the_altura() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        logger.log(Level.ALL, "i_am_signed_on_to_the_altura");

    }

}
