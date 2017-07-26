/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.co.rockhoppersuk.bitwise;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mxbailey
 */
public class BitWiseOperationsTest {


    @Test
    public void testBitWiseOperation() {

        String hexValue = "0F"; //15 00001111
        byte aByte = Byte.parseByte(hexValue, 16);

        hexValue = "83"; // 131 10000011
        byte anotherByte = (byte) Integer.parseInt(hexValue, 16);

        short aShort = BitWiseOperations.byteToShort(aByte);
        short anotherShort = BitWiseOperations.byteToShort(anotherByte);

        int result = aByte | anotherByte;

        int shortResult = aShort | anotherShort;

    }
}