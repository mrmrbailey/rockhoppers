/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.automation.uk.entity.gaming.drawbased;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author mxbailey
 */
@Embeddable
public class CancellationReceiptPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "run_id")
    private int runId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 21)
    @Column(name = "transaction_number")
    private String transactionNumber;

    public CancellationReceiptPK() {
    }

    public CancellationReceiptPK(int runId, String transactionNumber) {
        this.runId = runId;
        this.transactionNumber = transactionNumber;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) runId;
        hash += (transactionNumber != null ? transactionNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CancellationReceiptPK)) {
            return false;
        }
        CancellationReceiptPK other = (CancellationReceiptPK) object;
        if (this.runId != other.runId) {
            return false;
        }
        if ((this.transactionNumber == null && other.transactionNumber != null) || (this.transactionNumber != null && !this.transactionNumber.equals(other.transactionNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtech.automation.uk.entity.gaming.drawbased.CancellationReceiptPK[ runId=" + runId + ", transactionNumber=" + transactionNumber + " ]";
    }

}
