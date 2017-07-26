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
package com.gtech.automation.uk.service.system;

import com.gtech.automation.uk.entity.ControlJob;

/**
 * Service dealing with the control record.
 */
public interface ControlService {

    /**
     * Gets the run id for this automation run.
     * <p>
     * @return the run id for this automation run.
     */
    int getCurrentRunId();

    /**
     * Gets the current control job. If one has not been created by jenkins this will create one.
     *
     * @return the current control job.
     */
    ControlJob getCurrentControlJob();

    /**
     * Gets the control job.
     *
     * @param jobName the name of the job.
     * @param jobNumber the number of the job.
     *
     * @return the current control job.
     */
    ControlJob getControlJob(final String jobName, final int jobNumber);

    /**
     * Creates a control job for the current job name and number.
     *
     * Note although not a primary key infraction the job name and number MUST be unique and this is enforced.
     *
     * @param jobName the name of the job.
     * @param jobNumber the number of the job.
     *
     * @return a new ControlJob for the job name and number.
     */
    ControlJob createControlJob(final String jobName, final int jobNumber);
}
