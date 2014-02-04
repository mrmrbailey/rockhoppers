package com.camelotinteractive.game.escape;

import org.junit.Before;
import org.junit.Test;

import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGeneratorTestCase;

public class TestDealOrNoDeal extends TicketDataGeneratorTestCase{
	
	@Before
	public void setUp() throws Exception {
		setUpMocks(new Dondtwel());
	}

	@Test
	public void testGetTicketData() throws TicketDataException {
		System.out.println(getTicketData());
	}
}
