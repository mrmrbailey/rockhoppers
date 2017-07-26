/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.entity;

import com.gtech.guicepersist.enums.DrawBasedGame;
import com.gtech.guicepersist.enums.DrawDay;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 *
 * @author mxbailey
 */
@Entity
@Table(name = "ticket", catalog = "automation", schema = "cucumber_jvm")
@NamedQueries({
    @NamedQuery(name = "Ticket.findAll", query = "SELECT t FROM Ticket t"),
    @NamedQuery(name = "Ticket.findByTicketSerialNumber", query = "SELECT t FROM Ticket t WHERE t.ticketPK.ticketSerialNumber = :ticketSerialNumber"),
    @NamedQuery(name = "Ticket.findByGameName", query = "SELECT t FROM Ticket t WHERE t.gameName = :gameName"),
    @NamedQuery(name = "Ticket.findByCost", query = "SELECT t FROM Ticket t WHERE t.cost = :cost"),
    @NamedQuery(name = "Ticket.findByGGuardNumber", query = "SELECT t FROM Ticket t WHERE t.gGuardNumber = :gGuardNumber"),
    @NamedQuery(name = "Ticket.findByDrawDay", query = "SELECT t FROM Ticket t WHERE t.drawDay = :drawDay"),
    @NamedQuery(name = "Ticket.findByNumberOfWeeks", query = "SELECT t FROM Ticket t WHERE t.numberOfWeeks = :numberOfWeeks"),
    @NamedQuery(name = "Ticket.findByFreeTicket", query = "SELECT t FROM Ticket t WHERE t.freeTicket = :freeTicket"),

    @NamedQuery(name = "Ticket.findByRunId",
            query = "SELECT t FROM Ticket t WHERE t.ticketPK.runId = :runId"),
    @NamedQuery(name = "Ticket.findByRunIdAndTicketSerialNumber",
            query = "SELECT t FROM Ticket t "
                    + "WHERE t.ticketPK.runId = :runId "
                    + "And t.ticketPK.ticketSerialNumber = :ticketSerialNumber")
})

public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The named query for retrieving all the tickets for a given runId.
     * <p>
     * The parameter is
     * <ul>
     * <li>runId</li>
     * </ul>
     */
    public static final String FIND_BY_RUN_ID = "Ticket.findByRunId";

    /**
     * The named query for retrieving the ticket by the runId and the ticket serial number.
     * <p>
     * The parameters are
     * <ul>
     * <li>runId</li>
     * <li>ticketSerialNumber</li>
     * </ul>
     */
    public static final String FIND_BY_TICKET_SERIAL_NUMBER = "Ticket.findByRunIdAndTicketSerialNumber";

    @EmbeddedId
    protected TicketPK ticketPK;

    @Size(max = 15, message = "game_name max is 15 characters.")
    @Column(name = "game_name")
    private String gameName;

    @Min(value = 0)
    @Column(precision = 6, scale = 2)
    private BigDecimal cost;

    @Size(max = 6, message = "GGUARD is only 6 characters.")
    @Column(name = "g_guard_number")
    private String gGuardNumber;

    @Size(max = 11, message = "draw_day is 11 characters.")
    @Column(name = "draw_day")
    private String drawDay;

    @Max(value = 8, message = "You can only play for 8 weeks")
    @Column(name = "number_of_weeks")
    private Integer numberOfWeeks;

    @Column(name = "free_ticket")
    private Boolean freeTicket;

    @Transient
    private String ticketMessage;

    @JoinColumn(name = "run_id", referencedColumnName = "run_id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private ControlJob controlJob;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket", fetch = FetchType.LAZY)
    private List<Board> boards;

    public Ticket() {
    }

    public Ticket(TicketPK ticketPK) {
        this.ticketPK = ticketPK;
    }

    public Ticket(int runId, String ticketSerialNumber) {
        this.ticketPK = new TicketPK(runId, ticketSerialNumber);
    }

    /**
     * Creates a Ticket with the supplied parameters.
     * <p>
     * @param controlJob         the controlJob for this run.
     * @param ticketSerialNumber the serial number for the ticket.
     * @param game               the game the ticket is for.
     * @param cost               the cost of the ticket.
     * @param gGuardNumber       the GGUARD number on the ticket.
     * @param drawDay            the drawDay of the ticket.
     * @param numberOfWeeks      the number of weeks the ticket is for.
     * @param freeTicket         flag to determine if the ticket is free (TRUE) or not.
     * @param ticketMessage      the message on the ticket.
     */
    public Ticket(final ControlJob controlJob,
            final String ticketSerialNumber,
            final DrawBasedGame game,
            final BigDecimal cost,
            final String gGuardNumber,
            final DrawDay drawDay,
            final int numberOfWeeks,
            final boolean freeTicket,
            final String ticketMessage) {

        this.ticketPK = new TicketPK(controlJob.getRunId(), ticketSerialNumber);
        this.gameName = game.getName();
        this.cost = cost;
        this.gGuardNumber = gGuardNumber;
        this.drawDay = drawDay.getName();
        this.numberOfWeeks = numberOfWeeks;
        this.freeTicket = freeTicket;
        this.ticketMessage = ticketMessage;

        this.controlJob = controlJob;
    }

    public TicketPK getTicketPK() {
        return ticketPK;
    }

    public void setTicketPK(TicketPK ticketPK) {
        this.ticketPK = ticketPK;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getGGuardNumber() {
        return gGuardNumber;
    }

    public void setGGuardNumber(String gGuardNumber) {
        this.gGuardNumber = gGuardNumber;
    }

    public String getDrawDay() {
        return drawDay;
    }

    public void setDrawDay(String drawDay) {
        this.drawDay = drawDay;
    }

    public Integer getNumberOfWeeks() {
        return numberOfWeeks;
    }

    public void setNumberOfWeeks(Integer numberOfWeeks) {
        this.numberOfWeeks = numberOfWeeks;
    }

    public Boolean getFreeTicket() {
        return freeTicket;
    }

    public void setFreeTicket(Boolean freeTicket) {
        this.freeTicket = freeTicket;
    }

    public String getTicketMessage() {
        return ticketMessage;
    }

    public ControlJob getControlJob() {
        return controlJob;
    }

    public void setControlJob(ControlJob controlJob) {
        this.controlJob = controlJob;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public void addBoard(Board board) {
        this.boards.add(board);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketPK != null ? ticketPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.ticketPK == null && other.ticketPK != null) || (this.ticketPK != null && !this.ticketPK.equals(other.ticketPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gtech.guicepersist.entity.Ticket[ ticketPK=" + ticketPK + " ]";
    }

}
