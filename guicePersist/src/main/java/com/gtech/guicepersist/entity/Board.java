/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "board", catalog = "automation", schema = "cucumber_jvm")
@NamedQueries({
    @NamedQuery(name = "Board.findAll", query = "SELECT b FROM Board b"),
    @NamedQuery(name = "Board.findByRunId", query = "SELECT b FROM Board b WHERE b.boardPK.runId = :runId"),
    @NamedQuery(name = "Board.findByTicketSerialNumber", query = "SELECT b FROM Board b WHERE b.boardPK.ticketSerialNumber = :ticketSerialNumber"),
    @NamedQuery(name = "Board.findByBoardId", query = "SELECT b FROM Board b WHERE b.boardPK.boardId = :boardId"),
    @NamedQuery(name = "Board.findByMatrix1Numbers", query = "SELECT b FROM Board b WHERE b.matrix1Numbers = :matrix1Numbers"),
    @NamedQuery(name = "Board.findByMatrix2Numbers", query = "SELECT b FROM Board b WHERE b.matrix2Numbers = :matrix2Numbers"),
    @NamedQuery(name = "Board.findByRaffleNumber", query = "SELECT b FROM Board b WHERE b.raffleNumber = :raffleNumber")})
public class Board implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BoardPK boardPK;

    @Basic(optional = false)
    @NotNull(message = "matrix1_numbers can't be null.")
    @Size(min = 1, max = 18, message = "matrix 1 numbers wrong length.")
    @Column(name = "matrix1_numbers")
    private String matrix1Numbers;

    @Size(max = 5, message = "matrix 2 numbers wrong length.")
    @Column(name = "matrix2_numbers")
    private String matrix2Numbers;

    @Size(max = 14, message = "raffle number wrong length.")
    @Column(name = "raffle_number")
    private String raffleNumber;

    @JoinColumns({
        @JoinColumn(name = "run_id", referencedColumnName = "run_id", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "ticket_serial_number", referencedColumnName = "ticket_serial_number", nullable = false, insertable = false, updatable = false)})
    @NotNull(message = "ticket can't be null.")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ticket ticket;

    public Board() {
    }

    public Board(BoardPK boardPK) {
        this.boardPK = boardPK;
    }

    public Board(BoardPK boardPK, String matrix1Numbers) {
        this.boardPK = boardPK;
        this.matrix1Numbers = matrix1Numbers;
    }

    public Board(int runId, String ticketSerialNumber, String boardId) {
        this.boardPK = new BoardPK(runId, ticketSerialNumber, boardId);
    }

    public Board(final Ticket ticket,
            final String boardId,
            final String matrix1Numbers,
            final String matrix2Numbers,
            final String raffleNumber) {
        this.boardPK = new BoardPK(ticket.ticketPK.getRunId(), ticket.ticketPK.getTicketSerialNumber(), boardId);
        this.matrix1Numbers = matrix1Numbers;
        this.matrix2Numbers = matrix2Numbers;
        this.raffleNumber = raffleNumber;
        this.ticket = ticket;
    }

    public BoardPK getBoardPK() {
        return boardPK;
    }

    public void setBoardPK(BoardPK boardPK) {
        this.boardPK = boardPK;
    }

    public String getMatrix1Numbers() {
        return matrix1Numbers;
    }

    public void setMatrix1Numbers(String matrix1Numbers) {
        this.matrix1Numbers = matrix1Numbers;
    }

    public String getMatrix2Numbers() {
        return matrix2Numbers;
    }

    public void setMatrix2Numbers(String matrix2Numbers) {
        this.matrix2Numbers = matrix2Numbers;
    }

    public String getRaffleNumber() {
        return raffleNumber;
    }

    public void setRaffleNumber(String raffleNumber) {
        this.raffleNumber = raffleNumber;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (boardPK != null ? boardPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Board)) {
            return false;
        }
        Board other = (Board) object;
        if ((this.boardPK == null && other.boardPK != null) || (this.boardPK != null && !this.boardPK.equals(other.boardPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtech.guicepersist.entity.Board[ boardPK=" + boardPK + " ]";
    }
}
