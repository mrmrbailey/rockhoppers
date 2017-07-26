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
package com.gtech.automation.uk.entity.gaming.drawbased;

import com.gtech.automation.uk.entity.ControlJob;
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
 * The Cancellation Receipt entity.
 */
@Entity
@Table(name = "cancellation_receipt", catalog = "automation", schema = "cucumber_jvm")
@NamedQueries({
    @NamedQuery(name = "CancellationReceipt.findByRunId",
            query = "SELECT c FROM CancellationReceipt c WHERE c.cancellationReceiptPK.runId = :runId"),
    @NamedQuery(name = "CancellationReceipt.findByRunIdAndTransactionNumber",
            query = "SELECT c FROM CancellationReceipt c WHERE "
                    + "c.cancellationReceiptPK.runId = :runId "
                    + "AND c.cancellationReceiptPK.transactionNumber = :transactionNumber"),
    @NamedQuery(name = "CancellationReceipt.findByRunIdAndTicketSerialNumber",
            query = "SELECT c FROM CancellationReceipt c WHERE "
                    + "c.cancellationReceiptPK.runId = :runId "
                    + "AND c.ticketSerialNumber = :ticketSerialNumber")
})

public class CancellationReceipt {

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

    /**
     * Cancellation Receipt constructor.
     * <p>
     * @param controlJob         the controlJob for this run.
     * @param transactionNumber  the transaction number for the cancellation receipt.
     * @param ticketSerialNumber the serial number for the ticket.
     */
    public CancellationReceipt(ControlJob controlJob, String transactionNumber, String ticketSerialNumber) {
        this.cancellationReceiptPK = new CancellationReceiptPK(controlJob.getRunId(), transactionNumber);
        this.ticketSerialNumber = ticketSerialNumber;
        this.controlJob = controlJob;
    }

    /**
     * Gets the primary key for the cancellation receipt.
     * <p>
     * @return the primary key for the cancellation receipt.
     */
    public CancellationReceiptPK getCancellationReceiptPK() {
        return cancellationReceiptPK;
    }

    public String getSerialNumber() {
        return ticketSerialNumber;
    }

    /**
     * Returns the numerical values in the ticket serial number, striping out the formatting, eg "-" is removed.
     * <p>
     * @return the numerical values in the ticket serial number, striping out the formatting.
     */
    public String getUnformattedTicketSerialNumber() {
        return ticketSerialNumber.replace("-", "");
    }

    /**
     * Gets the controlJob for this cancellation.
     * <p>
     * @return the controlJob for this cancellation.
     */
    public ControlJob getControlJob() {
        return controlJob;
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
        return "com.gtech.automation.uk.entity.gaming.drawbased.CancellationReceipt[ cancellationReceiptPK=" + cancellationReceiptPK + " ]";
    }

}
