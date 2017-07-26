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
package com.gtech.automation.uk.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.gtech.automation.uk.service.guice.AutomationInitialiser;
import com.gtech.automation.uk.service.guice.AutomationModule;
import cucumber.api.guice.CucumberModules;
import cucumber.runtime.java.guice.InjectorSource;

/**
 * Guicy Injector for all systems.
 */
public class AutomationInjectorSource implements InjectorSource {

    @Override
    public Injector getInjector() {

        Injector injector = Guice.createInjector(Stage.PRODUCTION,
                CucumberModules.SCENARIO,
                new JpaPersistModule("openjpa"),
                new AutomationModule());

        injector.getInstance(AutomationInitialiser.class);
        
        return injector;
    }
}
