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
public class BoardPK implements Serializable {
    @Basic(optional = false)
    @NotNull(message = "run_id can't be null.")
    @Column(name = "run_id")

    private int runId;
    @Basic(optional = false)
    @NotNull(message = "ticket_serial_number can't be null.")
    @Size(max = 21, message = "ticket_serial_number wrong length")
    @Column(name = "ticket_serial_number")
    private String ticketSerialNumber;
    
    @Basic(optional = false)
    @NotNull(message = "board_id can't be null.")
    @Size(min = 1, max = 2, message = "ticket_serial_number wrong length")
    @Column(name = "board_id")
    private String boardId;

    public BoardPK() {
    }

    public BoardPK(int runId, String ticketSerialNumber, String boardId) {
        this.runId = runId;
        this.ticketSerialNumber = ticketSerialNumber;
        this.boardId = boardId;
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

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) runId;
        hash += (ticketSerialNumber != null ? ticketSerialNumber.hashCode() : 0);
        hash += (boardId != null ? boardId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BoardPK)) {
            return false;
        }
        BoardPK other = (BoardPK) object;
        if (this.runId != other.runId) {
            return false;
        }
        if ((this.ticketSerialNumber == null && other.ticketSerialNumber != null) || (this.ticketSerialNumber != null && !this.ticketSerialNumber.equals(other.ticketSerialNumber))) {
            return false;
        }
        if ((this.boardId == null && other.boardId != null) || (this.boardId != null && !this.boardId.equals(other.boardId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtech.guicepersist.entity.BoardPK[ runId=" + runId + ", ticketSerialNumber=" + ticketSerialNumber + ", boardId=" + boardId + " ]";
    }

}
