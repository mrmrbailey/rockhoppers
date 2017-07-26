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
public class FinalTicketParserTest {

    private final String WED_TICKET = "#8357-023311360-084279Good luck for your draw on Wed 19 Nov 14 A  10  15  17  19  38  46     PINK 6792 56951 play x Â£2.00 for 1 draw = Â£  2.00 012345678901234567890123456788357-023311360-084279  013800  Term. 10015201";
    private final String SAT_TICKET = "#8357-030651396-080179Good luck for your draw on Sat 22 Nov 14 A  02  08  11  12  16  31     JADE 0990 79531 play x Â£2.00 for 1 draw = Â£  2.00 8357-030651396-080179  012942  Term. 10015201";
    private final String WED_SAT_TICKET = "#8357-056880128-081179Good luck for your 1 x Wed and 1 x Sat drawsfrom Wed 19 Nov 14 to Sat 22 Nov 14 A  01  02  10  15  25  43     LIME 3332 04851 play x Â£2.00 for 2 draws = Â£  4.00 8357-056880128-081179  011305  Term. 10015201";
    private final String WED_2_WEEK_TICKET = "#8357-064220164-086779Good luck for your 2 x Wed drawsfrom Wed 19 Nov 14 to Wed 26 Nov 14 A  11  13  14  20  33  46     PINK 5309 37791 play x Â£2.00 for 2 draws = Â£  4.00 012345678901234567890123456788357-064220164-086779  019797  Term. 10015201";
    private final String SAT_2_WEEK_TICKET = "#8357-000956417-084179Good luck for your 2 x Sat drawsfrom Sat 22 Nov 14 to Sat 29 Nov 14 A  14  28  30  40  42  48     LIME 9880 47681 play x Â£2.00 for 2 draws = Â£  4.00 8357-000956417-084179  017831  Term. 10015201";
    private final String BOTH_MULTI_BOARD_TICKET = "#8357-034521089-087679Good luck for your 1 x Wed and 1 x Sat drawsfrom Wed 19 Nov 14 to Sat 22 Nov 14 A  03  11  19  20  31  48     B  05  07  15  24  28  41     C  01  04  06  10  13  23     D  06  08  15  26  47  48     E  06  31  32  39  42  47     F  08  31  36  41  45  48     G  10  11  13  32  37  40     BLUE 5185 7786LIME 1357 5725NAVY 7409 9175GREY 9384 7826LIME 7531 3119PLUM 4570 7652NAVY 7161 16257 plays x Â£2.00 for 2 draws = Â£ 28.00 8357-034521089-087679  013327  Term. 10015201";

    private final String LOTTO_MESSAGE = "#6013-040302081-085979************** TRAINING TICKET ***************NOT A VALID TICKET. NOT FOR SALEGood luck for your 2 x Wed drawsfrom Wed 27 Dec 00 to Wed 03 Jan 01 A  01  02  03  04  05  06     BLUE 9267 41251 play x Â£1.00 for 2 draws = Â£  2.00 LOTTO TICKET MSG FOR WED6013-040302081-085979  019659  Term. 01234567";
    private final String EURO_MESSAGE = "#7096-014655749-203079************** TRAINING TICKET ***************NOT A VALID TICKET. NOT FOR SALEGood luck for your draw on Tue 26 Dec 00       Lucky Stars   A 10  20  30  40  50 - 01  02  HBB5401588 play x Â£2.00 for 1 draw = Â£2.00 7096-014655749-203079  014626  Term. 01234567";
    private final String EURO_MESSAGE_MULTI_BOARD = "#7096-014655749-203079************** TRAINING TICKET ***************NOT A VALID TICKET. NOT FOR SALEGood luck for your 2 x Tue and 2 x Fri drawsfrom Tue 26 Dec 00 to Fri 05 Jan 01       Lucky Stars   A 14  17  18  20  38 - 06  08 B 01  27  43  44  46 - 05  08 C 14  18  20  22  45 - 06  11 D 06  08  19  22  30 - 02  04 E 20  23  32  41  42 - 02  09 Guaranteed UK prize in every draw HBB540154 HBB540155 HBB540156 HBB540157 HBB5401585 plays x Â£2.00 for 4 draws = Â£40.00 7096-014655749-203079  019577  Term. 01234567";
    private final String THUDERBALL_MESSAGE = "#67000-040890112-125179************** TRAINING TICKET ***************NOT A VALID TICKET. NOT FOR SALEGood luck for your draw on Fri 29 Dec 00         T/Ball      A  10  20  30  31  32  -  05  1 play x Â£1.00 for 1 draw = Â£1.00 THUNDER BALL TICKET MSG FOR FRI7000-040890112-125179  016867  Term. 01234567";
    private final String LOTTO_HOTPICKS_MESSAGE = "#6000-066443780-273079************** TRAINING TICKET ***************NOT A VALID TICKET. NOT FOR SALEGood luck for your draw on Wed 27 Dec 00 PICK 1  01               A1 play x Â£1.00 for 1 draw = Â£1.00 LOTTO HOTPICKS TICKET MSG6000-066443780-273079  011245  Term. 01234567";
    private final String ANOTHER_LOTTO_HOTPICKS_MESSAGE = "#r6000-066443780-273079************** TRAINING TICKET ***************NOT A VALID TICKET. NOT FOR SALEGood luck for your draw on Sat 30 Dec 00 PICK 1  01               APICK 2  33  34            CPICK 5  14  15  25  34  36   BPICK 5  31  32  33  34  35   D4 plays x Â£0.10 for 1 draw = Â£0.40 LOTTO HOTPICKS TICKET MSG6000-066443780-273079  014499  Term. 01234567";
    private final String THUNDERBALL_MULTI = "#å7000-040890112-125179************** TRAINING TICKET ***************NOT A VALID TICKET. NOT FOR SALEGood luck for your 4 x Wed 4 x Fri and 5 x Sat draws from Sat 30 Dec 00 to Tue 30 Jan 01         T/Ball      A  02  11  18  20  32  -  07  B  06  16  22  31  32  -  12  C  14  16  24  26  30  -  07  D  05  07  28  30  33  -  02  E  01  05  07  15  19  -  02  F  12  14  26  29  30  -  13  6 plays x Â£0.10 for 15 draws = Â£9.00 7000-040890112-125179  016195  Term. 01234567";

