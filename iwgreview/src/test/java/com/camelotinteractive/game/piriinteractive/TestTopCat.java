package com.camelotinteractive.game.piriinteractive;

import org.junit.Before;
import org.junit.Test;

import com.camelotinteractive.game.instant.TicketDataException;
import com.camelotinteractive.game.instant.TicketDataGeneratorTestCase;

public class TestTopCat extends TicketDataGeneratorTestCase{
	
	@Before
	public void setUp() throws Exception {
		setUpMocks(new TopCat());
	}

	//@Test
	public void testGetTicketData() throws TicketDataException {
		System.out.println(getTicketData());
	}
}

