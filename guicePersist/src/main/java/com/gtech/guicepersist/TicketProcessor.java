/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtech.guicepersist;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.gtech.guicepersist.entity.Board;
import com.gtech.guicepersist.entity.BoardBuilder;
import com.gtech.guicepersist.entity.Ticket;
import com.gtech.guicepersist.enums.DrawBasedGame;
import com.gtech.guicepersist.enums.DrawDay;
import com.gtech.guicepersist.service.CancellationReceiptService;
import com.gtech.guicepersist.service.ReceiptService;
import com.gtech.guicepersist.service.ReceiptServiceTicket;
import com.gtech.guicepersist.service.TicketService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mxbailey
 */
public class TicketProcessor {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new JpaPersistModule("openjpa"));
        injector.getInstance(ApplicationInitialiser.class);

        ReceiptServiceTicket service = injector.getInstance(TicketService.class);

        String ticketNumber = "8401-019641600-207579";

/*        Ticket ticket = service.createTicket(ticketNumber, DrawBasedGame.LOTTO, BigDecimal.TEN, "123456", DrawDay.TUESDAY, 8, true, "Ticket Message");

        System.out.println(ticket.toString());
        System.out.println(ticket.getCost().toPlainString());
        System.out.println(ticket.getDrawDay());
        System.out.println(ticket.getGGuardNumber());
        System.out.println(ticket.getNumberOfWeeks());

        Board aBoard = service.createBoard(new BoardBuilder(ticket, "A", "01 02 03 04 05 06").raffleNumber("PINK 1000 1000"));
        System.out.println(aBoard.toString());
        System.out.println(ticket.getBoards());
*/

        Ticket ticket = service.getReceipt(ticketNumber);

        System.out.println(ticket.toString());
        System.out.println(ticket.getCost().toPlainString());
        System.out.println(ticket.getDrawDay());
        System.out.println(ticket.getGGuardNumber());
        System.out.println(ticket.getNumberOfWeeks());
        System.out.println(ticket.getBoards());

        System.out.println(service.getReceipts());



    }
}
