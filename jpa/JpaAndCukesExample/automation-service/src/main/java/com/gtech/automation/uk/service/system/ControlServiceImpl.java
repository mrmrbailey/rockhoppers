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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Service dealing with the control record.
 */
public class ControlServiceImpl implements ControlService {

    private static final Logger logger = Logger.getLogger(ControlServiceImpl.class.getName());

    private final EntityManager em;
    private final String currentJobName;
    private final int currentJobNumber;

    @Inject
    ControlServiceImpl(final EntityManager em,
            @Named("control.job.name") final String currentJobName,
            @Named("control.job.number") final String currentJobNumber) {
        this.em = em;
        this.currentJobName = currentJobName;
        this.currentJobNumber = Integer.parseInt(currentJobNumber);
    }

    @Override
    public int getCurrentRunId() {
        logger.log(Level.FINEST, "getCurrentRunId");
        return getCurrentControlJob().getRunId();
    }

    @Override
    public ControlJob getCurrentControlJob() {
        logger.log(Level.FINEST, "getCurrentControlJob");
        return getControlJob(currentJobName, currentJobNumber, true);
    }

    @Override
    public ControlJob getControlJob(final String jobName, final int jobNumber) {
        logger.log(Level.FINEST, "getControlJob. jobName: {0} jobNumber: {1}", new Object[]{jobName, jobNumber});

        return getControlJob(jobName, jobNumber, false);
    }

    /**
     * gets the matching control job.
     * <p>
     * If the flag is set an empty result set will result in the creation of a ControlJob, if not then an exception is
     * thrown.
     * <p>
     * @param jobName          the name of the job.
     * @param jobNumber        the number of the job.
     * @param createMissingJob flag to determine if a missing job is created (TRUE) or not.
     * @return
     */
    private ControlJob getControlJob(final String jobName, final int jobNumber, final boolean createMissingJob) {
        logger.log(Level.FINEST, "getControlJob. jobName: {0} jobNumber: {1}", new Object[]{jobName, jobNumber});

        Query query = em.createNamedQuery(ControlJob.FIND_BY_JOB_NAME_AND_NUMBER);

        query.setParameter("jobName", currentJobName);
        query.setParameter("jobNumber", currentJobNumber);

        List<ControlJob> controlJobs = query.getResultList();

        // Check the result set.
        if (controlJobs.isEmpty()) {
            if (createMissingJob) {
                //we need to create the job.
                return createControlJob(jobName, jobNumber);
            } else {
                String message = "Unable to find control job for jobName: " + jobName + " jobNumber: " + jobNumber;
                logger.log(Level.SEVERE, message);
                throw new IllegalArgumentException(message);
            }
        } else if (controlJobs.size() > 1) {
            String message = "Found " + controlJobs.size() + " jobs matching jobName: " + jobName + " jobNumber: " + jobNumber;
            logger.log(Level.SEVERE, message);
            throw new IllegalStateException(message);
        }

        return controlJobs.get(0);
    }

    @Override
    public ControlJob createControlJob(final String jobName, final int jobNumber) {
        logger.log(Level.FINEST, "getControlJob. jobName: {0} jobNumber: {1}", new Object[]{jobName, jobNumber});

        ControlJob controlJob = new ControlJob(jobName, jobNumber);
        em.getTransaction().begin();
        em.persist(controlJob);
        em.getTransaction().commit();

        return controlJob;

    }


}
