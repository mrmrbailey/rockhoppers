/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.entity;

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
public class TicketPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "run_id", nullable = false)
    private int runId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 21)
    @Column(name = "ticket_serial_number", nullable = false, length = 21)
    private String ticketSerialNumber;

    public TicketPK() {
    }

    public TicketPK(int runId, String ticketSerialNumber) {
        this.runId = runId;
        this.ticketSerialNumber = ticketSerialNumber;
    }

    public int getRunId() {
        return runId;
    }

    public void setRunId(int runId) {
        this.runId = runId;
    }

    public String getTicketSerialNumber() {
        return ticketSerialNumber;
    }

    public void setTicketSerialNumber(String ticketSerialNumber) {
        this.ticketSerialNumber = ticketSerialNumber;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) runId;
        hash += (ticketSerialNumber != null ? ticketSerialNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TicketPK)) {
            return false;
        }
        TicketPK other = (TicketPK) object;
        if (this.runId != other.runId) {
            return false;
        }
        if ((this.ticketSerialNumber == null && other.ticketSerialNumber != null) || (this.ticketSerialNumber != null && !this.ticketSerialNumber.equals(other.ticketSerialNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtech.guicepersist.entity.TicketPK[ runId=" + runId + ", ticketSerialNumber=" + ticketSerialNumber + " ]";
    }

}
