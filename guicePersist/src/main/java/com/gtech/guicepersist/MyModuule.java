/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist;

import com.google.inject.AbstractModule;
import com.gtech.guicepersist.service.CancellationReceiptService;
import com.gtech.guicepersist.service.ControlServiceDAO;
import com.gtech.guicepersist.service.ControlService;
import com.gtech.guicepersist.service.ReceiptService;
import com.gtech.guicepersist.service.ReceiptServiceTicket;
import com.gtech.guicepersist.service.TicketService;

/**
 *
 * @author mxbailey
 */
public class MyModuule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ControlServiceDAO.class).to(ControlService.class);
        bind(ReceiptService.class).to(CancellationReceiptService.class);
        bind(ReceiptServiceTicket.class).to(TicketService.class);
    }
}
