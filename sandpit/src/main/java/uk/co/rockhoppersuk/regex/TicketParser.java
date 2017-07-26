/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mxbailey
 */
public class TicketParser {

    private static final String IMAGE_PATTERN = ".*";
    private static final String TICKET_SERIAL_PATTERN = "\\d{4}-\\d{9}-\\d{6}";
    private static final String DRAW_DETAILS_PATTERN = "([A-Z][a-z]{2}\\s\\d{2}\\s(to )?){2,4}";
    private static final String LOTTO_BOARDS_PATTERN = "([A-G](\\s*(\\d{2}\\s*){6})){1,7}";
    private static final String LOTTO_RAFFLE_PATTERN = "([A-Z]{4}\\s\\d{4}\\s\\d{4}){1,7}";
    private static final String NUMBER_OF_WEEKS_PATTERN = ".*(\\d\\sx){0,1}.*";
    private static final String COST_PATTERN = "\\d\\splay(s)?\\sx.*\\d{1,3}\\.\\d{2}\\sfor\\s\\d\\sdraw(s)?\\s=\\s.*(\\d{1,2}\\.\\d{2})";
    private static final String TICKET_MESSAGE_PATTERN = ".*";
    private static final String G_GUARD_PATTERN = "\\d{6}";
    private static final String TERMINAL_NUMBER_PATTERN = "\\s*Term\\.\\s\\d{8}";

    private static final String LOTTO_TICKET_PATTERN = "^" + IMAGE_PATTERN
                                                       + "(" + TICKET_SERIAL_PATTERN + ")"
                                                       + "(" + NUMBER_OF_WEEKS_PATTERN + ")"
                                                       + "(" + DRAW_DETAILS_PATTERN + ")"
                                                       + "(" + LOTTO_BOARDS_PATTERN + ")"
                                                       + "(" + LOTTO_RAFFLE_PATTERN + ")"
                                                       + "(" + COST_PATTERN + ")"
                                                       + "(" + TICKET_MESSAGE_PATTERN + ")"
                                                       + "(" + TICKET_SERIAL_PATTERN + ")"
                                                       + "\\s*(" + G_GUARD_PATTERN + ")"
                                                       + "(" + TERMINAL_NUMBER_PATTERN + ")$";

    public String parseReceipt(final String ticketText) {
        Pattern pattern = Pattern.compile(LOTTO_TICKET_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);
        return matcher.group();
    }

    public boolean parseSomeTicket(final String ticketText) {
        System.out.println(LOTTO_TICKET_PATTERN);
        Pattern pattern = Pattern.compile(LOTTO_TICKET_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);
        if (!matcher.matches()) {
            System.err.println("OPPS");
        }
        System.out.println(matcher.groupCount());
        for (int i = 0; i <= matcher.groupCount(); i++) {
            System.out.println(i + " : " + matcher.group(i));
        }
        return true;
    }

}
