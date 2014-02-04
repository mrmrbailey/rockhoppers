/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.number;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 *
 * @author mxbailey
 */
public class BigDecimalMathContext {

    public BigDecimal getRoundedNumber(double number, int precision) {
        return new BigDecimal(number, new MathContext(2));
    }
}