    /**
     * Test of parseTicket method, of class FinalTicketParser.
     */
    @Test
    public void testParseTicket() {
        System.out.println("parseTicket");
/*
        FinalTicketParser instance = new FinalTicketParser();
        System.out.println("WED_TICKET");
        assertTrue(instance.parseTicket(WED_TICKET));
        System.out.println("SAT_TICKET");
        assertTrue(instance.parseTicket(SAT_TICKET));
        System.out.println("WED_SAT_TICKET");
        assertTrue(instance.parseTicket(WED_SAT_TICKET));
        System.out.println("WED_2_WEEK_TICKET");
        assertTrue(instance.parseTicket(WED_2_WEEK_TICKET));
        System.out.println("SAT_2_WEEK_TICKET");
        assertTrue(instance.parseTicket(SAT_2_WEEK_TICKET));
        System.out.println("BOTH_MULTI_BOARD_TICKET");
        assertTrue(instance.parseTicket(BOTH_MULTI_BOARD_TICKET));
        System.out.println("LOTTO_MESSAGE");
        assertTrue(instance.parseTicket(LOTTO_MESSAGE));
        System.out.println("EURO_MESSAGE");
        assertTrue(instance.parseTicket(EURO_MESSAGE));
        System.out.println("EURO_MESSAGE_MULTI_BOARD");
        assertTrue(instance.parseTicket(EURO_MESSAGE_MULTI_BOARD));
        System.out.println("THUDERBALL_MESSAGE");
        assertTrue(instance.parseTicket(THUDERBALL_MESSAGE));
        System.out.println("LOTTO_HOTPICKS_MESSAGE");
        assertTrue(instance.parseTicket(LOTTO_HOTPICKS_MESSAGE));
        System.out.println("ANOTHER_LOTTO_HOTPICKS_MESSAGE");
        assertTrue(instance.parseTicket(ANOTHER_LOTTO_HOTPICKS_MESSAGE));

        FinalTicketParser euroticket = new EuroTicketParser();
        System.out.println("EURO_MESSAGE");
        assertTrue(euroticket.parseTicket(EURO_MESSAGE));
        System.out.println("EURO_MESSAGE_MULTI_BOARD");
        assertTrue(euroticket.parseTicket(EURO_MESSAGE_MULTI_BOARD));

        FinalTicketParser lottoTicket = new LottoTicketParser();
        System.out.println("WED_TICKET");
        assertTrue(lottoTicket.parseTicket(WED_TICKET));
        System.out.println("SAT_TICKET");
        assertTrue(lottoTicket.parseTicket(SAT_TICKET));
        System.out.println("WED_SAT_TICKET");
        assertTrue(lottoTicket.parseTicket(WED_SAT_TICKET));
        System.out.println("WED_2_WEEK_TICKET");
        assertTrue(lottoTicket.parseTicket(WED_2_WEEK_TICKET));
        System.out.println("SAT_2_WEEK_TICKET");
        assertTrue(lottoTicket.parseTicket(SAT_2_WEEK_TICKET));
        System.out.println("BOTH_MULTI_BOARD_TICKET");
        assertTrue(lottoTicket.parseTicket(BOTH_MULTI_BOARD_TICKET));
        System.out.println("LOTTO_MESSAGE");

        FinalTicketParser hotpick = new HotpicksTicketParser();
        System.out.println("LOTTO_HOTPICKS_MESSAGE");
        assertTrue(hotpick.parseTicket(LOTTO_HOTPICKS_MESSAGE));
        System.out.println("ANOTHER_LOTTO_HOTPICKS_MESSAGE");
        assertTrue(hotpick.parseTicket(ANOTHER_LOTTO_HOTPICKS_MESSAGE));
*/
        FinalTicketParser tb = new TBparser();
        System.out.println("THUDERBALL_MESSAGE");
        assertTrue(tb.parseTicket(THUDERBALL_MESSAGE));
        System.out.println("THUNDERBALL_MULTI");
        assertTrue(tb.parseTicket(THUNDERBALL_MULTI));

    }

}
