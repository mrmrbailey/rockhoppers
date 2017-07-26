/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist.service;

import com.gtech.guicepersist.entity.CancellationReceipt;
import java.util.List;

/**
 *
 * @author mxbailey
 */
public interface ReceiptService {

    //Not in orig as parameters are not correct.
    CancellationReceipt createReceipt(final String transactionNumber, final String ticketSerialNumber);

    /**
     * Gets the receipt for the given serial number.
     * <p>
     * @param serialNumber the unique serial number of the receipt.
     * @return the receipt for the given serial number.
     */
    CancellationReceipt getReceipt(String serialNumber);

    /**
     * Gets the receipts for the current control job.
     * <p>
     * @return the receipts for the current control job.
     */
    List<CancellationReceipt> getReceipts();

    CancellationReceipt getReceiptForTicket(String ticketSerialNumber);

}
