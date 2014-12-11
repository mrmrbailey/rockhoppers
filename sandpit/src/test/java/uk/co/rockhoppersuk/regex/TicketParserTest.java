/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.regex;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class TicketParserTest {


    String WED_TICKET = "#8357-023311360-084279Good luck for your draw on Wed 19 Nov 14 A  10  15  17  19  38  46     PINK 6792 56951 play x Â£2.00 for 1 draw = Â£  2.00 012345678901234567890123456788357-023311360-084279  013800  Term. 10015201";
    String SAT_TICKET = "#8357-030651396-080179Good luck for your draw on Sat 22 Nov 14 A  02  08  11  12  16  31     JADE 0990 79531 play x Â£2.00 for 1 draw = Â£  2.00 8357-030651396-080179  012942  Term. 10015201";
    String WED_SAT_TICKET = "#8357-056880128-081179Good luck for your 1 x Wed and 1 x Sat drawsfrom Wed 19 Nov 14 to Sat 22 Nov 14 A  01  02  10  15  25  43     LIME 3332 04851 play x Â£2.00 for 2 draws = Â£  4.00 8357-056880128-081179  011305  Term. 10015201";
    String WED_2_WEEK_TICKET = "#8357-064220164-086779Good luck for your 2 x Wed drawsfrom Wed 19 Nov 14 to Wed 26 Nov 14 A  11  13  14  20  33  46     PINK 5309 37791 play x Â£2.00 for 2 draws = Â£  4.00 012345678901234567890123456788357-064220164-086779  019797  Term. 10015201";
    String SAT_2_WEEK_TICKET = "#8357-000956417-084179Good luck for your 2 x Sat drawsfrom Sat 22 Nov 14 to Sat 29 Nov 14 A  14  28  30  40  42  48     LIME 9880 47681 play x Â£2.00 for 2 draws = Â£  4.00 8357-000956417-084179  017831  Term. 10015201";
    String BOTH_MULTI_BOARD_TICKET = "#8357-034521089-087679Good luck for your 1 x Wed and 1 x Sat drawsfrom Wed 19 Nov 14 to Sat 22 Nov 14 A  03  11  19  20  31  48     B  05  07  15  24  28  41     C  01  04  06  10  13  23     D  06  08  15  26  47  48     E  06  31  32  39  42  47     F  08  31  36  41  45  48     G  10  11  13  32  37  40     BLUE 5185 7786LIME 1357 5725NAVY 7409 9175GREY 9384 7826LIME 7531 3119PLUM 4570 7652NAVY 7161 16257 plays x Â£2.00 for 2 draws = Â£ 28.00 8357-034521089-087679  013327  Term. 10015201";

    /**
     * Test of parseReceipt method, of class TicketParser.
     */
    @Test
    public void testParseReceipt() {
        System.out.println("parseReceipt");
        TicketParser instance = new TicketParser();
        boolean result = instance.parseSomeTicket(WED_TICKET);
        result = instance.parseSomeTicket(SAT_TICKET);
        result = instance.parseSomeTicket(WED_SAT_TICKET);
        result = instance.parseSomeTicket(WED_2_WEEK_TICKET);
        result = instance.parseSomeTicket(SAT_2_WEEK_TICKET);
        result = instance.parseSomeTicket(BOTH_MULTI_BOARD_TICKET);
        assertTrue(result);
    }

}
