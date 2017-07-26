/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.bitwise;

/**
 *
 * @author mxbailey
 */
public class BitWiseOperations {

    public static int byteToInt(byte b) {
        return 0xFF & (short) b;
    }

    public static short byteToShort(byte b) {
        return (short) (0xFF & (short) b);
    }

    public static int shortToInt(short s) {
        return 0xFFFF & s;
    }
}
