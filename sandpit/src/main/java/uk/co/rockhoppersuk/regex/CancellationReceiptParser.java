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
public class CancellationReceiptParser {

//    private final static String CANCELLATION_RECEIPT_PATTERN = "^#.*CANCELLATIONTICKET NUMBER:\\s(\\d{4}-\\d{9}-\\d{2}[\\dX]{2}\\d{2}).*REFUND AMOUNT:\\s{2}.?.?\\d{1,3}\\.\\d{2}.*TRANSACTION NUMBER:\\s{2}(\\d{4}-\\d{9}-\\d{2}[\\dX]{2}\\d{2}).*$";
    private final static String CANCELLATION_RECEIPT_PATTERN = "^#.*CANCELLATIONTICKET NUMBER:\\s(\\d{4}-\\d{9}-\\d{2}[\\dX]{2}\\d{2}).*REFUND AMOUNT:\\s{2}.?.?(\\d{1,3}\\.\\d{2}).*TRANSACTION NUMBER:\\s(\\d{4}-\\d{9}-\\d{6}).*$";

    public String parseReceipt(final String ticketText) {
        Pattern pattern = Pattern.compile(CANCELLATION_RECEIPT_PATTERN);
        Matcher matcher = pattern.matcher(ticketText);
        return matcher.group();
    }

    public boolean parseSomeReceipt(final String receiptText) {
        System.out.println(CANCELLATION_RECEIPT_PATTERN);
        Pattern pattern = Pattern.compile(CANCELLATION_RECEIPT_PATTERN);
        Matcher matcher = pattern.matcher(receiptText);
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
