package uk.co.rockhoppersuk.format;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberFormater {

    public String getNumber(BigDecimal value) {
        String format = value.compareTo(new BigDecimal(1000)) < 0 ? "#,##0.00" : "#,##0";
        DecimalFormat df = new DecimalFormat(format);
        return df.format(value);
    }
}
