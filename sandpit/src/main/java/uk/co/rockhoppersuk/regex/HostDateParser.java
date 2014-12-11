/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.regex;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author mxbailey
 */
public class HostDateParser {

    String DATE_TIME_PATTERN = "^DATE:([A-Z][a-z]{2}\\s[A-Z][a-z]{2}\\s\\d{2}\\s\\d{4}\\s\\d{2}:\\d{2}:\\d{2})$";
    String DATE_FORMAT_PATTERN = "EEE MMM dd yyyy HH:mm:ss";

    public Date getDate(String input) throws ParseException {

        Pattern pattern = Pattern.compile(DATE_TIME_PATTERN);

        for (StringTokenizer stringTokenizer = new StringTokenizer(input, "\n"); stringTokenizer.hasMoreTokens();) {

            Matcher matcher = pattern.matcher(stringTokenizer.nextToken());
            if (matcher.matches()) {
                System.out.println(matcher.groupCount());
                for (int i = 0; i <= matcher.groupCount(); i++) {
                    System.out.println(i + " : " + matcher.group(i));
                }

                DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.UK);
                return dateFormat.parse(matcher.group(1));
            }

        }
        return null;
    }

}
