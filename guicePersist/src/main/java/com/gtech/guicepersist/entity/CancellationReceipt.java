/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mxbailey
 */
@Entity
@Table(name = "cancellation_receipt", catalog = "automation", schema = "cucumber_jvm")
@NamedQueries({
    @NamedQuery(name = "CancellationReceipt.findAll", query = "SELECT c FROM CancellationReceipt c"),
    @NamedQuery(name = "CancellationReceipt.findByRunId",
            query = "SELECT c FROM CancellationReceipt c WHERE c.cancellationReceiptPK.runId = :runId"),
    @NamedQuery(name = "CancellationReceipt.findByTransactionNumber", query = "SELECT c FROM CancellationReceipt c WHERE c.cancellationReceiptPK.transactionNumber = :transactionNumber"),
    @NamedQuery(name = "CancellationReceipt.findByTicketSerialNumber", query = "SELECT c FROM CancellationReceipt c WHERE c.ticketSerialNumber = :ticketSerialNumber"),

    @NamedQuery(name = "CancellationReceipt.findByRunIdAndTransactionNumber",
            query = "SELECT c FROM CancellationReceipt c WHERE "
                    + "c.cancellationReceiptPK.runId = :runId "
                    + "AND c.cancellationReceiptPK.transactionNumber = :transactionNumber"),
    @NamedQuery(name = "CancellationReceipt.findByRunIdAndTicketSerialNumber",
            query = "SELECT c FROM CancellationReceipt c WHERE "
                    + "c.cancellationReceiptPK.runId = :runId "
                    + "AND c.ticketSerialNumber = :ticketSerialNumber")

})

public class CancellationReceipt implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * The named query for retrieving all the cancellations for a given runId.
     * <p>
     * The parameter is
     * <ul>
     * <li>runId</li>
     * </ul>
     */
    public static final String FIND_BY_RUN_ID = "CancellationReceipt.findByRunId";
    /**
     * The named query for retrieving the cancellation receipt by the runId and the transaction number.
     * <p>
     * The parameters are
     * <ul>
     * <li>runId</li>
     * <li>transactionNumber</li>
     * </ul>
     */
    public static final String FIND_BY_TRANSACTION_NUMBER = "CancellationReceipt.findByRunIdAndTransactionNumber";

    /**
     * The named query for retrieving the cancellation receipt by the runId and the ticket serial number.
     * <p>
     * The parameters are
     * <ul>
     * <li>runId</li>
     * <li>ticketSerialNumber</li>
     * </ul>
     */
    public static final String FIND_BY_TICKET_SERIAL_NUMBER = "CancellationReceipt.findByRunIdAndTicketSerialNumber";


    @EmbeddedId
    protected CancellationReceiptPK cancellationReceiptPK;

    @Size(min = 21, max = 21, message = "ticket serial number should be 21 characters")
    @Column(name = "ticket_serial_number")
    private String ticketSerialNumber;

    @NotNull
    @JoinColumn(name = "run_id", referencedColumnName = "run_id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ControlJob controlJob;

    public CancellationReceipt() {
    }

    public CancellationReceipt(CancellationReceiptPK cancellationReceiptPK) {
        this.cancellationReceiptPK = cancellationReceiptPK;
    }

    public CancellationReceipt(int runId, String transactionNumber) {
        this.cancellationReceiptPK = new CancellationReceiptPK(runId, transactionNumber);
    }

    public CancellationReceipt(ControlJob controlJob, String transactionNumber, String ticketSerialNumber) {
        this.cancellationReceiptPK = new CancellationReceiptPK(controlJob.getRunId(), transactionNumber);
        this.ticketSerialNumber = ticketSerialNumber;
        this.controlJob = controlJob;
    }

    public CancellationReceipt(CancellationReceiptPK cancellationReceiptPK, String ticketSerialNumber) {
        this.cancellationReceiptPK = cancellationReceiptPK;
        this.ticketSerialNumber = ticketSerialNumber;
    }

    public CancellationReceiptPK getCancellationReceiptPK() {
        return cancellationReceiptPK;
    }

    public void setCancellationReceiptPK(CancellationReceiptPK cancellationReceiptPK) {
        this.cancellationReceiptPK = cancellationReceiptPK;
    }

    public String getTicketSerialNumber() {
        return ticketSerialNumber;
    }

    public void setTicketSerialNumber(String ticketSerialNumber) {
        this.ticketSerialNumber = ticketSerialNumber;
    }

    public ControlJob getControlJob() {
        return controlJob;
    }

    public void setControlJob(ControlJob controlJob) {
        this.controlJob = controlJob;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cancellationReceiptPK != null ? cancellationReceiptPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancellationReceipt)) {
            return false;
        }
        CancellationReceipt other = (CancellationReceipt) object;
        if ((this.cancellationReceiptPK == null && other.cancellationReceiptPK != null) || (this.cancellationReceiptPK != null && !this.cancellationReceiptPK.equals(other.cancellationReceiptPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtech.guicepersist.entity.CancellationReceipt[ cancellationReceiptPK=" + cancellationReceiptPK + " ]";
    }

}
