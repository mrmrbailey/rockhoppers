/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.gtech.guicepersist.service.CancellationReceiptService;
import com.gtech.guicepersist.service.ReceiptService;

/**
 *
 * @author mxbailey
 */
public class CancellationsProcessor {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new JpaPersistModule("openjpa"));
        injector.getInstance(ApplicationInitialiser.class);

        ReceiptService service = injector.getInstance(CancellationReceiptService.class);

        String transactionNumber = "8401-038191361-208080";
        String ticketNumber = "8401-019641600-207580";

//        System.out.println(service.createReceipt(transactionNumber, ticketNumber));
        System.out.println(service.getReceipt(transactionNumber));
        System.out.println(service.getReceiptForTicket(ticketNumber));
        System.out.println(service.getReceipts().size());

    }
}
