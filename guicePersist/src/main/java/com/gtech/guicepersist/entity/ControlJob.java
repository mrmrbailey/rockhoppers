/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.entity;

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
 *
 * @author mxbailey
 */
@Entity
@Table(name = "control_job", catalog = "automation", schema = "cucumber_jvm")
@NamedQueries({
    @NamedQuery(name = "ControlJob.findAll", query = "SELECT c FROM ControlJob c"),
    @NamedQuery(name = "ControlJob.findByRunId", query = "SELECT c FROM ControlJob c WHERE c.runId = :runId"),
    @NamedQuery(name = "ControlJob.findByJobName", query = "SELECT c FROM ControlJob c WHERE c.jobName = :jobName"),
    @NamedQuery(name = "ControlJob.findByJobNumber", query = "SELECT c FROM ControlJob c WHERE c.jobNumber = :jobNumber"),
    @NamedQuery(name = "ControlJob.findByInsertTs", query = "SELECT c FROM ControlJob c WHERE c.insertTs = :insertTs"),
    @NamedQuery(name = "ControlJob.findByJobNameAndNumber",
            query = "SELECT c FROM ControlJob c WHERE c.jobName = :jobName AND c.jobNumber = :jobNumber")
})
public class ControlJob implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * The named query for retrieving the control job by job name and number.
     *
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
//    @NotNull
    @Column(name = "run_id")
    private Integer runId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 31)
    @Column(name = "job_name")
    private String jobName;
    @Basic(optional = false)

    @NotNull
    @Column(name = "job_number")
    private int jobNumber;

    @Column(name = "insert_ts", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date insertTs;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "controlJob", fetch = FetchType.LAZY)
    private List<Ticket> ticketList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "controlJob", fetch = FetchType.LAZY)
    private List<CancellationReceipt> cancellationReceiptList;


    /**
     * default constructor.
     */
    public ControlJob() {
    }

    /**
     * Don't Think I need this
     * @param runId The id for the run.
     */
    public ControlJob(Integer runId) {
        this.runId = runId;
    }

    /**
     * Don't think I need this one either as runId is generated
     * @param runId
     * @param jobName
     * @param jobNumber
     */
    public ControlJob(Integer runId, String jobName, int jobNumber) {
        this.runId = runId;
        this.jobName = jobName;
        this.jobNumber = jobNumber;
    }

    /**
     * I added this one so hopefully it works!
     *
     * @param jobName - the name of the job
     * @param jobNumber - the number of the job
     */
    public ControlJob(String jobName, int jobNumber) {
        this.jobName = jobName;
        this.jobNumber = jobNumber;
    }

    public Integer getRunId() {
        return runId;
    }

    public void setRunId(Integer runId) {
        this.runId = runId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(int jobNumber) {
        this.jobNumber = jobNumber;
    }

    public Date getInsertTs() {
        return insertTs;
    }

    public void setInsertTs(Date insertTs) {
        this.insertTs = insertTs;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public List<CancellationReceipt> getCancellationReceiptList() {
        return cancellationReceiptList;
    }

    public void setCancellationReceiptList(List<CancellationReceipt> cancellationReceiptList) {
        this.cancellationReceiptList = cancellationReceiptList;
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
        return "com.gtech.guicepersist.entity.ControlJob[ runId=" + runId + " ]";
    }

}
