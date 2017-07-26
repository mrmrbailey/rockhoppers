/*
 *  This document set is the property of GTECH UK, Watford,
 *  and contains confidential and trade secret information.
 *  It cannot be transferred from the custody or control of GTECH except as
 *  authorized in writing by an officer of GTECH. Neither this item nor
 *  the information it contains can be used, transferred, reproduced, published,
 *  or disclosed, in whole or in part, directly or indirectly, except as
 *  expressly authorized by an officer of GTECH, pursuant to written agreement.
 *
 *  Copyright 2015 GTECH UK. All Rights Reserved.
 */
package com.gtech.automation.uk.service.guice;

import com.google.inject.persist.PersistService;
import javax.inject.Inject;

/**
 * Starts jpa.
 */
public class AutomationInitialiser {

    @Inject AutomationInitialiser(PersistService service) {
        service.start();
    }

}
