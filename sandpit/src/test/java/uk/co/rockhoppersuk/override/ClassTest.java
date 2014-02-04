package uk.co.rockhoppersuk.override;

import static org.junit.Assert.*;

import org.junit.Test;

public class ClassTest {

	@Test
	public final void testService() {
		BaseClass clazz = new Class();
		clazz.service(1);
		//I am truly sorryI only want the output 
		assertTrue(true);
	}

}
