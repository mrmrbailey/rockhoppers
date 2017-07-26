/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.service;

import com.gtech.guicepersist.entity.CancellationReceipt;
import com.gtech.guicepersist.entity.ControlJob;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mxbailey
 */
public class ControlService implements ControlServiceDAO {

    private static final Logger logger = Logger.getLogger(ControlService.class.getName());

    private EntityManager em;

    private static final String currentJobName = "jobname";
    private static final int currentJobNumber = 4;

    @Inject
    public ControlService(EntityManager em) {
        this.em = em;
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

    @Override
    public List<CancellationReceipt> getCurrentCancellations() {
        logger.log(Level.FINEST, "getCurrentCancellations");

        return getCurrentControlJob().getCancellationReceiptList();
    }
}
