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
public class AnotherTicketParser {

    private static final String TICKET_SERIAL_PATTERN = "(\\d{4}-\\d{9}-\\d{6})";
    private static final String GOOD_LUCK_MESSAGE = ".*Good luck for your ";
    private static final String NUMBER_OF_WEEKS_PATTERN = "(\\d)?"; //either empty therefore 1 or a number.
    private static final String PRE_DRAW_DATE_MESSAGE = "(\\sx.*from|draw on)\\s";
    private static final String DRAW_DATE_MESSAGE = "(((Tue|Wed|Fri|Sat)\\s\\d{2}\\s[A-Z][a-z]{2}\\s\\d{2}\\s(to\\s)?){1,2})";
    private static final String PRE_COST_MESSAGE = "\\d\\splay.*=.*"; //this skips the boards.
    private static final String COST_PATTERN = "(\\d{1,3}\\.\\d{2})";
    private static final String TICKET_MESSAGE_PATTERN = "\\s(.*)";
    private static final String G_GUARD_PATTERN = "\\s{2}(\\d{6})";
    private static final String END_MESSAGE = "\\s{2}Term\\.\\s\\d{8}$";

    private static final String TICKET_PATTERN = "^#.*"
                                                 + TICKET_SERIAL_PATTERN
                                                 + GOOD_LUCK_MESSAGE
                                                 + NUMBER_OF_WEEKS_PATTERN
                                                 + PRE_DRAW_DATE_MESSAGE
                                                 + DRAW_DATE_MESSAGE
                                                 + ".*" + PRE_COST_MESSAGE
                                                 + COST_PATTERN
                                                 + TICKET_MESSAGE_PATTERN
                                                 + TICKET_SERIAL_PATTERN
                                                 + G_GUARD_PATTERN
                                                 + END_MESSAGE;

    private static final String LOTTO_BOARD_PATTERN = "([A-Z])((\\s{2}\\d{2}){6})";
    private static final String LOTTO_RAFFLE_PATTERN = "[A-Z]{4}(\\s\\d{4}){2}";

    public boolean parseSomeTicket(final String ticketText) {
        System.out.println(TICKET_PATTERN);
        Pattern pattern = Pattern.compile(TICKET_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);
        if (!matcher.matches()) {
            System.err.println("OPPS");
            return false;
        } else {
            System.out.println(matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " : " + matcher.group(i));
            }
            return true;
        }
    }

    public boolean parseBoards(final String ticketText) {

        Pattern pattern = Pattern.compile(LOTTO_BOARD_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);

        System.out.println(matcher.matches());

        while (matcher.find()) {
            System.out.println(matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " : " + matcher.group(i));
            }
        }

        pattern = Pattern.compile(LOTTO_RAFFLE_PATTERN);
        matcher = pattern.matcher(ticketText);

        System.out.println(matcher.matches());

        while (matcher.find()) {
            System.out.println(matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " : " + matcher.group(i));
            }
        }
        return true;
    }

    private static final String EURO_BOARD_PATTERN = "([A-Z])((\\s.?\\d{2}){5})\\s-\\s((\\d{2}\\s.?){2})";
    private static final String EURO_RAFFLE_PATTERN = "[A-Z]{3}\\d{6}";

    public boolean parseEuroBoards(final String ticketText) {

        Pattern pattern = Pattern.compile(EURO_BOARD_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);

        System.out.println(matcher.matches());

        while (matcher.find()) {
            System.out.println(matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " : " + matcher.group(i));
            }
        }

        pattern = Pattern.compile(EURO_RAFFLE_PATTERN);
        matcher = pattern.matcher(ticketText);

        System.out.println(matcher.matches());

        while (matcher.find()) {
            System.out.println(matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " : " + matcher.group(i));
            }
        }
        return true;
    }

    private static final String LOTTO_HOTPICKS_SELECTIONS_PATTERN = "([A-E])PICK\\s\\d\\s+((\\d{2}\\s+){1,5})";

    public boolean parseLottoHotpickBoards(final String ticketText) {

        Pattern pattern = Pattern.compile(LOTTO_HOTPICKS_SELECTIONS_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);

        System.out.println(matcher.matches());

        while (matcher.find()) {
            System.out.println(matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " : " + matcher.group(i));
            }
        }

        return true;
    }

    private static final String TB_PATTERN = "^.*([A-F])((\\s{2}\\d{2}){5}).*?-\\s*(\\d{2}).*$";

    public boolean parseThuderballBoards(final String ticketText) {

        Pattern pattern = Pattern.compile(TB_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);

        System.out.println(matcher.matches());

        while (matcher.find()) {
            System.out.println(matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " : " + matcher.group(i));
            }
        }

        return true;
    }

//    private static final String EURO_BOARD_PATTERN_1 = ".*Lucky Stars\\s*(([A-Z]\\s(\\d{2}\\s*){5}-\\s((\\d{2}\\s*){2})){1,5}).*draw\\s(([A-Z]{3}\\d{6}){1,5})";
    private static final String EURO_BOARD_PATTERN_1 = ".*Lucky Stars\\s*(([A-Z]\\s(\\d{2}\\s*){5}-\\s((\\d{2}\\s*){2})){1,5}).*draw((\\s[A-Z]{3}\\d{6}){1,5})";
//    private static final String PRE_COST_MESSAGE = "\\d\\splay.*=.*"; //this skips the boards.

    private static final String WHOLE_EURO_BOARD_PATTERN = "^#.*"
                                                           + TICKET_SERIAL_PATTERN
                                                           + GOOD_LUCK_MESSAGE
                                                           + NUMBER_OF_WEEKS_PATTERN
                                                           + PRE_DRAW_DATE_MESSAGE
                                                           + DRAW_DATE_MESSAGE
                                                           + EURO_BOARD_PATTERN_1
                                                           + PRE_COST_MESSAGE
                                                           + COST_PATTERN
                                                           + TICKET_MESSAGE_PATTERN
                                                           + TICKET_SERIAL_PATTERN
                                                           + G_GUARD_PATTERN
                                                           + END_MESSAGE;

    public boolean parseEuroBoards2(final String ticketText) {
        System.out.println(WHOLE_EURO_BOARD_PATTERN);
        Pattern pattern = Pattern.compile(WHOLE_EURO_BOARD_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);
        if (!matcher.matches()) {
            System.err.println("OPPS");
            return false;
        } else {

            System.out.println(matcher.groupCount());
            for (int i = 0; i <= matcher.groupCount(); i++) {
                System.out.println(i + " : " + matcher.group(i));
            }
            return true;
        }
    }

}
