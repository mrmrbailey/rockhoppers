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
public class FinalTicketParser {

    private static final String TICKET_SERIAL_GROUP_NAME = "ticketSerialGroupName";
    private static final String TICKET_SERIAL_PATTERN_NO_GROUP = "\\d{4}-\\d{9}-\\d{6}";
    private static final String TICKET_SERIAL_PATTERN = "(?<" + TICKET_SERIAL_GROUP_NAME + ">"+TICKET_SERIAL_PATTERN_NO_GROUP+")";

    private static final String GOOD_LUCK_MESSAGE = ".*Good luck for your ";

    private static final String NUMBER_OF_WEEKS_GROUP_NAME = "noWeeks";
    private static final String NUMBER_OF_WEEKS_PATTERN = "(?<" + NUMBER_OF_WEEKS_GROUP_NAME + ">\\d)?"; //either empty therefore 1 or a number.

    private static final String PRE_DRAW_DATE_MESSAGE = "(?:\\sx.*from|draw on)\\s";

    private static final String DRAW_DATES_GROUP_NAME = "drawDates";
    private static final String DRAW_DATE_MESSAGE = "(?<" + DRAW_DATES_GROUP_NAME + ">(?:(?:Tue|Wed|Fri|Sat)\\s\\d{2}\\s[A-Z][a-z]{2}\\s\\d{2}\\s(?:to\\s)?){1,2})";

    private static final String BOARDS_GROUP_NAME = "boards"; //todo add the boards
    private static final String BOARDS_PATTERN = "(?<"+BOARDS_GROUP_NAME+">.*)"; //todo add the boards

    private static final String PRE_COST_MESSAGE = "\\d\\splay.*=.*\\p{Sc}\\s*"; //this skips the boards.

    private static final String COST_GROUP_NAME = "cost";
    private static final String COST_PATTERN = "(?<" + COST_GROUP_NAME + ">\\d{1,3}\\.\\d{2})";

    private static final String TICKET_MESSAGE_GROUP_NAME = "ticketMessage";
    private static final String TICKET_MESSAGE_PATTERN = "\\s(?<" + TICKET_MESSAGE_GROUP_NAME + ">.*)";

    private static final String GGUARD_GROUP_NAME = "gguard";
    private static final String G_GUARD_PATTERN = "\\s{2}(?<" + GGUARD_GROUP_NAME + ">\\d{6})";

    private static final String START_MESSAGE = "^#.*";
    private static final String END_MESSAGE = "\\s{2}Term\\.\\s\\d{8}$";

    private static final String TICKET_PATTERN = START_MESSAGE
                                                 + TICKET_SERIAL_PATTERN
                                                 + GOOD_LUCK_MESSAGE
                                                 + NUMBER_OF_WEEKS_PATTERN
                                                 + PRE_DRAW_DATE_MESSAGE
                                                 + DRAW_DATE_MESSAGE
                                                 + BOARDS_PATTERN
                                                 + PRE_COST_MESSAGE
                                                 + COST_PATTERN
                                                 + TICKET_MESSAGE_PATTERN
                                                  + TICKET_SERIAL_PATTERN_NO_GROUP
                                                  + G_GUARD_PATTERN
                                                  + END_MESSAGE;

    public boolean parseTicket(final String ticketText) {

        Pattern pattern = Pattern.compile(TICKET_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);

        System.out.println("ticketText: " + ticketText);
        System.out.println("pattern: " + TICKET_PATTERN);

        System.out.println(matcher.matches());

        if (matcher.matches()) {
            System.out.println("groupCount: " + matcher.groupCount());
            System.out.println(matcher.group(TICKET_SERIAL_GROUP_NAME));
            if (matcher.group(NUMBER_OF_WEEKS_GROUP_NAME) == null) {
                System.out.println("1");
            } else {
                System.out.println(matcher.group(NUMBER_OF_WEEKS_GROUP_NAME));
            }
            System.out.println(matcher.group(DRAW_DATES_GROUP_NAME));
            System.out.println(parseBoards(matcher));
            System.out.println(matcher.group(COST_GROUP_NAME));
            System.out.println(matcher.group(TICKET_MESSAGE_GROUP_NAME));
            System.out.println(matcher.group(GGUARD_GROUP_NAME));
            return true;
        } else {
            return false;
        }

    }


    protected String parseBoards(Matcher matcher) {
        return matcher.group(BOARDS_GROUP_NAME);
    }
}
