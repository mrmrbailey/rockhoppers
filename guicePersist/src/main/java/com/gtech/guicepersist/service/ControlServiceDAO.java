/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.service;

import com.gtech.guicepersist.entity.CancellationReceipt;
import com.gtech.guicepersist.entity.ControlJob;
import java.util.List;

/**
 *
 * @author mxbailey
 */
public interface ControlServiceDAO {

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

    List<CancellationReceipt> getCurrentCancellations();


}
