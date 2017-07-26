/*
 *  This document set is the property of GTECH UK, Watford,
 *  and contains confidential and trade secret information.
 *  It cannot be transferred from the custody or control of GTECH except as
 *  authorized in writing by an officer of GTECH. Neither this item nor
 *  the information it contains can be used, transferred, reproduced, published,
 *  or disclosed, in whole or in part, directly or indirectly, except as
 *  expressly authorized by an officer of GTECH, pursuant to written agreement.
 *
 *  Copyright 2014 GTECH UK. All Rights Reserved.
 */
package com.gtech.automation.uk.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Our cucumber runner.
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature/",
        dryRun = false,
        glue = {"classpath:com/gtech/automation/uk/steps"},
        plugin = {"html:target/cucucmber-report",
            "junit:target/cucucmber-report/cucumber-test-results.xml",
            "json:target/cucucmber-report/test-results.json"})
public class RunCukesTest {

}
