package com.camelotinteractive.game.publiccreative;

import org.junit.Before;
import org.junit.Test;

import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGeneratorTestCase;

public class CachLinesTest extends TicketDataGeneratorTestCase{

	@Before
	public void setUp() throws Exception {
		setUpMocks(new Cashlines());
	}


	@Test
	public void testGetTicketData() throws TicketDataException {
		System.out.println(getTicketData());
	}

}
