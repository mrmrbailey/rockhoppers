package uk.co.rockhoppersuk.number;

import static org.junit.Assert.*;

import org.junit.Test;

public class DivisonTest {

	@Test
	public final void testIsDouble() {
        Divison d = new Divison();
		assertTrue(d.isDouble(12, 6));
		assertTrue(d.isDoubleInvalid(12, 6));

        assertFalse(d.isDouble(12, 5));
        assertFalse(d.isDoubleInvalid(12, 5));

        assertFalse(d.isDouble(11, 6));
        assertFalse(d.isDoubleInvalid(11, 6));

        assertFalse(d.isDouble(11, 5));
        // This should be false but it isn't
        assertTrue(d.isDoubleInvalid(11, 5));
	}

}
