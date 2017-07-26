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
package com.gtech.automation.uk.entity;

import com.gtech.automation.uk.entity.gaming.drawbased.CancellationReceipt;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The entity for the record of the job that ran to create the data.
 */
@Entity
@Table(name = "control_job", catalog = "automation", schema = "cucumber_jvm")
@NamedQueries({
    @NamedQuery(name = "ControlJob.findByJobNameAndNumber",
            query = "SELECT c FROM ControlJob c WHERE c.jobName = :jobName AND c.jobNumber = :jobNumber")
})
public class ControlJob implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The named query for retrieving the control job by job name and number.
     * <p>
     * The parameters are
     * <ul>
     * <li>jobName</li>
     * <li>jobNumber</li>
     * </ul>
     */
    public static final String FIND_BY_JOB_NAME_AND_NUMBER = "ControlJob.findByJobNameAndNumber";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "run_id")
    private Integer runId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 31)
    @Column(name = "job_name")
    private String jobName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1)
    @Column(name = "job_number")
    private int jobNumber;

    @Column(name = "insert_ts", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertTs;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "controlJob", fetch = FetchType.LAZY)
    private List<CancellationReceipt> cancellations;

    /**
     * default constructor.
     */
    public ControlJob() {
    }

    /**
     * Constructor for the control job.
     * <p>
     * @param jobName   - the name of the job
     * @param jobNumber - the number of the job
     */
    public ControlJob(String jobName, int jobNumber) {
        this.jobName = jobName;
        this.jobNumber = jobNumber;
    }

    /**
     * Constructor for the control job. for use in Unit Tests - the runId is set by the DB.
     * <p>
     * @param mockRunId     - the id for this run
     * @param mockJobName   - the name of the job
     * @param mockJobNumber - the number of the job
     */
    public ControlJob(int mockRunId, String mockJobName, int mockJobNumber) {
        this.runId = mockRunId;
        this.jobName = mockJobName;
        this.jobNumber = mockJobNumber;
    }

    /**
     * Gets the run_id (primary key) for this control job.
     * <p>
     * @return the run_id for this control job.
     */
    public Integer getRunId() {
        return runId;
    }

    /**
     * Gets the name of the control job for this run.
     * <p>
     * @return the name of the control job for this run.
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Gets the number of the control job for this run.
     * <p>
     * @return the number of the control job for this run.
     */
    public int getJobNumber() {
        return jobNumber;
    }

    /**
     * Gets the time that this job ran.
     * <p>
     * @return the time that this job ran.
     */
    public Date getInsertTs() {
        return insertTs;
    }


    /**
     * Gets all the cancellations for this job.
     * <p>
     * @return all the cancellations for this job.
     */
    public List<CancellationReceipt> getCancellations() {
        return cancellations;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (runId != null ? runId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ControlJob)) {
            return false;
        }
        ControlJob other = (ControlJob) object;
        if ((this.runId == null && other.runId != null) || (this.runId != null && !this.runId.equals(other.runId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtech.automation.uk.entity.ControlJob[ runId=" + runId + " ]";
    }

}
