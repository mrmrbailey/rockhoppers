/*
 *  This document set is the property of GTECH UK, Watford,
 *  and contains confidential and trade secret information.
 *  It cannot be transferred from the custody or control of GTECH except as
 *  authorized in writing by an officer of GTECH. Neither this item nor
 *  the information it contains can be used, transferred, reproduced, published,
 *  or disclosed, in whole or in part, directly or indirectly, except as
 *  expressly authorized by an officer of GTECH, pursuant to written agreement.
 *
 *  Copyright 2014 GTECH UK. All Rights Reserved.
 */
package com.gtech.automation.uk.service.gaming;

import com.gtech.automation.uk.entity.ControlJob;
import com.gtech.automation.uk.entity.gaming.drawbased.CancellationReceipt;
import com.gtech.automation.uk.service.system.ControlService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Implementor or the receipt service for cancellation receipts.
 */
public class CancellationReceiptService {

    private static final Logger logger = Logger.getLogger(CancellationReceiptService.class.getName());

    private final EntityManager em;
    private final ControlJob currentJob;

    @Inject
    protected CancellationReceiptService(final EntityManager em,
            final ControlService controlService) {
        this.em = em;
        this.currentJob = controlService.getCurrentControlJob();
    }

    /**
     * Creates a cancellation receipt, and persists it.
     * <p>
     * @param transactionNumber  the transaction number for the cancellation receipt.
     * @param ticketSerialNumber the serial number for the ticket.
     * <p>
     * @return the Cancellation receipt created.
     */
    public CancellationReceipt createReceipt(final String transactionNumber, final String ticketSerialNumber) {
        logger.log(Level.FINEST, "createReceipt. transactionNumber: {0} ticketSerialNumber{1}",
                new Object[]{transactionNumber, ticketSerialNumber});

        CancellationReceipt cancellationReceipt = new CancellationReceipt(currentJob, transactionNumber, ticketSerialNumber);

        em.getTransaction().begin();
        em.persist(cancellationReceipt);
        em.getTransaction().commit();
        return cancellationReceipt;
    }

    /**
     * Gets the receipt for the given transaction number.
     * <p>
     * @param transactionNumber the unique transaction number of the receipt.
     * @return the Cancellation Receipt.
     */
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

    public List<CancellationReceipt> getReceipts() {
        Query query = em.createNamedQuery(CancellationReceipt.FIND_BY_RUN_ID);
        query.setParameter("runId", currentJob.getRunId());

        return query.getResultList();
    }
}
