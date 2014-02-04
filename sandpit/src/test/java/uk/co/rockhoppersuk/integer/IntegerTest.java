package uk.co.rockhoppersuk.integer;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerTest {

	@Test
	public final void test() {
		Integer i = new Integer(1);
		setInt(i);
		i = null;
		try {
			setInt(i);
		} catch (NullPointerException npe) {
			return;
		}
		fail("NullPointer excpected");
	}
	
	private void setInt(int a) {
		int i = a;
	}

}
