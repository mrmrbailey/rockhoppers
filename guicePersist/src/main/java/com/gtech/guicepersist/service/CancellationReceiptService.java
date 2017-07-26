/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.service;

import com.gtech.guicepersist.entity.CancellationReceipt;
import com.gtech.guicepersist.entity.CancellationReceiptPK;
import com.gtech.guicepersist.entity.ControlJob;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author mxbailey
 */
public class CancellationReceiptService implements ReceiptService {

    private static final Logger logger = Logger.getLogger(CancellationReceiptService.class.getName());

    private final EntityManager em;
    private final ControlJob currentJob;

    @Inject
    protected CancellationReceiptService(final EntityManager em,
            final ControlService controlService) {
        this.em = em;
        this.currentJob = controlService.getCurrentControlJob();
    }

    @Override
    public CancellationReceipt createReceipt(final String transactionNumber, final String ticketSerialNumber) {
        CancellationReceipt cancellationReceipt = new CancellationReceipt(currentJob, transactionNumber, ticketSerialNumber);

        em.getTransaction().begin();
        em.persist(cancellationReceipt);
        em.getTransaction().commit();
        return cancellationReceipt;
    }

    @Override
    public CancellationReceipt getReceipt(String transactionNumber) {
        logger.log(Level.FINEST, "getReceipt. transactionNumber: {0}", transactionNumber);

        Query query = em.createNamedQuery(CancellationReceipt.FIND_BY_TRANSACTION_NUMBER);
        query.setParameter("runId", currentJob.getRunId());
        query.setParameter("transactionNumber", transactionNumber);

        List<CancellationReceipt> receipts = query.getResultList();

        // Check the result set.
        if (receipts.isEmpty()) {
            String message = "Unable to find receipt for transactionNumber: " + transactionNumber;
            logger.log(Level.SEVERE, message);
            throw new IllegalArgumentException(message);

        } else if (receipts.size() > 1) {
            String message = "Found " + receipts.size() + " receipts matching transactionNumber: " + transactionNumber;
            logger.log(Level.SEVERE, message);
            throw new IllegalStateException(message);
        }
        return receipts.get(0);
    }

    /**
     * Gets the cancellation receipt for the ticket with the ticketSerialNumber serial number.
     * <p>
     * @param ticketSerialNumber the serial number of the ticket that was cancelled.
     * @return the Cancellation Receipt for the ticket with the ticketSerialNumber serial number.
     */
    @Override
    public CancellationReceipt getReceiptForTicket(String ticketSerialNumber) {
        logger.log(Level.FINEST, "getReceiptForTicket. ticketSerialNumber: {0}", ticketSerialNumber);

        Query query = em.createNamedQuery(CancellationReceipt.FIND_BY_TICKET_SERIAL_NUMBER);

        query.setParameter("runId", currentJob.getRunId());
        query.setParameter("ticketSerialNumber", ticketSerialNumber);

        List<CancellationReceipt> receipts = query.getResultList();

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
    public List<CancellationReceipt> getReceipts() {
        Query query = em.createNamedQuery(CancellationReceipt.FIND_BY_RUN_ID);
        query.setParameter("runId", currentJob.getRunId());

        return query.getResultList();
    }

}
