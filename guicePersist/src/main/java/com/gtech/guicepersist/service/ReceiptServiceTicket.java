/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.service;

import com.gtech.guicepersist.entity.Board;
import com.gtech.guicepersist.entity.BoardBuilder;
import com.gtech.guicepersist.entity.ControlJob;
import com.gtech.guicepersist.entity.Ticket;
import com.gtech.guicepersist.enums.DrawBasedGame;
import com.gtech.guicepersist.enums.DrawDay;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import javax.persistence.Query;

/**
 *
 * @author mxbailey
 */
public interface ReceiptServiceTicket {

    /**
     * Creates a Ticket with the supplied parameters.
     * <p>
     * @param ticketSerialNumber the serial number for the ticket.
     * @param game               the game the ticket is for.
     * @param cost               the cost of the ticket.
     * @param gGuardNumber       the GGUARD number on the ticket.
     * @param drawDay            the drawDay of the ticket.
     * @param numberOfWeeks      the number of weeks the ticket is for.
     * @param freeTicket         flag to determine if the ticket is free (TRUE) or not.
     * @param ticketMessage      the message on the ticket.
     * <p>
     * @return the created ticket.
     */
    Ticket createTicket(final String ticketSerialNumber,
            final DrawBasedGame game,
            final BigDecimal cost,
            final String gGuardNumber,
            final DrawDay drawDay,
            final int numberOfWeeks,
            final boolean freeTicket,
            final String ticketMessage);

    Board createBoard(BoardBuilder builder);

    Ticket getReceipt(final String ticketSerialNumber);

    List<Ticket> getReceipts();
}
