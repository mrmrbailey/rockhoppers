/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.service;

import com.gtech.guicepersist.entity.Board;
import com.gtech.guicepersist.entity.BoardBuilder;
import com.gtech.guicepersist.entity.CancellationReceipt;
import com.gtech.guicepersist.entity.ControlJob;
import com.gtech.guicepersist.entity.Ticket;
import com.gtech.guicepersist.enums.DrawBasedGame;
import com.gtech.guicepersist.enums.DrawDay;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 *
 * @author mxbailey
 */
public class TicketService implements ReceiptServiceTicket {

    private static final Logger logger = Logger.getLogger(CancellationReceiptService.class.getName());

    private final EntityManager em;
    private final ControlJob currentJob;

    @Inject
    protected TicketService(final EntityManager em,
            final ControlService controlService) {
        this.em = em;
        this.currentJob = controlService.getCurrentControlJob();
    }

    @Override
    public Ticket createTicket(String ticketSerialNumber, DrawBasedGame game, BigDecimal cost, String gGuardNumber, DrawDay drawDay, int numberOfWeeks, boolean freeTicket, String ticketMessage) {

        Ticket ticket = new Ticket(currentJob,
                ticketSerialNumber,
                game,
                cost,
                gGuardNumber,
                drawDay,
                numberOfWeeks,
                freeTicket,
                ticketMessage);

        em.getTransaction().begin();
        em.persist(ticket);
        em.getTransaction().commit();
        return ticket;
    }

    @Override
    public Board createBoard(BoardBuilder builder) {
        Board board = builder.build();
        em.getTransaction().begin();

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Board>> constraintViolations = validator.validate(board);

        if (constraintViolations.size() > 0) {
            System.out.println("Constraint Violations occurred..");
            for (ConstraintViolation<Board> contraints : constraintViolations) {
                System.out.println(contraints.getRootBeanClass().getSimpleName()
                                   + "." + contraints.getPropertyPath() + " " + contraints.getMessage());
            }
        }

        em.persist(board);
        em.getTransaction().commit();

        return board;
    }

    @Override
    public Ticket getReceipt(final String ticketSerialNumber) {
        logger.log(Level.FINEST, "getReceipt. ticketSerialNumber: {0}", ticketSerialNumber);
        Query query = em.createNamedQuery(Ticket.FIND_BY_TICKET_SERIAL_NUMBER);
        query.setParameter("runId", currentJob.getRunId());
        query.setParameter("ticketSerialNumber", ticketSerialNumber);

        List<Ticket> receipts = query.getResultList();

        // Check the result set.
        if (receipts.isEmpty()) {
            String message = "Unable to find receipt for ticketSerialNumber: " + ticketSerialNumber;
            logger.log(Level.SEVERE, message);
            throw new IllegalArgumentException(message);

        } else if (receipts.size() > 1) {
            String message = "Found " + receipts.size() + " receipts matching ticketSerialNumber: " + ticketSerialNumber;
            logger.log(Level.SEVERE, message);
            throw new IllegalStateException(message);
        }
        return receipts.get(0);
    }

    @Override
    public List<Ticket> getReceipts() {
        logger.log(Level.FINEST, "getReceipts");

        Query query = em.createNamedQuery(Ticket.FIND_BY_RUN_ID);
        query.setParameter("runId", currentJob.getRunId());

        return query.getResultList();
    }
}
