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
package com.gtech.automation.uk.service.guice;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.name.Names;
import com.gtech.automation.uk.service.gaming.CancellationReceiptService;
import com.gtech.automation.uk.service.system.ControlService;
import com.gtech.automation.uk.service.system.ControlServiceImpl;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Guicy Injector for the automation service.
 * <p>
 * There is no logic in this class just bindings
 */
public class AutomationModule implements Module {

    private static final Logger logger = Logger.getLogger(AutomationModule.class.getName());

    private Properties props = null;

    @Override
    public void configure(final Binder binder) {

        logger.log(Level.FINEST, "Guice Bindings for the host");

        if (props == null) {
            props = new Properties();
            props.put("control.job.name", "jobname");
            props.put("control.job.number", "1");
        }
        logger.log(Level.FINER, "Binding {0} properties", Integer.toString(props.size()));
        Names.bindProperties(binder, props);

        binder.bind(ControlService.class).to(ControlServiceImpl.class);

        // Bind the receiptServices.
        binder.bind(CancellationReceiptService.class);
    }
}
